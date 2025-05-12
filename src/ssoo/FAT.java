package ssoo;

public class FAT {
	
	Cluster clusters[];
	RootDir rootDir;

	FAT(){
		clusters = new Cluster[0];
		rootDir = new RootDir();
	}
	
	FAT(int size) {
		clusters = new Cluster[size];
		for (int i = 0; i < size; i++) {
			clusters[i] = new Cluster(i);
		}
		rootDir = new RootDir();
	}

	public void runStartingCode() {

	}

	public void createDir() {
		
	}

	public void createFile() {

	}

	public void deleteFile() {

	}

	public void deleteDirectory() {

	}

	public void showMetadata() {
		
	}

	public void listProces() {

	}

	public void launchProces() {

	}

	public void killProces() {

	}
	
	public void moveFile() {
		
	}
	
	public void moveDir() {
		
	}
	
	public void fillCluster() {
		
	}
	
	
}