package test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.dao.DateProcessor;

@ExtendWith(value = SpringExtension.class)
public class TestDateProcessor {

	@Test
	public void testParseLocalDateTime_ToString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateProcessor.DATE_FORMAT);
		String nowAsString = now.format(formatter);
		
		assertEquals(nowAsString, DateProcessor.toString(now));
	}
	
	@Test
	public void testParseString_ToLocalDateTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateProcessor.DATE_FORMAT);
		String nowAsString = now.format(formatter);
		
		LocalDateTime parseNow = DateProcessor.toDate(nowAsString);
		assertEquals(nowAsString, parseNow.format(formatter));
	}
}
