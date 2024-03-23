package org.elnar.crudapp.view;

import lombok.RequiredArgsConstructor;
import org.elnar.crudapp.controller.LabelController;
import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class LabelView {
	private final LabelController labelController;
	private final Scanner scanner = new Scanner(System.in);
	
	public void run(){
		boolean running = true;
		
		while (running){
			System.out.println("1. Создать метку");
			System.out.println("2. Получить метку по ID");
			System.out.println("3. Получить все метки");
			System.out.println("4. Обновить метку");
			System.out.println("5. Удалить метку");
			System.out.println("0. Выйти");
			System.out.print("Выберите опцию: ");
			
			int option = scanner.nextInt();
			scanner.nextLine();
			
			switch (option) {
				case 1 -> createLabel();
				case 2 -> getLabelById();
				case 3 -> getAllLabels();
				case 4 -> updateLabel();
				case 5 -> deleteLabel();
				case 0 -> running = false;
				default -> System.out.println("Неверная опция. Пожалуйста, попробуйте еще раз.");
			}
		}
	}
	
	private void createLabel(){
		System.out.println("Введите название: ");
		String name = scanner.nextLine();
		
		Label createdLabel = Label.builder()
				.name(name)
				.labelStatus(LabelStatus.ACTIVE)
				.build();
		
		labelController.createLabel(createdLabel);
		System.out.println("Метка создана");
	}
	
	private void getLabelById(){
		System.out.println("Введите ID метки: ");
		Long id = scanner.nextLong();
		Label label = labelController.getLabelById(id);
		System.out.println("Найденная метка: " + label);
	}
	
	private void getAllLabels(){
		List<Label> labels = labelController.getAllLabels();
		for(Label label : labels){
			System.out.println(label);
		}
	}
	
	private void updateLabel(){
		System.out.println("Введите ID метки для обновления: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		
		System.out.println("Введите обновленное название: ");
		String name = scanner.nextLine();
		
		Label updatedLabel = Label.builder()
				.id(id)
				.name(name)
				.labelStatus(LabelStatus.ACTIVE)
				.build();
		
		labelController.updateLabel(updatedLabel);
		System.out.println("Метка обновлена");
	}
	
	private void deleteLabel(){
		System.out.print("Введите ID метки для удаления: ");
		Long id = scanner.nextLong();
		labelController.deleteLabel(id);
		System.out.println("Метка удалена.");
	}
}
