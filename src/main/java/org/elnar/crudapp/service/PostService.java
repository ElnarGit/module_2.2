package org.elnar.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.repository.jdbc.JdbcPostRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final JdbcPostRepositoryImpl jdbcPostRepository;
	
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
	
	public void addLabelToPost(Long postId, Label label){
		Post post = postRepository.getById(postId);
		post.addLabel(label);
		jdbcPostRepository.addLabelToPost(postId, label);
		//postRepository.update(post);
	}
}
