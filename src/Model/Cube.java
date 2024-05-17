package Model;

public enum Cube {
    Noir, Bleu, Vert, Vide, Blanc, Rouge, Jaune, Neutre;

    static String conversionString(Cube cube){
        switch(cube){
            case Vide: return "0 ";
            case Noir: return  "1 ";
            case Bleu: return "2 ";
            case Vert: return "3 ";
            case Rouge: return "4 ";
            case Jaune: return "5 ";
            case Neutre: return "6 ";
            case Blanc: return "7 ";
            default:
                System.exit(2);
        }
        return "";
    }
    static Cube conversion(String s){
        switch(s){
            case "0": return Cube.Vide;  
            case "1": return Cube.Noir;
            case "2": return Cube.Bleu;
            case "3": return Cube.Vert;
            case "4": return Cube.Rouge;
            case "5": return Cube.Jaune;
            case "6": return Cube.Neutre;
            case "7": return Cube.Blanc;
            default: System.exit(2);
        }
        return Cube.Vide;
    }
    static Cube intToCube(int s){
        switch(s){
            case 0: return Cube.Vide;  
            case 1: return Cube.Noir;
            case 2: return Cube.Bleu;
            case 3: return Cube.Vert;
            case 4: return Cube.Rouge;
            case 5: return Cube.Jaune;
            case 6: return Cube.Neutre;
            case 7: return Cube.Blanc;
            default: System.exit(2);
        }
        return Cube.Vide;
    }
}