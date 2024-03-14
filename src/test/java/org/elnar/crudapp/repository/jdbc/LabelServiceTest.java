package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LabelServiceTest {
	
	@Mock
	LabelService labelService = Mockito.mock(LabelService.class);
	
	private Label testLabel;
	
	
	@BeforeEach
	void setUp()  {
		testLabel = Label.builder()
				.id(1L)
				.name("Test name")
				.labelStatus(LabelStatus.ACTIVE)
				.build();
	}
	
	@Test
	void  testGetById(){
		when(labelService.getLabelById(1L)).thenReturn(testLabel);
		
		assertNotNull(testLabel);
		assertEquals("Test name", testLabel.getName());
		assertEquals(LabelStatus.ACTIVE, testLabel.getLabelStatus());
	}
	
	@Test
	void testGetAllLabels() {
		List<Label> labels = new ArrayList<>();
		labels.add(testLabel);
		
		when(labelService.getAllLabels()).thenReturn(labels);
		
		List<Label> result = labelService.getAllLabels();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test name", result.get(0).getName());
		assertEquals(LabelStatus.ACTIVE, result.get(0).getLabelStatus());
	}
	
	@Test
	void testSaveLabel() {
		when(labelService.savelabel(testLabel)).thenReturn(testLabel);
		
		assertNotNull(testLabel);
		assertEquals("Test name", testLabel.getName());
		assertEquals(LabelStatus.ACTIVE, testLabel.getLabelStatus());
	}
	
	@Test
	void testUpdateLabel() {
		Label updatedLabel = Label.builder()
				.id(1L)
				.name("Test name updated")
				.labelStatus(LabelStatus.ACTIVE)
				.build();
		
		when(labelService.updateLabel(updatedLabel)).thenReturn(updatedLabel);
		
		assertNotNull(updatedLabel);
		assertEquals("Test name updated", updatedLabel.getName());
		assertEquals(LabelStatus.ACTIVE, updatedLabel.getLabelStatus());
	}
	
	@Test
	void testDeleteLabel() {
		labelService.deleteLabel(1L);
		verify(labelService, times(1)).deleteLabel(1L);
	}
}
