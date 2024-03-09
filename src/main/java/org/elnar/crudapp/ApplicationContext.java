package org.elnar.crudapp;

import org.elnar.crudapp.controller.LabelController;
import org.elnar.crudapp.controller.PostController;
import org.elnar.crudapp.controller.WriterController;
import org.elnar.crudapp.repository.LabelRepository;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.repository.WriterRepository;
import org.elnar.crudapp.repository.jdbc.JdbcLabelRepositoryImpl;
import org.elnar.crudapp.repository.jdbc.JdbcPostRepositoryImpl;
import org.elnar.crudapp.repository.jdbc.JdbcWriterRepositoryImpl;
import org.elnar.crudapp.service.LabelService;
import org.elnar.crudapp.service.PostService;
import org.elnar.crudapp.service.WriterService;
import org.elnar.crudapp.view.LabelView;
import org.elnar.crudapp.view.PostView;
import org.elnar.crudapp.view.WriterView;

import java.util.Scanner;

public class ApplicationContext {
	Scanner scanner = new Scanner(System.in);
	
	public void start() {
		System.out.println("Select an option:");
		System.out.println("1. Writer");
		System.out.println("2. Post");
		System.out.println("3. Label");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch (choice) {
			case 1:
				writerRun();
				break;
			case 2:
				postRun();
				break;
			case 3:
				labelRun();
				break;
			default:
				System.out.println("Invalid choice");
		}
	}
	
	public void writerRun() {
		WriterRepository writerRepository = new JdbcWriterRepositoryImpl();
		PostRepository postRepository = new JdbcPostRepositoryImpl();
		
		WriterService writerService = new WriterService(writerRepository, postRepository);
		
		WriterController writerController = new WriterController(writerService);
		WriterView writerView = new WriterView(writerController);
		writerView.run();
	}
	
	public void postRun() {
		PostRepository postRepository = new JdbcPostRepositoryImpl();
		JdbcPostRepositoryImpl jdbcPostRepository = new JdbcPostRepositoryImpl();
		PostService postService = new PostService(postRepository, jdbcPostRepository);
		
		PostController postController = new PostController(postService);
		PostView postView = new PostView(postController);
		postView.run();
	}
	
	public void labelRun() {
		LabelRepository labelRepository = new JdbcLabelRepositoryImpl();
		
		LabelService labelService = new LabelService(labelRepository);
		
		LabelController labelController = new LabelController(labelService);
		LabelView labelView = new LabelView(labelController);
		labelView.run();
	}
	
}
