package ssoo;

public class File extends GenericFile {
    String name;
    int clusterNumber;

    File(String name, int clusterNumber) {
        super(clusterNumber);
        this.name = name;
    }

}