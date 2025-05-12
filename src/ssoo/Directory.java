package ssoo;

import java.util.ArrayList;
import java.util.List;

public class Directory extends GenericFile{
    String nombre;
    int clusterNumber;
    List<GenericFile> content = new ArrayList<GenericFile>();
    
}
