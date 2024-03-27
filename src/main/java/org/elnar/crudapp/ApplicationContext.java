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
	
	public void writerRun() {
		writerView.run();
	}
	
	public void postRun() {
		postView.run();
	}
	
	public void labelRun() {
		labelView.run();
	}
}
