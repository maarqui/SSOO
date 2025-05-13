package ssoo;

public class MyFile extends GenericFile {
    String name;
    int clusterNumber;

    public MyFile(String name, int clusterNumber) {
        super(clusterNumber);
        this.name = name;
    }
    
    public MyFile(String path) {
        super(path);
    }

    public MyFile(int clusterNumber) {
        super(clusterNumber);
    }
}
