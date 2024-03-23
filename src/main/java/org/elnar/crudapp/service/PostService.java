package org.elnar.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	
	public Post getPostById(Long id){
		return postRepository.getById(id);
	}
	
	public List<Post> getAllPosts(){
		return postRepository.getAll();
	}
	
	public Post savePost(Post post){
		return postRepository.save(post);
	}
	
	public Post updatePost(Post post){
		return postRepository.update(post);
	}
	
	public void deletePost(Long id){
		postRepository.deleteById(id);
	}
}
