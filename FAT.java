package ssoo;

import java.util.ArrayList;
import java.util.Scanner;

public class FAT {

    Cluster clusters[];  
    RootDir rootDir;     

    FAT() {
        clusters = new Cluster[12]; // Cluster limit set to 12
        for (int i = 0; i < 12; i++) {
            clusters[i] = new Cluster(i);
        }
        rootDir = new RootDir();
    }

    public void createFile(int clustersNeeded, String fileName) {
        Scanner sc = new Scanner(System.in);
        System.out.print("To create file inside a directory press 1, otherwise press 2: ");
        int option = sc.nextInt();
        if (option == 1) {
            createFileInDir(clustersNeeded, fileName);
        } else if (option == 2) { 
            createFileOutDir(clustersNeeded, fileName);
        } else {
            System.out.println("Invalid option");
        }
        sc.close();
    }
    
    public void createFileOutDir(int clustersNeeded, String fileName) {
        int firstAvailableCluster = findFirstAvailableCluster(clustersNeeded);
        if (firstAvailableCluster == -1) {
            System.out.println("Not enough consecutive available clusters.");
            return;
        }
        
        ArrayList<Integer> occupiedClusters = new ArrayList<>();
        int currentCluster = firstAvailableCluster;

        for (int i = 0; i < clustersNeeded; i++) {
            occupiedClusters.add(currentCluster);
            currentCluster++;
        }

        GenericFile file = new GenericFile(firstAvailableCluster, false, clustersNeeded, fileName);
        file.setOccupiedClusters(occupiedClusters);

        for (int clusterId : occupiedClusters) {
            clusters[clusterId].setFile(file);
            clusters[clusterId].setSizeInCluster(1); 
        }

        System.out.println("File '" + fileName + "' created occupying " + clustersNeeded + " clusters starting at cluster " + firstAvailableCluster);
    }

    private int findFirstAvailableCluster(int clustersNeeded) {
        int consecutiveFree = 0;
        for (int i = 0; i < clusters.length; i++) {
            if (!clusters[i].isOccupied()) {
                consecutiveFree++;
                if (consecutiveFree == clustersNeeded) {
                    return i - clustersNeeded + 1;
                }
            } else {
                consecutiveFree = 0;
            }
        }
        return -1; 
    }

    public void createFileInDir(int clustersNeeded, String fileName) {
        // TO BE IMPLEMENTED
    }

    public void createDir(String dirName) {
        int availableCluster = findFirstAvailableCluster(1); 
        if (availableCluster == -1) {
            System.out.println("No available clusters to create the directory.");
            return;
        }
        
        GenericFile dir = new GenericFile(availableCluster, true, dirName); 
        clusters[availableCluster].setFile(dir);  
        System.out.println("Directory '" + dirName + "' created in cluster " + availableCluster);
    }

    public void deleteFile(GenericFile file) {
        System.out.println("Deleting file " + file.getName());
        for (int clusterIndex : file.getOccupiedClusters()) {
            clusters[clusterIndex].clearFile();
        }
        file.delete();
    }

    public void deleteDirectory(GenericFile dir) {
        System.out.println("Deleting directory " + dir.getName());
        clusters[dir.getClusterNumber()].clearFile();
        dir.delete();
    }

    public void showClusters() {
        System.out.println("Listing files:");
        for (int i = 0; i < clusters.length; i++) {
            Cluster cluster = clusters[i];
            System.out.println("Cluster " + i + ":");
            System.out.println(" - Status: " + (cluster.isOccupied() ? "Occupied" : "Free"));

            if (cluster.isOccupied()) {
                GenericFile file = cluster.getFile();
                if (file != null) {
                    System.out.println("    - " + file.getName() + " (" + (file.isDirectory() ? "Directory" : "File") + ")");
                    if (!file.isDirectory()) {
                        ArrayList<Integer> occupiedClusters = file.getOccupiedClusters();
                        int position = occupiedClusters.indexOf(i) + 1;
                        int totalClusters = file.getSize(); 
                        
                        System.out.println("    - Progress: " + position + "/" + totalClusters);
                        System.out.println("    - Total size: " + totalClusters + " clusters");
                    }
                }
            }
        }
    }

    public void moveFile(int origin, int destiny) {
        // TO BE IMPLEMENTED
    }

    public void moveDir(int origin, int destiny) {
        // TO BE IMPLEMENTED
    }
}