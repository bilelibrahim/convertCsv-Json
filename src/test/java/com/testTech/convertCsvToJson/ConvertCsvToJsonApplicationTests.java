package com.testTech.convertCsvToJson;

import com.testTech.convertCsvToJson.service.ConvertCsvToJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConvertCsvToJsonApplicationTests {

	@Autowired
	ConvertCsvToJson convertCsvToJson;
	@Test
	void contextLoads() {
	}


	@Test
	public void testConvert(){
		convertCsvToJson.convert("input.csv");
	}

}
