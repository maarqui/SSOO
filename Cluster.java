package ssoo;


public class Cluster {
    int clusterNumber;
    GenericFile file;
    boolean isOccupied;
    int sizeInCluster; 

    Cluster(int clusterNumber) {
        this.clusterNumber = clusterNumber;
        this.isOccupied = false;
        this.file = null;
        this.sizeInCluster = 0; 
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setFile(GenericFile file) {
        this.file = file;
        this.isOccupied = true;
    }

    public void setSizeInCluster(int sizeInCluster) {
        this.sizeInCluster = sizeInCluster;
    }

    public GenericFile getFile() {
        return file;
    }

    public void clearFile() {
        this.file = null;
        this.isOccupied = false;
        this.sizeInCluster = 0;  
    }

 
}


