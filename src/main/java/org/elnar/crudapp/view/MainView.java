package org.elnar.crudapp.view;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.ApplicationContext;

import java.util.Scanner;

@RequiredArgsConstructor
public class MainView {
	private final ApplicationContext applicationContext;
	private final Scanner scanner = new Scanner(System.in);
	
	public void start() {
		System.out.println("Выберите опцию:");
		System.out.println("1. Писатель");
		System.out.println("2. Пост");
		System.out.println("3. Метка");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch (choice) {
			case 1 -> applicationContext.writerRun();
			case 2 -> applicationContext.postRun();
			case 3 -> applicationContext.labelRun();
			default -> System.out.println("Неверный выбор");
		}
	}
}
