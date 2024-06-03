package Modele;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class LecteurNiveau {
        public FileInputStream fis;
        Scanner read;

    LecteurNiveau(String src){
        try{
            fis=new FileInputStream(src);
        }catch (FileNotFoundException e){
            System.err.println("ERREUR : Fichier introuvable");
            System.exit(1);
        }
        read=new Scanner(fis);
    }
    
    Niveau lisProchainNiveau(){
        Niveau N=new Niveau();
        String line;
        int i=0;
        boolean finished=false;
        while(!finished){
            if(!read.hasNext()){
                return null;// plus de niveaux dispos dans le fichier;
            }
            line=read.nextLine();
            if (line.length()!=0){

            
                if (line.charAt(0)==';'){// lecture du nom et fin de la lecture du niveau
                    N.fixeNom(line.substring(2));
                    finished=true;
                }else{
                    for (int j=0;j<line.length();j++){
                        switch (line.charAt(j)) {
                            case '#':
                                N.ajouteMur(i, j);
                                break;
                            case ' ':
                                N.videCase(i, j);
                                break;
                            case '$':
                                N.ajouteCaisse(i, j);
                                break;
                            case '.':
                                N.ajouteBut(i, j);
                                break;
                            case '@':
                                N.ajoutePousseur(i, j);
                                break;
                            default:
                                break;
                        }
                    }
                    i++;
                }
            }
        }

        return N;
    }

}
