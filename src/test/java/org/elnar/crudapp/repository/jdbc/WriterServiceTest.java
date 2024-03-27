package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.WriterRepository;
import org.elnar.crudapp.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class WriterServiceTest {
	
	@Mock
	private WriterRepository writerRepository;
	
	private WriterService writerService;
	
	private Writer testWriter;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		writerService = new WriterService(writerRepository);
		
		testWriter = Writer.builder()
				.id(1L)
				.firstname("Test")
				.lastname("Test")
				.posts(new ArrayList<>())
				.writerStatus(WriterStatus.ACTIVE)
				.build();
	}
	
	@Test
	void testGetById() {
		when(writerRepository.getById(anyLong())).thenReturn(testWriter);
		
		Writer writer = writerService.getWriterById(1L);
		
		assertNotNull(writer);
		assertEquals("Test", writer.getFirstname());
		assertEquals("Test", writer.getLastname());
		assertEquals(WriterStatus.ACTIVE, writer.getWriterStatus());
	}
	
	@Test
	void testGetAllWriters() {
		List<Writer> writers = new ArrayList<>();
		writers.add(testWriter);
		
		when(writerRepository.getAll()).thenReturn(writers);
		
		List<Writer> result = writerService.getAllWriters();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test", result.get(0).getFirstname());
		assertEquals("Test", result.get(0).getLastname());
		assertEquals(WriterStatus.ACTIVE, result.get(0).getWriterStatus());
	}
	
	@Test
	void testSaveWriter() {
		when(writerRepository.save(any(Writer.class))).thenReturn(testWriter);
		
		Writer writer = writerService.saveWriter(testWriter);
		
		assertNotNull(writer);
		assertEquals("Test", writer.getFirstname());
		assertEquals("Test", writer.getLastname());
		assertEquals(WriterStatus.ACTIVE, writer.getWriterStatus());
		
		verify(writerRepository, times(1)).save(testWriter);
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
		
		when(writerRepository.update(any(Writer.class))).thenReturn(updatedWriter);
		
		Writer writer = writerService.updateWriter(updatedWriter);
		
		assertNotNull(writer);
		assertEquals("Updated Test", writer.getFirstname());
		assertEquals("Updated Test", writer.getLastname());
		assertEquals(WriterStatus.ACTIVE, writer.getWriterStatus());
		
		verify(writerRepository, times(1)).update(updatedWriter);
	}
	
	@Test
	void testDeleteWriter() {
		writerService.deleteWriter(1L);
		verify(writerRepository, times(1)).deleteById(1L);
	}
}
