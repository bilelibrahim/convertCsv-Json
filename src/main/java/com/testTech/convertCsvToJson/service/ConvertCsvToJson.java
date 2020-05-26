package com.testTech.convertCsvToJson.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class ConvertCsvToJson {

    public String convert(String fileName){
        List<String> csvRows = null;
        String json = "";
        try(Stream<String> reader = Files.lines(Paths.get(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString()))){
            csvRows = reader.collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        if(csvRows != null){

            json = csvToJson(csvRows, fileName);
            log.info(json);

        }

        return json;
    }

    private String csvToJson(List<String> csv, String fileName){
        AtomicInteger idx = new AtomicInteger();
        // Si le csv est vide return.
        if(csv.size() <= 1){
            return "[]";
        }
        // Suppression des lignes vides.
        csv.removeIf(e -> e.trim().isEmpty());

        //construction des moms de colonnes.
        String[] columns = {"numReference", "size", "price", "type"};
        // entête du JSON
        StringBuilder json = new StringBuilder("\n{");
        StringBuilder errors = new StringBuilder("\nerrors : [");
        json.append("\n\"inputFile\" : \"" + fileName +"\"," );
        json.append("\n\"references\" :[\n");
        //Traitement des lignes, conversion csv -> json
        csv
            .stream()
            .map(e -> e.split(";"))
            .filter(e -> e.length == columns.length) //le nombre de colonnes doit être égale au nombre des nom de colonnes.
            .forEach(row -> {
                String message = validateRow(row);
                idx.getAndIncrement();
                if("ok".equals(message)) {
                    buildJsonEntry(columns, json, row);
                }else{
                    buildError(idx, errors, row, message);

                }
            });
        if(!"\nerrors : [".equals(errors)){
            json.append(errors.toString());
        }
        json.append("\n]");

        json.append("\n}");
        return json.toString();

    }

    private void buildJsonEntry(String[] columns, StringBuilder json, String[] row) {
        json.append("\t{\n");
        for (int i = 0; i < columns.length; i++) {
            json.append("\t\t\"")
                    .append(columns[i])
                    .append("\" : \"")
                    .append(row[i])
                    .append("\",\n"); //comma-1
        }
        json.append("\t},");
    }

    private void buildError(AtomicInteger idx, StringBuilder errors, String[] row, String message) {
        errors.
                append("\t{\n")
                .append("\t\t")
                .append("\n\t\t\"line\" : ").append(idx.get()).append(",")
                .append("\n\t\t\"message\" : ").append("\""+message+"\"").append(",")
                .append("\n\t\t\"value\" : ").append("\"").append(row[0]).append(",").append(row[1]).append(",").append(row[2]).append(",").append(row[3]).append("\"")
                .append("\n},");
    }

    /**
     * return message d'erreur
     * @param row
     * @return
     */
    private String validateRow(String[] row) {
        if (row[0].length() != 10) {
            return "Incorrect reference";
        }

        if (!row[1].equals("R") && !row[1].equals("G") && !row[1].equals("B")) {
            return "Incorrect value of color";
        }
        return "ok";

    }

}