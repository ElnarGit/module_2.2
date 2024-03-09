package org.elnar.crudapp.controller;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.service.PostService;

import java.util.Date;
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
	
	public void createdPost(String content, Date created, Date updated, PostStatus postStatus){
		Post newPost = Post.builder()
				.content(content)
				.created(created)
				.updated(updated)
				.postStatus(postStatus)
				.build();
		
		postService.savePost(newPost);
	}
	
	public void updatePost(Long id, String content, Date updated, PostStatus postStatus){
		Post updatedPost = Post.builder()
				.id(id)
				.content(content)
				.updated(updated)
				.postStatus(postStatus)
				.build();
		
		postService.updatePost(updatedPost);
	}
	
	public void deletePost(Long id){
		postService.deletePost(id);
	}
	
	public void addLabelToPost(Long postId, Label label){
		postService.addLabelToPost(postId, label);
	}
}
