package org.elnar.crudapp.view;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.controller.WriterController;
import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class WriterView {
	private final WriterController writerController;
	private final Scanner scanner = new Scanner(System.in);
	
	public void run(){
		boolean running = true;
		
		while (running){
			System.out.println("1. Create Writer");
			System.out.println("2. Get Writer by ID");
			System.out.println("3. Get All Writers");
			System.out.println("4. Update Writer");
			System.out.println("5. Delete Writer");
			System.out.println("6. Add post to Writer");
			System.out.println("0. Exit");
			System.out.print("Select an option: ");
			
			int option = scanner.nextInt();
			scanner.nextLine();
			
			switch (option){
				case 1 :
					createWriter();
					break;
				case 2:
					getWriterById();
					break;
				case 3:
					getAllWriters();
					break;
				case 4:
					updateWriter();
					break;
				case 5:
					deleteWriter();
					break;
				case 6:
					addPostToWriter();
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
	
	public void createWriter(){
		System.out.println("Enter firstname: ");
		String firstname = scanner.nextLine();
		
		System.out.println("Enter lastname: ");
		String lastname = scanner.nextLine();
		
		List<Post> posts = new ArrayList<>();
		
		WriterStatus writerStatus = WriterStatus.ACTIVE;
		
		writerController.saveWriter(firstname, lastname, posts, writerStatus);
		System.out.println("Writer created");
	}
	
	public void getWriterById(){
		System.out.println("Enter Writer id: ");
		Long id = scanner.nextLong();
		Writer writer = writerController.getWriterById(id);
		System.out.println("Writer found: " + writer);
	}
	
	public void getAllWriters(){
		List<Writer> writers = writerController.getAllWriters();
		for(Writer writer : writers){
			System.out.println(writer);
		}
	}
	
	public void updateWriter(){
		System.out.println("Enter Writer id to update: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		
		System.out.println("Enter update firstname: ");
		String firstname = scanner.nextLine();
		
		System.out.println("Enter update lastname: ");
		String lastname = scanner.nextLine();
		
		List<Post> posts = new ArrayList<>();
		
		WriterStatus writerStatus = WriterStatus.ACTIVE;
		
		writerController.updateWriter(id, firstname, lastname, posts, writerStatus);
		System.out.println("Writer updated");
	}
	
	public void deleteWriter(){
		System.out.print("Enter writer ID to delete: ");
		Long id = scanner.nextLong();
		writerController.deleteWriterById(id);
		System.out.println("Writer deleted.");
	}
	
	public void addPostToWriter() {
		System.out.print("Enter writer ID to add post: ");
		Long writerId = scanner.nextLong();
		scanner.nextLine();
		
		Writer writer = writerController.getWriterById(writerId);
		
		System.out.print("Enter post content: ");
		String content = scanner.nextLine();
		
		Post newPost = Post.builder()
				.content(content)
				.created(new Date())
				.updated(new Date())
				.postStatus(PostStatus.ACTIVE)
				.writerId(writer)
				.build();
		
		writerController.addPostToWriter(writerId, newPost);
		System.out.println("Post added to writer.");
	}
}
