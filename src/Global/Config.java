package Global;

public class Config {
    static String lan = "FR";
    public static String IA = "Facile";
    public static boolean isPlaying=false;
    public static String getLanguage(){
        return lan;
    }

    public static void setLanguage(String S){
        lan=S;
    }

    public static void setIA(String S){
        IA=S;
    }
    
    public static void setIsPlaying(boolean b){
        isPlaying=b;
    }
}
