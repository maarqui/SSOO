package ssoo;

import java.util.ArrayList;

public class RootDir {
    private ArrayList<GenericFile> rootFiles;

    public RootDir() {
        this.rootFiles = new ArrayList<>();
    }

    public void addFile(GenericFile file) {
        rootFiles.add(file);
    }

    public void removeFile(String name) {
        rootFiles.removeIf(f -> f.getName().equals(name));
    }

    public GenericFile getFileByName(String name) {
        for (GenericFile f : rootFiles) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<GenericFile> getAllFiles() {
        return rootFiles;
    }
}
