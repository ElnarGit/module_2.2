package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.service.WriterService;

import java.util.List;

@RequiredArgsConstructor
public class WriterController {
	private final WriterService writerService;
	
	public Writer getWriterById(Long id){
		return writerService.getWriterById(id);
	}
	
	public List<Writer> getAllWriters(){
		return writerService.getAllWriters();
	}
	
	public void saveWriter(Writer writer){
		writerService.saveWriter(writer);
	}
	
	public void updateWriter(Writer updateWriter){
		writerService.updateWriter(updateWriter);
	}
	
	public void deleteWriterById(Long id){
		writerService.deleteWriter(id);
	}
}
