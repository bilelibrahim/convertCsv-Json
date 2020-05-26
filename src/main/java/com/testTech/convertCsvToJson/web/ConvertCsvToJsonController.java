package com.testTech.convertCsvToJson.web;

import com.testTech.convertCsvToJson.service.ConvertCsvToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertCsvToJsonController {

    @Autowired
    ConvertCsvToJson convertCsvToJson;
    @GetMapping("/convert")
    public ResponseEntity<Object> converCsvToJson(@RequestParam String fileName) {
        String json = convertCsvToJson.convert(fileName);
        return new ResponseEntity<Object>(json, HttpStatus.OK);
    }

}
