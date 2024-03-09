package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.service.LabelService;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
	private final LabelService labelService;
	
	public Label getLabelById(Long id){
		return labelService.getLabelById(id);
	}
	
	public List<Label> getAllLabels(){
		return labelService.getAllLabels();
	}
	
	public void createLabel(String name, LabelStatus labelStatus){
		Label newLabel = Label.builder()
				.name(name)
				.labelStatus(labelStatus)
				.build();
		
		labelService.savelabel(newLabel);
	}
	
	public void updateLabel(Long id, String name, LabelStatus labelStatus){
		Label updateLabel = Label.builder()
				.id(id)
				.name(name)
				.labelStatus(labelStatus)
				.build();
		
		labelService.updateLabel(updateLabel);
	}
	
	public void deleteLabel(Long id){
		labelService.deleteLabel(id);
	}
}
