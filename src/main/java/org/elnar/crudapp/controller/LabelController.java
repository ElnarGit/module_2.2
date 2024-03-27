package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
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
	
	public void createLabel(Label label){
		labelService.saveLabel(label);
	}
	
	public void updateLabel(Label updateLabel){
		labelService.updateLabel(updateLabel);
	}
	
	public void deleteLabel(Long id){
		labelService.deleteLabel(id);
	}
}
