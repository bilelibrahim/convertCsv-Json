## Tester la conversion CSV -> JSON
Via Test unitaire
 
ConvertCsvToJsonApplicationTests.convert()

Via appel rest : 
1. lancer l'application avec : mvn spring-boot:run 
2. http://localhost:8000/convert?fileName=input.csv
