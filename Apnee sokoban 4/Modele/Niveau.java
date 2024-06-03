package Modele;
import java.awt.Point;

public class Niveau {
    String nom=new String();
    int[][] grille;
    int max_l,max_c;
    public static final int VIDE=0;
    public static final int POUSSEUR=1;
    public static final int CAISSE=2;
    public static final int MUR=3;
    public static final int BUT=4;
    public static final int POU_BUT=5;
    public static final int CA_BUT=6;

    Niveau(){
        this.grille=new int[1][1];
        this.max_l=1;
        this.max_c=1;
    }
    public int max_l(){
        return this.max_l;
    }
    public int max_c(){
        return this.max_c;
    }
    public int[][] grille(){
        return grille;
    }

    void resize(int L, int C){
        if (L>=this.grille.length || C>=this.grille[0].length){
            int[][] newGrille=new int[this.grille.length*2][this.grille[0].length*2];//Je remplis la grille de gauche a droite et de haut en bas, 
                                                                                     //a priori je ne devrai pas a avoir a multiplier par 2 plus d'une fois l et c
            for (int i=0;i<this.grille.length;i++){
                for (int j=0;j<this.grille[0].length;j++){
                    newGrille[i][j]=this.grille[i][j];
                }
            }
            this.grille=newGrille;
        }
        if (L >= this.max_l){
            this.max_l = L+1;
        }
        if (C >= this.max_c){
            this.max_c = C+1;
        }
    }

    void fixeNom(String s){nom=s;}

    void videCase(int i, int j){
        resize(i,j);
        grille[i][j]=VIDE;
    }
    void ajouteMur(int i,int j){
        resize(i,j);
        grille[i][j]=MUR;
    }
    void ajoutePousseur(int i, int j){
        resize(i,j);
        if (grille[i][j]==BUT){
            grille[i][j]=POU_BUT;
        }else{grille[i][j]=POUSSEUR;}
        
    }
    void ajouteCaisse(int i, int j){
        resize(i,j);
        if (grille[i][j]==BUT){
            grille[i][j]=CA_BUT;
        }else{grille[i][j]=CAISSE;}
        
    }
    void ajouteBut(int i, int j){
        resize(i,j);
        grille[i][j]=BUT;
    }
    void ajouteCaisseSurBut(int i, int j){
        resize(i, j);
        grille[i][j]=CA_BUT;
    }
    void ajoutePousseurSurBut(int i, int j){
        resize(i, j);
        grille[i][j]=POU_BUT;
    }

    int lignes(){return max_l;}
    int colonnes(){return max_c;}
    public boolean estVide(int l, int c){ return this.grille[l][c]==VIDE;}
    public boolean aMur(int l, int c){ return this.grille[l][c]==MUR;}
    public boolean aBut(int l, int c){ return this.grille[l][c]==BUT || this.grille[l][c]==CA_BUT || this.grille[l][c]==POU_BUT;}//Inclut uniquement BUT,CA_BUT et POU_BUT
    public boolean aCaisse(int l, int c){ return this.grille[l][c]==CAISSE || this.grille[l][c]==CA_BUT;}//Inclut uniquement CAISSE et CA_BUT
    public boolean aPousseur(int l, int c){ return this.grille[l][c]==POUSSEUR || this.grille[l][c]==POU_BUT;}//Inclut uniquement POUSSEUR et POU_BUT
    
    public boolean estLibre(int l, int c){ return (estVide(l, c) || (aBut(l, c) && !aCaisse(l, c)));}
    public Point getPousseur(){
        for(int i=0; i<max_l;i++){
            for(int j=0; j<max_c;j++){
                if (aPousseur(i, j)){
                    return new Point(i,j);
                }
            }
        }
        return null;
    }

    public String toString(){
        String out="";
        for(int i=0; i<max_l;i++){
            for(int j=0; j<max_c;j++){
                switch(grille[i][j]){
                    case VIDE:
                        out=out.concat(" ");
                        break;
                    case MUR:
                        out=out.concat("#");
                        break;
                    case BUT:
                        out=out.concat(".");
                        break;
                    case CAISSE:
                        out=out.concat("$");
                        break;
                    case POUSSEUR:
                        out=out.concat("@");
                        break;
                    case POU_BUT:
                        out=out.concat("&");
                        break;
                    case CA_BUT:
                        out=out.concat("w");
                        break;
                    default:
                        out.concat("unexpected or not implemented://");
                }
            }
            out=out.concat("\n");
        }
        return out;
    }

    Niveau copy(){
        Niveau N= new Niveau();
        N.fixeNom(nom);
        for(int i=0;i<max_l;i++){
            for(int j=0; j<max_c;j++){
                switch(grille[i][j]){
                    case VIDE:
                        N.videCase(i, j);
                        break;
                    case MUR:
                        N.ajouteMur(i, j);
                        break;
                    case BUT:
                        N.ajouteBut(i, j);
                        break;
                    case CAISSE:
                        N.ajouteCaisse(i, j);
                        break;
                    case POUSSEUR:
                        N.ajoutePousseur(i, j);
                        break;
                    case POU_BUT:
                    case CA_BUT:
                    default:
                        System.out.println("unexpected or not implemented://");
                        break;
                }
            }
        }
        return N;
    }
}
