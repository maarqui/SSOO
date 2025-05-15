package ssoo;

public class MyFile extends GenericFile {
    public MyFile(String name, int clusterNumber) {
        super(clusterNumber);
    }

    public MyFile(String path) {
        super(path);
    }

    public MyFile(int clusterNumber) {
        super(clusterNumber);
    }
}
