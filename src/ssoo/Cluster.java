package ssoo;

public class Cluster {
	// Data
	int clusterNumber;
	GenericFile gf;
	// Metadata
	boolean Dis;
	boolean Fin;
	int next;
	// boolean dañado
	boolean isDirectory;
	
	Cluster(int clusterNumber) {
		this.clusterNumber = clusterNumber;
		this.Dis = false;
		this.Fin = false;
		this.next = -1;
	}
	Cluster(int clusterNumber, GenericFile gf) {
		this.clusterNumber = clusterNumber;
		this.gf = gf;
		this.Dis = false;
		this.Fin = false;
		this.next = -1;
	}

	public void printCluster(){
		System.out.println("Cluster[" + clusterNumber + "]: " + gf);
	}
}
