package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.WriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JdbcWriterRepositoryImplTest {
	
	@Mock
	WriterRepository writerRepository = Mockito.mock(WriterRepository.class);
	
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
		when(writerRepository.getById(1L)).thenReturn(testWriter);
		
		assertNotNull(testWriter);
		assertEquals("Test", testWriter.getFirstname());
		assertEquals("Test", testWriter.getLastname());
		assertEquals(WriterStatus.ACTIVE, testWriter.getWriterStatus());
	}
	
	@Test
	void testGetAllWriters() {
		List<Writer> writers = new ArrayList<>();
		writers.add(testWriter);
		
		when(writerRepository.getAll()).thenReturn(writers);
		
		List<Writer> result = writerRepository.getAll();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test", result.get(0).getFirstname());
		assertEquals("Test", result.get(0).getLastname());
		assertEquals(WriterStatus.ACTIVE, result.get(0).getWriterStatus());
	}
	
	@Test
	 void testSaveWriter() {
		when(writerRepository.save(testWriter)).thenReturn(testWriter);
		
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
		
		when(writerRepository.update(updatedWriter)).thenReturn(updatedWriter);
		
		assertNotNull(updatedWriter);
		assertEquals("Updated Test", updatedWriter.getFirstname());
		assertEquals("Updated Test", updatedWriter.getLastname());
		assertEquals(WriterStatus.ACTIVE, updatedWriter.getWriterStatus());
	}
	
	@Test
	void testDeleteWriter() {
		writerRepository.deleteById(1L);
		verify(writerRepository, times(1)).deleteById(1L);
	}
}
