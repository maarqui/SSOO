package ssoo;

public class RootDir {
    private GenericFile root;

    public RootDir() {
        root = new GenericFile(0, true, "root");
    }

    public GenericFile getRoot() {
        return root;
    }
}
