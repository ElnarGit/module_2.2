package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Post;
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
	
	public void saveWriter(String firstname, String lastname,
						   List<Post> posts, WriterStatus writerStatus){
		Writer newWriter = Writer.builder()
				.firstname(firstname)
				.lastname(lastname)
				.posts(posts)
				.writerStatus(writerStatus)
				.build();
		writerService.saveWriter(newWriter);
	}
	
	public void updateWriter(Long id,String firstname, String lastname,
							 List<Post> posts, WriterStatus writerStatus){
		Writer updateWriter = Writer.builder()
				.id(id)
				.firstname(firstname)
				.lastname(lastname)
				.posts(posts)
				.writerStatus(writerStatus)
				.build();
		
		writerService.updateWriter(updateWriter);
	}
	
	public void deleteWriterById(Long id){
		writerService.deleteWriter(id);
	}
	
	public void addPostToWriter(Long writerId, Post post){
		writerService.addPostToWriter(writerId, post);
	}
}
