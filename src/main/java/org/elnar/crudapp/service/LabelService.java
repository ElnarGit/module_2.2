package org.elnar.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.LabelRepository;

import java.util.List;

@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;
	
	public Label getLabelById(Long id){
		return labelRepository.getById(id);
	}
	
	public List<Label> getAllLabels(){
		return labelRepository.getAll();
	}
	
	public Label saveLabel(Label label){
		return labelRepository.save(label);
	}
	
	public Label updateLabel(Label label){
		return labelRepository.update(label);
	}
	
	public void deleteLabel(Long id){
		labelRepository.deleteById(id);
	}
}
