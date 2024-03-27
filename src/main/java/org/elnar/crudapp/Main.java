package org.elnar.crudapp;

import org.elnar.crudapp.view.MainView;

public class Main {
	public static void main(String[] args)  {
		ApplicationContext context = new ApplicationContext();
		MainView mainView = new MainView(context);
		mainView.start();
	}
}