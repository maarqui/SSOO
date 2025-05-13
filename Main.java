package ssoo;

public class Main {
    public static void main(String[] args) {
        
        /* Group members:
         * 
         * Dario Sarasa
         * Daniel Marquino
         * Alejandro Recasens
         * 
         */
        
        FAT fat = new FAT();
        Console console = new Console(fat);

        console.chooseOption();
    }
}
