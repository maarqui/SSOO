package ssoo;

public class Consola {

	FAT fat;
	Data data;

	public Consola(FAT fat, Data data) {
		this.fat = fat;
		this.data = data;
	}

	public void imprimirOpciones() {
		System.out.println("\nEscoja una opcion:\n ");
		System.out.println("   1.Crear Archivo");
		System.out.println("   2.Crear Directorio");
		System.out.println("   3.Run Starting Code");
		System.out.println("   4.Eliminar Archivo");
		System.out.println("   5.Eliminar Directorio");
		System.out.println("   6.Mover Archivo");
		System.out.println("   7.Mostrar directorio");
	}

	public void analizaOpcion(int opcion) {
		switch (opcion) {
		case 1:
			fat.createFile();
			break;
		case 2:
			fat.createDir();
			break;
		case 3:
			fat.runStartingCode();
			break;
		case 4:
			fat.deleteFile();
			break;
		case 5:
			fat.deleteDirectory();
			break;
		case 6:
			data.moveFile();
			break;
		case 7:
			data.moveDir();
			break;
		default:
			System.out.println("Introduzca una opcion valida");
		}

	}
}
