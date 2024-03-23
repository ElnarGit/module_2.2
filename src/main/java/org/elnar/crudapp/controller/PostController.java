package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.service.PostService;

import java.util.List;

@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	public Post getPostById(Long id){
		return postService.getPostById(id);
	}
	
	public List<Post> getAllPosts(){
		return postService.getAllPosts();
	}
	
	public void createdPost(Post post){
		postService.savePost(post);
	}
	
	public void updatePost(Post updatePost){
		postService.updatePost(updatePost);
	}
	
	public void deletePost(Long id){
		postService.deletePost(id);
	}
}
