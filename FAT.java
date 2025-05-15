package ssoo;

import java.util.*;
import java.util.concurrent.*;

public class FAT {
    private Cluster[] clusters;
    private RootDir rootDir;
    private List<Process> processes;
    private Map<Integer, Thread> processThreads;
    private int nextPid = 1;

    public FAT() {
        clusters = new Cluster[20];
        for (int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster(i);
        }
        rootDir = new RootDir();
        initializeRootDirectory();
        processes = new LinkedList<>();
        //Concurrente para que multiples hilos puedan acceder al mapa a la vez
        processThreads = new ConcurrentHashMap<>();
    }

    private void initializeRootDirectory() {
        ArrayList<Integer> freeClusters = getFreeClusters(1);
        if (freeClusters.isEmpty()) {
            System.out.println("No free clusters available for root directory.");
            return;
        }
        int clusterId = freeClusters.get(0);
        GenericFile root = new GenericFile(clusterId, true, "root");
        rootDir.addFile(root);
        clusters[clusterId].setFile(root);
        clusters[clusterId].setOccupied(true);
        createInitialTmpDirectory(root);
    }

    private void createInitialTmpDirectory(GenericFile parent) {
        ArrayList<Integer> free = getFreeClusters(1);
        if (free.isEmpty()) return;

        int clusterId = free.get(0);
        GenericFile tmp = new GenericFile(clusterId, true, "tmp");
        clusters[clusterId].setFile(tmp);
        clusters[clusterId].setOccupied(true);
        parent.addChild(tmp);
    }

    private ArrayList<Integer> getFreeClusters(int count) {
        ArrayList<Integer> free = new ArrayList<>();
        for (int i = 0; i < clusters.length && free.size() < count; i++) {
            if (!clusters[i].isOccupied()) {
                free.add(i);
            }
        }
        return free;
    }

    public void createFile(int clustersNeeded, String fileName) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the parent directory: ");
        String parentDirName = scanner.next();

        GenericFile parentDir = findFileByName(parentDirName);
        if (parentDir == null || !parentDir.isDirectory()) {
            System.out.println("Directory not found.");
            return;
        }

        ArrayList<Integer> free = getFreeClusters(clustersNeeded);
        if (free.size() < clustersNeeded) {
            System.out.println("Not enough free clusters.");
            return;
        }

        ArrayList<Integer> allocatedClusters = new ArrayList<>(free.subList(0, clustersNeeded));
        GenericFile newFile = new GenericFile(allocatedClusters.get(0), false, clustersNeeded, fileName);
        newFile.setOccupiedClusters(allocatedClusters);

        for (int clusterId : allocatedClusters) {
            clusters[clusterId].setFile(newFile);
            clusters[clusterId].setOccupied(true);
        }

        parentDir.addChild(newFile);
        System.out.println("File '" + fileName + "' created in '" + parentDirName + "'");
    }

    public void createDirectory(String dirName) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the parent directory: ");
        String parentDirName = scanner.next();

        GenericFile parentDir = findFileByName(parentDirName);
        if (parentDir == null || !parentDir.isDirectory()) {
            System.out.println("Parent directory not found.");
            return;
        }

        ArrayList<Integer> free = getFreeClusters(1);
        if (free.isEmpty()) {
            System.out.println("No free clusters available.");
            return;
        }

        int clusterId = free.get(0);
        GenericFile newDir = new GenericFile(clusterId, true, dirName);
        clusters[clusterId].setFile(newDir);
        clusters[clusterId].setOccupied(true);

        parentDir.addChild(newDir);
        System.out.println("Directory '" + dirName + "' created in '" + parentDirName + "'");
    }

    public void deleteFile(GenericFile file) {
        if (file == null)
            return;

        if (file.isDirectory()) {
            for (GenericFile child : new ArrayList<>(file.getChildren())) {
                deleteFile(child);
            }
        }

        for (int cluster : file.getOccupiedClusters()) {
            clusters[cluster].clearFile();
        }

        GenericFile parent = findParentOf(file);
        if (parent != null) {
            parent.getChildren().remove(file);
        } else {
            rootDir.removeFile(file.getName());
        }

        System.out.println("Deleted: " + file.getName());
    }

    public void moveFile() {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file or directory to move: ");
        String fileName = scanner.next();
        GenericFile file = findFileByName(fileName);
        if (file == null) {
            System.out.println("File or directory not found.");
            return;
        }

        System.out.print("Enter the destination directory name: ");
        String destDirName = scanner.next();
        GenericFile destDir = findFileByName(destDirName);
        if (destDir == null || !destDir.isDirectory()) {
            System.out.println("Destination directory not found.");
            return;
        }

        GenericFile parent = findParentOf(file);
        if (parent != null) {
            parent.getChildren().remove(file);
        } else {
            rootDir.removeFile(file.getName());
        }

        destDir.addChild(file);
        System.out.println("Moved '" + fileName + "' to '" + destDirName + "'");
    }

    public void showClusters() {
        System.out.printf("%-8s %-10s %-8s %-10s %-10s %-7s\n", 
            "Cluster", "Disponible", "Dañado", "Reservado", "Siguiente", "Final");
        System.out.println("-----------------------------------------------------------");

        for (int i = 0; i < clusters.length; i++) {
            Cluster c = clusters[i];

            boolean disponible = !c.isOccupied();
            boolean dañado = false;
            boolean reservado = false;

            int siguiente = -1;
            boolean esFinal = false;

            if (c.isOccupied() && c.getFile() != null) {
                GenericFile file = c.getFile();
                List<Integer> ocupados = file.getOccupiedClusters();

                if (file.isDirectory()) {
                    siguiente = -1;
                    esFinal = true;
                } else {
                    int pos = ocupados.indexOf(i);
                    if (pos != -1 && pos < ocupados.size() - 1) {
                        siguiente = ocupados.get(pos + 1);
                        esFinal = false;
                    } else {
                        siguiente = -1;
                        esFinal = true;
                    }
                }
            }

            System.out.printf("%-8d %-10s %-8s %-10s %-10s %-7s\n",
                i, disponible, dañado, reservado, siguiente == -1 ? "-1" : siguiente, esFinal);
        }

        System.out.println("\n=== Cluster Status ===");
        for (int i = 0; i < clusters.length; i++) {
            Cluster c = clusters[i];
            System.out.print("[" + i + "] -> ");
            if (c.isOccupied() && c.getFile() != null) {
                System.out.println(c.getFile().getName() + " (" + (c.getFile().isDirectory() ? "DIR" : "FILE") + ")");
            } else {
                System.out.println("free");
            }
        }
    }


    public void showDirectoryContents(String dirName) {
        GenericFile dir = findFileByName(dirName);
        if (dir == null || !dir.isDirectory()) {
            System.out.println("Directory not found.");
            return;
        }

        System.out.println("Contents of '" + dir.getName() + "':");
        for (GenericFile child : dir.getChildren()) {
            System.out.println(" - " + child.getName() + (child.isDirectory() ? " [DIR]" : ""));
        }
    }

    public void printTree() {
        for (GenericFile rootFile : rootDir.getAllFiles()) {
            printRecursive(rootFile, "");
        }
    }

    private void printRecursive(GenericFile file, String indent) {
        System.out.println(indent + "- " + file.getName() + (file.isDirectory() ? " [DIR]" : ""));
        if (file.isDirectory()) {
            for (GenericFile child : file.getChildren()) {
                printRecursive(child, indent + "  ");
            }
        }
    }

    public GenericFile findFileByName(String name) {
        for (GenericFile root : rootDir.getAllFiles()) {
            GenericFile result = searchRecursive(root, name);
            if (result != null)
                return result;
        }
        return null;
    }

    private GenericFile searchRecursive(GenericFile file, String name) {
        if (file.getName().equals(name))
            return file;
        if (file.isDirectory()) {
            for (GenericFile child : file.getChildren()) {
                GenericFile result = searchRecursive(child, name);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public GenericFile findParentOf(GenericFile target) {
        for (GenericFile root : rootDir.getAllFiles()) {
            GenericFile parent = findParentRecursive(root, target);
            if (parent != null)
                return parent;
        }
        return null;
    }

    private GenericFile findParentRecursive(GenericFile current, GenericFile target) {
        if (!current.isDirectory())
            return null;

        for (GenericFile child : current.getChildren()) {
            if (child == target)
                return current;

            GenericFile result = findParentRecursive(child, target);
            if (result != null)
                return result;
        }
        return null;
    }

    public void listProcesses() {
        if (processes.isEmpty()) {
            System.out.println("No running processes.");
            return;
        }
        System.out.println("Running processes:");
        for (Process p : processes) {
            System.out.println(p);
        }
    }

    public void createProcess(String name) {
        Process p = new Process(nextPid++, name);
        processes.add(p);
        
        //Podria hacerse directamente, relamente pasarle parametro y ver si es equals es innecesario
        if (name.equals("BorraTMPcada5Segundos")) {
        	//En vez de hacer un nuevo hilo Runnable usamos un lambda que es mas facil de leer
            Thread t = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        GenericFile tmp = findFileByName("tmp");
                        if (tmp != null && tmp.isDirectory() && !tmp.getChildren().isEmpty()) {
                            deleteFile(tmp.getChildren().get(0));
                        }
                        Thread.sleep(5000);
                    }
                } catch (InterruptedException e) {
                    System.out.println("[BorraTMP] Process interrupted.");
                }
            });
            t.start();
            processThreads.put(p.getPid(), t);
        }

        System.out.println("Process created: " + p);
    }

    public boolean killProcess(int pid) {
        for (Process p : processes) {
            if (p.getPid() == pid) {
                processes.remove(p);
                Thread t = processThreads.get(pid);
                if (t != null) {
                    t.interrupt();
                    processThreads.remove(pid);
                }
                return true;
            }
        }
        return false;
    }

    public List<Process> getProcesses() {
        return processes;
    }
}
