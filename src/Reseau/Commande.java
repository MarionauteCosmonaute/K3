package Reseau;

public class Commande {
    String[] command;

    Commande(String command){
        this.command = command.split(" ");
    }

    public void translate(){
        switch (command[0]) {
            case "1":
                
                break;
        
            default:
                throw new IllegalArgumentException("Invalid command: " + command[0]);
        }
    }
}
