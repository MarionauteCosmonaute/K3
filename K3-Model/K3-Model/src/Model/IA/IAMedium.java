// package Model;
// import java.util.*;
// import java.io.*;
// import java.awt.Point;

// public class IAMedium extends IA {

    

//     public ArrayList<ArrayList<Point>> coupIA(Jeu j, int joueur1){
//         ArrayList<ArrayList<Point>> resultat_ok = new ArrayList<>();
//         ArrayList<ArrayList<Point>> resultat_ko = new ArrayList<>();
//         int value_max= -100000;
//         ArrayList<Point> cubes_access = j.Accessible_Playable();
//         for(Point depart : cubes_access){
//             ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations(j.getPlayer().get((int) depart.getX(),(int) depart.getY()));
//             for(Point arrivee : coups_jouables){
//                 ArrayList<Point> pos = new ArrayList<>();
//                 pos.add(depart);
//                 pos.add(arrivee);
//                 Jeu clone = new Jeu(2);
//                 clone = j.clone();
//                 clone.add_central_pyramid((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY());
//                 int value= MinMaxIA(clone, 3, joueur1, -10000, +10000, 1);
//                 if(value==value_max){
//                     resultat_ok.add(pos);
//                 }
//                 else if(value>value_max){
//                     resultat_ok.clear();
//                     resultat_ok.add(i);
//                     value_max = value;
//                 }
//                 else if(value==1000){
//                     resultat_ok.clear();
//                     resultat_ok.add(pos);
//                     return resultat_ok;
//                 }
//                 else{
//                     resultat_ko.add(pos);
//                 }
//             }
//         }
//         if(resultat_ok.size()==0){
//             return resultat_ko;
//         }
//         return resultat_ok;
//     }

//     public ArrayList<int> pyramideIA(Jeu j, int joueur1){
//         ArrayList<int> resultat_ok = new ArrayList<>();
//         ArrayList<int> resultat_ko = new ArrayList<>();
//         int value_max= -100000;
//         for(int i=0; i<j.getPlayer(joueur1).getBagSize();i++){
//             ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations(j.getPlayer(player1).personalBag(i));
//             for(Point coup : coups_jouables){
//                 Jeu clone = new Jeu(2);
//                 clone = j.clone();
//                 clone.add_central_pyramid(coup.getX(), coup.getY(), (int) j.getPlayer(joueur1).getBag().get(i).getX(), (int) j.getPlayer(joueur1).getBag().get(i).getY());
//                 int value= MinMaxIA(clone, 3, joueur1, -10000, +10000, -1);
//                 if(value==value_max){
//                     resultat_ok.add(i);
//                 }
//                 else if(value>value_max){
//                     resultat_ok.clear();
//                     resultat_ok.add(i);
//                     value_max = value;
//                 }
//                 else if(value==1000){
//                     resultat_ok.clear();
//                     resultat_ok.add(i);
//                     return resultat_ok;
//                 }
//                 else{
//                     resultat_ko.add(i);
//                 }
//             }
//         }
//         if(resultat_ok.size()==0){
//             return resultat_ko;
//         }
//         return resultat_ok;
//     }

//     @Override
//     public void add_central(){
//         ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player);
//         Random random = new Random();
//         ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
//         jeu.add_central((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
//     }

//     @Override
//     public void ajoute(){
//         ArrayList<int> coups_possibles = pyramideIA(jeu, jeu.current_player);
//         Random random = new Random();
//         Point x_y_pyra = jeu.findFirstFreeElement();
//         int cube_a_placer = coups_possibles.get(random.nextInt(coups_possibles.size()));
//         jeu.ajoute((int) x_y_pyra.getX(), (int) x_y_pyra.getY(), cube_a_placer);
//     }
// }
