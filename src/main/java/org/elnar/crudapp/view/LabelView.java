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
			System.out.println("1. Create Label");
			System.out.println("2. Get Label by ID");
			System.out.println("3. Get All Labels");
			System.out.println("4. Update Label");
			System.out.println("5. Delete Label");
			System.out.println("0. Exit");
			System.out.print("Select an option: ");
			
			int option = scanner.nextInt();
			scanner.nextLine();
			
			switch (option){
				case 1 :
					createLabel();
					break;
				case 2:
					getLabelById();
					break;
				case 3:
					getAllLabels();
					break;
				case 4:
					updateLabel();
					break;
				case 5:
					deleteLabel();
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
	
	public void createLabel(){
		System.out.println("Enter name: ");
		String name = scanner.nextLine();
		
		LabelStatus labelStatus = LabelStatus.ACTIVE;
		
		labelController.createLabel(name, labelStatus);
		System.out.println("Label created");
	}
	
	public void getLabelById(){
		System.out.println("Enter Label id: ");
		long id = scanner.nextLong();
		Label label = labelController.getLabelById(id);
		System.out.println("Label found: " + label);
	}
	
	public void getAllLabels(){
		List<Label> labels = labelController.getAllLabels();
		for(Label label : labels){
			System.out.println(label);
		}
	}
	
	public void updateLabel(){
		System.out.println("Enter Label id to update: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		
		System.out.println("Enter update name: ");
		String name = scanner.nextLine();
		
		LabelStatus labelStatus = LabelStatus.ACTIVE;
		
		labelController.updateLabel(id, name, labelStatus);
		System.out.println("Label updated");
	}
	
	public void deleteLabel(){
		System.out.print("Enter label ID to delete: ");
		Long id = scanner.nextLong();
		labelController.deleteLabel(id);
		System.out.println("Label deleted.");
	}
}
