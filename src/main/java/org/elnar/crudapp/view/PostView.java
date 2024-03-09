package org.elnar.crudapp.view;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.controller.PostController;
import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class PostView {
	private final PostController postController;
	private final Scanner scanner = new Scanner(System.in);
	
	public void run(){
		boolean running = true;
		
		while (running){
			System.out.println("1. Create Post");
			System.out.println("2. Get Post by ID");
			System.out.println("3. Get All Posts");
			System.out.println("4. Update Post");
			System.out.println("5. Delete Post");
			System.out.println("6. Add label to Post");
			System.out.println("0. Exit");
			System.out.print("Select an option: ");
			
			int option = scanner.nextInt();
			scanner.nextLine();
			
			switch (option){
				case 1 :
					createPost();
					break;
				case 2:
					getPostById();
					break;
				case 3:
					getAllPosts();
					break;
				case 4:
					updatePost();
					break;
				case 5:
					deletePost();
					break;
				case 6:
					addLabelToPost();
					break;
				case 0:
					running = false;
					break;
				default:
					System.out.println("Invalid option. Please try again.");
					break;
			}
		}
	}
	
	private void createPost(){
		System.out.println("Enter content: ");
		String content = scanner.nextLine();
		
		Date created = new Date();
		Date updated = new Date();
		
		PostStatus postStatus = PostStatus.ACTIVE;
		
		postController.createdPost(content, created, updated, postStatus);
		System.out.println("Post created");
	}
	
	private void getPostById(){
		System.out.println("Enter Post id: ");
		long id = scanner.nextLong();
		Post post = postController.getPostById(id);
		System.out.println("Post found: " + post);
	}
	
	public void getAllPosts(){
		List<Post> posts = postController.getAllPosts();
		for(Post post : posts){
			System.out.println(post);
		}
	}
	
	public void updatePost(){
		System.out.println("Enter Post id to update: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		
		System.out.println("Enter update content: ");
		String content = scanner.nextLine();
		
		Date created = new Date();
		
		PostStatus postStatus = PostStatus.ACTIVE;
		
		postController.updatePost(id, content, created, postStatus);
		System.out.println("Post updated");
	}
	
	public void deletePost(){
		System.out.print("Enter post ID to delete: ");
		Long id = scanner.nextLong();
		postController.deletePost(id);
		System.out.println("Post deleted.");
	}
	
	public void addLabelToPost() {
		System.out.print("Enter post ID to add label: ");
		Long postId = scanner.nextLong();
		scanner.nextLine();
		
		System.out.print("Enter label name to add: ");
		String labelName = scanner.nextLine();
		
		Label newLabel = Label.builder()
				.name(labelName)
				.labelStatus(LabelStatus.ACTIVE)
				.build();
		
		postController.addLabelToPost(postId, newLabel);
		System.out.println("Label added to post.");
	}
}

