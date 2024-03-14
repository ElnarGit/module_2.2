package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class WriterServiceTest {
	
	@Mock
	WriterService writerService = Mockito.mock(WriterService.class);
	
	private Writer testWriter;
	
	
	@BeforeEach
	void setUp()  {
		testWriter = Writer.builder()
				.id(1L)
				.firstname("Test")
				.lastname("Test")
				.posts(new ArrayList<>())
				.writerStatus(WriterStatus.ACTIVE)
				.build();
	}
	
	@Test
	void  testGetById(){
		when(writerService.getWriterById(1L)).thenReturn(testWriter);
		
		assertNotNull(testWriter);
		assertEquals("Test", testWriter.getFirstname());
		assertEquals("Test", testWriter.getLastname());
		assertEquals(WriterStatus.ACTIVE, testWriter.getWriterStatus());
	}
	
	@Test
	void testGetAllWriters() {
		List<Writer> writers = new ArrayList<>();
		writers.add(testWriter);
		
		when(writerService.getAllWriters()).thenReturn(writers);
		
		List<Writer> result = writerService.getAllWriters();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test", result.get(0).getFirstname());
		assertEquals("Test", result.get(0).getLastname());
		assertEquals(WriterStatus.ACTIVE, result.get(0).getWriterStatus());
	}
	
	@Test
	void testSaveWriter() {
		when(writerService.saveWriter(testWriter)).thenReturn(testWriter);
		
		assertNotNull(testWriter);
		assertEquals("Test", testWriter.getFirstname());
		assertEquals("Test", testWriter.getLastname());
		assertEquals(WriterStatus.ACTIVE, testWriter.getWriterStatus());
	}
	
	@Test
	void testUpdateWriter() {
		Writer updatedWriter = Writer.builder()
				.id(1L)
				.firstname("Updated Test")
				.lastname("Updated Test")
				.posts(new ArrayList<>())
				.writerStatus(WriterStatus.ACTIVE)
				.build();
		
		when(writerService.updateWriter(updatedWriter)).thenReturn(updatedWriter);
		
		assertNotNull(updatedWriter);
		assertEquals("Updated Test", updatedWriter.getFirstname());
		assertEquals("Updated Test", updatedWriter.getLastname());
		assertEquals(WriterStatus.ACTIVE, updatedWriter.getWriterStatus());
	}
	
	@Test
	void testDeleteWriter() {
		writerService.deleteWriter(1L);
		verify(writerService, times(1)).deleteWriter(1L);
	}
}
