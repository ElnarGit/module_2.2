package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.LabelRepository;
import org.elnar.crudapp.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LabelServiceTest {
	
	@Mock
	private LabelRepository labelRepository;
	
	private LabelService labelService;
	
	private Label testLabel;
	
	
	@BeforeEach
	void setUp()  {
		MockitoAnnotations.openMocks(this);
		
		labelService = new LabelService(labelRepository);
		
		testLabel = Label.builder()
				.id(1L)
				.name("Test name")
				.labelStatus(LabelStatus.ACTIVE)
				.build();
	}
	
	@Test
	void  testGetById(){
		when(labelRepository.getById(anyLong())).thenReturn(testLabel);
		
		Label label = labelService.getLabelById(1L);
		
		assertNotNull(label);
		assertEquals("Test name", label.getName());
		assertEquals(LabelStatus.ACTIVE, label.getLabelStatus());
	}
	
	@Test
	void testGetAllLabels() {
		List<Label> labels = new ArrayList<>();
		labels.add(testLabel);
		
		when(labelRepository.getAll()).thenReturn(labels);
		
		List<Label> result = labelService.getAllLabels();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test name", result.get(0).getName());
		assertEquals(LabelStatus.ACTIVE, result.get(0).getLabelStatus());
	}
	
	@Test
	void testSaveLabel() {
		when(labelRepository.save(any(Label.class))).thenReturn(testLabel);
		
		Label label = labelService.saveLabel(testLabel);
		
		assertNotNull(label);
		assertEquals("Test name", label.getName());
		assertEquals(LabelStatus.ACTIVE, label.getLabelStatus());
		
		verify(labelRepository, times(1)).save(testLabel);
	}
	
	@Test
	void testUpdateLabel() {
		Label updatedLabel = Label.builder()
				.id(1L)
				.name("Test name updated")
				.labelStatus(LabelStatus.ACTIVE)
				.build();
		
		when(labelRepository.update(any(Label.class))).thenReturn(updatedLabel);
		
		Label label = labelService.updateLabel(updatedLabel);
		
		assertNotNull(label);
		assertEquals("Test name updated", label.getName());
		assertEquals(LabelStatus.ACTIVE, label.getLabelStatus());
		
		verify(labelRepository, times(1)).update(updatedLabel);
	}
	
	@Test
	void testDeleteLabel() {
		labelService.deleteLabel(1L);
		verify(labelRepository, times(1)).deleteById(1L);
	}
}
