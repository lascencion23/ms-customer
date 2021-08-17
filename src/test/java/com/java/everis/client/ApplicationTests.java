package com.java.everis.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
class ApplicationTests {

	@Test
	public void contextLoads() {
		assertTrue(true);
	}

}
