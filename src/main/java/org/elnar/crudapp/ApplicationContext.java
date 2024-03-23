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
	private final WriterRepository writerRepository = new JdbcWriterRepositoryImpl();
	private final PostRepository postRepository = new JdbcPostRepositoryImpl();
	private final LabelRepository labelRepository = new JdbcLabelRepositoryImpl();
	
	private final WriterService writerService = new WriterService(writerRepository);
	private final PostService postService = new PostService(postRepository);
	private final LabelService labelService = new LabelService(labelRepository);
	
	private final WriterController writerController = new WriterController(writerService);
	private final PostController postController = new PostController(postService);
	private final LabelController labelController = new LabelController(labelService);
	
	private final WriterView writerView = new WriterView(writerController, postController);
	private final PostView postView = new PostView(postController, writerController, labelController);
	private final LabelView labelView = new LabelView(labelController);
	
	private final Scanner scanner = new Scanner(System.in);
	
	public void start() {
		System.out.println("Выберите опцию:");
		System.out.println("1. Писатель");
		System.out.println("2. Пост");
		System.out.println("3. Метка");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch (choice) {
			case 1 -> writerRun();
			case 2 -> postRun();
			case 3 -> labelRun();
			default -> System.out.println("Неверный выбор");
		}
	}
	
	private void writerRun() {
		writerView.run();
	}
	
	private void postRun() {
		postView.run();
	}
	
	private void labelRun() {
		labelView.run();
	}
}
