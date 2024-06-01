import Model.*;
import java.util.Scanner;

public class PlayerTest {
    private static void  addBag(Player player, Cube cube){
        player.addBag(cube);
    }
    private static void  addSide(Player player, Cube cube){
        player.addSide(cube);
    }

    private static void aleatoire(Player player){
        player.constructionAleatoire();
    }
    public static void main(String[] args) {
        Player player = new Player(6);
        Scanner s = new Scanner(System.in);
        String[] entree;
        while(true){
            entree = s.nextLine().split("\\s+");
            if(entree[0].equals("quit")){break;}
            switch (entree[0]) {
                case "bag":
                    addBag(player, Cube.valueOf(entree[1]));
                    break;
                case "side":
                    addSide(player, Cube.valueOf(entree[1]));
                    break;
                case "total":
                    System.out.println(player.totalCube());
                    break;
                case "rbuild":
                    aleatoire(player);
                    break;
                case "build":
                    player.construction(Integer.parseInt(entree[1]),Integer.parseInt(entree[2]), Cube.valueOf(entree[3]));
                    break;
                case "rand":
                    for(int i = 0; i < 5; i++){player.addBag(Cube.valueOf(entree[1]));}
                    break;
                case "print":
                    System.out.println(player);
                    break;
                default:
                    break;
            }
        }
        s.close();


    }
}
