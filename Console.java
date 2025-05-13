package ssoo;

import java.util.Scanner;

public class Console {
	FAT fat;

	public Console(FAT fat) {
		this.fat = fat;
	}

	public void printOptions() {
		System.out.println("\nEscoja una opción:\n ");
		System.out.println("   1. Create File");
		System.out.println("   2. Create Directory");
		System.out.println("   3. Delete File");
		System.out.println("   4. Delete Directory");
		System.out.println("   5. Move File");
		System.out.println("   6. Move Directory");
		System.out.println("   7. Show data");
		System.out.println("   8. Exit");
	}

	public void chooseOption() {
		Scanner sc = new Scanner(System.in);
		int option;

		do {
			printOptions();
			System.out.print("Choose an option: ");
			option = sc.nextInt();
			sc.nextLine(); 

			switch (option) {
			case 1:
				System.out.print("File size: ");
				int fileSize = sc.nextInt();
				sc.nextLine(); 
				System.out.print("File name: ");
				String fileName = sc.nextLine();
				fat.createFile(fileSize, fileName); 
				break;
			case 2:
				System.out.print("Directory name: ");
				String dirName = sc.nextLine();
				fat.createDir(dirName); 
				break;
			case 3:
				System.out.print("File cluster to delete: ");
				int deleteClusterFile = sc.nextInt();
				fat.deleteFile(new GenericFile(deleteClusterFile)); 
				break;
			case 4:
				System.out.print("Directory cluster to delete: ");
				int deleteClusterDir = sc.nextInt();
				fat.deleteDirectory(new GenericFile(deleteClusterDir)); 
				break;
			case 5:
				System.out.print("Cluster origin(file): ");
				int originFileCluster = sc.nextInt();
				System.out.print("Cluster destiny: ");
				int destinyFileCluster = sc.nextInt();
				fat.moveFile(originFileCluster, destinyFileCluster);
				break;
			case 6: 
				System.out.print("Cluster origin(directory): ");
				int originClusterDir = sc.nextInt();
				System.out.print("Cluster destiny: ");
				int destinyClusterDir = sc.nextInt();
				fat.moveDir(originClusterDir, destinyClusterDir);
				break;
			case 7:
				fat.showClusters();
				break;
			case 8:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Inavlid option");
				break;
			}
		} while (option != 8);
		sc.close();
	}
}
