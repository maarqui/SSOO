package ssoo;

import java.util.ArrayList;

public class GenericFile {
    private String name;
    private boolean isDirectory;
    private int clusterNumber;  
    private ArrayList<Integer> occupiedClusters;  
    private int size;

    public GenericFile(int clusterNumber, boolean isDirectory, int size, String name) {
        this.clusterNumber = clusterNumber;
        this.isDirectory = isDirectory;
        this.name = name != null ? name : "Cluster_" + clusterNumber;
        this.occupiedClusters = new ArrayList<>();
        this.size = size;
        this.occupiedClusters.add(clusterNumber); 
    }

    public GenericFile(int clusterNumber, boolean isDirectory, String name) {
        this(clusterNumber, isDirectory, 0, name);
    }

    public GenericFile(String name) {
        this.name = name;
        this.isDirectory = name.endsWith("/");  
        this.occupiedClusters = new ArrayList<>();
    }

    public GenericFile(int clusterNumber) {
        this.clusterNumber = clusterNumber;
        this.name = "Cluster_" + clusterNumber; 
        this.isDirectory = false; 
        this.occupiedClusters = new ArrayList<>();
        this.occupiedClusters.add(clusterNumber); 
        this.size = 0; 
    }

    public void delete() {
        System.out.println("Deleting " + name);
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public String getName() {
        return name;
    }

    public void renameTo(GenericFile dest) {
        System.out.println("Moving " + name + " to " + dest.getName());
        this.name = dest.getName();  
    }

    public void setOccupiedClusters(ArrayList<Integer> clusters) {
        this.occupiedClusters = clusters;
    }

    public ArrayList<Integer> getOccupiedClusters() {
        return occupiedClusters;
    }

    public int getSize() {
        return size;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }
}