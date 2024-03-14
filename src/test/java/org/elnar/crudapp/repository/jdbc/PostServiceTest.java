package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PostServiceTest {
	
	@Mock
	PostService postService = Mockito.mock(PostService.class);
	
	private Post testPost;
	
	
	@BeforeEach
	void setUp()  {
		testPost = Post.builder()
				.id(1L)
				.content("Test content")
				.created(new Date())
				.updated(new Date())
				.postStatus(PostStatus.ACTIVE)
				.build();
	}
	
	@Test
	void  testGetById(){
		when(postService.getPostById(1L)).thenReturn(testPost);
		
		assertNotNull(testPost);
		assertEquals("Test content", testPost.getContent());
		assertEquals(PostStatus.ACTIVE, testPost.getPostStatus());
	}
	
	@Test
	void testGetAllPosts() {
		List<Post> posts = new ArrayList<>();
		posts.add(testPost);
		
		when(postService.getAllPosts()).thenReturn(posts);
		
		List<Post> result = postService.getAllPosts();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test content", result.get(0).getContent());
		assertEquals(PostStatus.ACTIVE, result.get(0).getPostStatus());
	}
	
	@Test
	void testSavePost() {
		when(postService.savePost(testPost)).thenReturn(testPost);
		
		assertNotNull(testPost);
		assertEquals("Test content", testPost.getContent());
		assertEquals(PostStatus.ACTIVE, testPost.getPostStatus());
	}
	
	@Test
	void testUpdatePost() {
		Post updatedPost = Post.builder()
				.id(1L)
				.content("Test content updated")
				.updated(new Date())
				.postStatus(PostStatus.ACTIVE)
				.build();
		
		when(postService.updatePost(updatedPost)).thenReturn(updatedPost);
		
		assertNotNull(updatedPost);
		assertEquals("Test content updated", updatedPost.getContent());
		assertEquals(PostStatus.ACTIVE, updatedPost.getPostStatus());
	}
	
	@Test
	void testDeletePost() {
		postService.deletePost(1L);
		verify(postService, times(1)).deletePost(1L);
	}
}
