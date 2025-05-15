package ssoo;

import java.util.ArrayList;
import java.util.List;

public class MyDirectory extends GenericFile {
    private List<GenericFile> content = new ArrayList<>();

    public MyDirectory(String name, int clusterNumber) {
        super(clusterNumber);
    }

    public MyDirectory(String name, int clusterNumber, List<GenericFile> content) {
        super(clusterNumber);
        this.content = content;
    }

    public void addFile(GenericFile file) {
        content.add(file);
    }

    public void removeFile(GenericFile file) {
        content.remove(file);
    }
}
