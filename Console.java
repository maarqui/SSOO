package ssoo;

import java.util.Scanner;

public class Console {
    private FAT fat;

    public Console(FAT fat) {
        this.fat = fat;
    }

    public void printOptions() {
        System.out.println("\nChoose an option:\n");
        System.out.println("   1. Create File");
        System.out.println("   2. Create Directory");
        System.out.println("   3. Delete File");
        System.out.println("   4. Delete Directory");
        System.out.println("   5. Move File or Directory");
        System.out.println("   6. Show data");
        System.out.println("   7. List processes");
        System.out.println("   8. Kill process");
        System.out.println("   9. Start BorraTMPcada5Segundos");
        System.out.println("  10. Exit (kills 'Consola')");
    }

    public void chooseOption() {
        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        int option;

        do {
            printOptions();
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("File size (in clusters): ");
                    int fileSize = sc.nextInt();
                    sc.nextLine();
                    System.out.print("File name: ");
                    String fileName = sc.nextLine();
                    fat.createFile(fileSize, fileName);
                    break;

                case 2:
                    System.out.print("Directory name: ");
                    String dirName = sc.nextLine();
                    fat.createDirectory(dirName);
                    break;

                case 3:
                    System.out.print("File name to delete: ");
                    String deleteFileName = sc.nextLine();
                    var fileToDelete = fat.findFileByName(deleteFileName);
                    if (fileToDelete != null && !fileToDelete.isDirectory()) {
                        fat.deleteFile(fileToDelete);
                    } else {
                        System.out.println("No file found with that name");
                    }
                    break;

                case 4:
                    System.out.print("Directory name to delete: ");
                    String deleteDirName = sc.nextLine();
                    var dirToDelete = fat.findFileByName(deleteDirName);
                    if (dirToDelete != null && dirToDelete.isDirectory()) {
                        fat.deleteFile(dirToDelete);
                    } else {
                        System.out.println("No directory found with that name");
                    }
                    break;

                case 5:
                    fat.moveFile();
                    break;

                case 6:
                    fat.showClusters();
                    System.out.println("\nDirectory tree:");
                    fat.printTree();
                    break;

                case 7:
                    fat.listProcesses();
                    break;

                case 8:
                    System.out.print("PID of process to kill: ");
                    int pid = sc.nextInt();
                    sc.nextLine();
                    boolean result = fat.killProcess(pid);
                    if (result) {
                        System.out.println("Process with PID " + pid + " terminated.");
                    } else {
                        System.out.println("Process not found.");
                    }
                    break;

                case 9:
                    fat.createProcess("BorraTMPcada5Segundos");
                    break;

                case 10:
                    fat.getProcesses().stream()
                        .filter(p -> p.getName().equals("Consola"))
                        .findFirst()
                        .ifPresent(p -> fat.killProcess(p.getPid()));
                    System.out.println("Console process terminated. Exiting...");
                    return;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (true);
    }
}
