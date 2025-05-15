package ssoo;

public class Cluster {
    private GenericFile file;
    private boolean occupied;

    public Cluster(int clusterNumber) {
        this.occupied = false;
        this.file = null;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setFile(GenericFile file) {
        this.file = file;
        this.occupied = true;
    }

    public GenericFile getFile() {
        return file;
    }

    public void clearFile() {
        this.file = null;
        this.occupied = false;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
