package ssoo;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		/*Integrantes grupo:
		 * 
		 * Dario Sarasa
		 * Daniel Marquino
		 * Alejandro Recasens
		 * 
		 */
		
		FAT fat = new FAT();
		Consola consola = new Consola(fat);

		Scanner input = new Scanner(System.in);

		consola.imprimirOpciones();
		System.out.print("Ingrese una opción: ");
		int opcion = input.nextInt();
		consola.analizaOpcion(opcion);
	}
}