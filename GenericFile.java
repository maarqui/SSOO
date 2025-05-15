package ssoo;

import java.util.ArrayList;

public class GenericFile {
    private String name;
    private boolean directory;
    private int clusterNumber;
    private ArrayList<Integer> occupiedClusters;
    private ArrayList<GenericFile> children;
    private int size;

    public GenericFile(int clusterNumber, boolean directory, int size, String name) {
        this.clusterNumber = clusterNumber;
        this.directory = directory;
        this.name = (name != null) ? name : "Cluster_" + clusterNumber;
        this.occupiedClusters = new ArrayList<>();
        this.size = size;
        this.occupiedClusters.add(clusterNumber);
        if (directory) {
            this.children = new ArrayList<>();
        }
    }

    public GenericFile(int clusterNumber, boolean directory, String name) {
        this(clusterNumber, directory, 0, name);
    }

    public GenericFile(String name) {
        this.name = name;
        this.directory = name.endsWith("/");
        this.occupiedClusters = new ArrayList<>();
        if (directory) {
            this.children = new ArrayList<>();
        }
    }

    public GenericFile(int clusterNumber) {
        this(clusterNumber, false, 0, "Cluster_" + clusterNumber);
    }

    public boolean isDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }

    public void renameTo(GenericFile dest) {
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

    public void addChild(GenericFile child) {
        if (directory && children != null) {
            children.add(child);
        }
    }

    public ArrayList<GenericFile> getChildren() {
        return children;
    }

    public GenericFile getChildByName(String name) {
        if (!directory || children == null) return null;
        for (GenericFile child : children) {
            if (child.getName().equals(name)) return child;
        }
        return null;
    }
}
