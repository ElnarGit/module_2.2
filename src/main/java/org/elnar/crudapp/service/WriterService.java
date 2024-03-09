package org.elnar.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.repository.WriterRepository;

import java.util.List;

@RequiredArgsConstructor
public class WriterService {
	private final WriterRepository writerRepository;
	private final PostRepository postRepository;
	
	public Writer getWriterById(Long id){
		return writerRepository.getById(id);
	}
	
	public List<Writer> getAllWriters(){
		return writerRepository.getAll();
	}
	
	public Writer saveWriter(Writer writer){
		return writerRepository.save(writer);
	}
	
	public Writer updateWriter(Writer writer){
		return writerRepository.update(writer);
	}
	
	public void deleteWriter(Long id){
		writerRepository.deleteById(id);
	}
	
	public void addPostToWriter(Long writerId, Post post){
		Writer writer = writerRepository.getById(writerId);
		post.setWriterId(writer);
		postRepository.save(post);
	}
}
