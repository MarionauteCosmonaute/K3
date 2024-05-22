package Model;

public enum Cube {
    Noir(1),
    Bleu(2),
    Vert(3),
    Vide(7),
    Blanc(0),
    Rouge(4),
    Jaune(5),
    Neutre(6);

    private int value;
    
    private Cube(int value){
        this.value = value;
    }
    
    public int getInt(){
        return value;
    }

    static String conversionString(Cube cube){
        return cube.value + " ";
    }

    public static Cube conversion(String s){
        switch(s){
            case "7": return Cube.Vide;  
            case "1": return Cube.Noir;
            case "2": return Cube.Bleu;
            case "3": return Cube.Vert;
            case "4": return Cube.Rouge;
            case "5": return Cube.Jaune;
            case "6": return Cube.Neutre;
            case "0": return Cube.Blanc;
            default: System.err.println("Conversion Cube impossible pour le string: " + s);System.exit(2);
        }
        return Cube.Vide;
    }
    public static Cube intToCube(int s){
        switch(s){
            case 7: return Cube.Vide;  
            case 1: return Cube.Noir;
            case 2: return Cube.Bleu;
            case 3: return Cube.Vert;
            case 4: return Cube.Rouge;
            case 5: return Cube.Jaune;
            case 6: return Cube.Neutre;
            case 0: return Cube.Blanc;
            default: System.err.println("Conversion Cube impossible pour le int: " + s);System. exit(2);
        }
        return Cube.Vide;
    }
}