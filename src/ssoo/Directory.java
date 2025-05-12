package ssoo;

import java.util.ArrayList;
import java.util.List;

public class Directory extends GenericFile {
    String name;
    int clusterNumber;
    List<GenericFile> content = new ArrayList<GenericFile>();

    public Directory(String name, int clusterNumber) {
        super(clusterNumber);
        this.name = name;
    }

    public Directory(String name, int clusterNumber, List<GenericFile> content) {
        super(clusterNumber);
        this.name = name;
        this.content = content;
    }
    
    public void addFile(GenericFile file) {
        content.add(file);
    }
    public void removeFile(GenericFile file) {
        content.remove(file);
    }
}