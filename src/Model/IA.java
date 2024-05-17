// package Model;

// import java.util.*;
// import java.awt.Point;

// public abstract class IA {
//     Jeu jeu;

//     public static IA nouvelle(Jeu j) {
//         IA resultat = null;
//         String type = "Aleatoire";

//         switch (type) {
//             case "Aleatoire":
//                 resultat = new IAAleatoire();
//                 break;
//             case "Facile":
//                 resultat = new IAFacile();
//                 break;
//             case "Medium":
//                 resultat = new IAMedium();
//                 break;
//             case "Difficile":
//                 resultat = new IADifficile();
//                 break;
//         }
//         if (resultat != null) {
//             resultat.jeu = j;
//         }
//         return resultat;
//     }

//     public int MinMaxIA(Jeu j,int depth, int player_max, int alpha, int beta, int IA){
//         int value;
//         boolean bon_joueur = player_max == j.getCurrent();
//         ArrayList<Point> cubes_access = j.Accessible_Playable(j.getCurrent());
//         if(j.End_Game()){ //Condition de défaite de tous les autres joueurs en même temps à implémenter
//                                              //Besoin d'une fonctione auxiliaire qui permet lors de tests de si un joueur a perdu de l'exclure du calcul
//                                              //Et appeler l'IA avec les nouveaux paramètres.
//             if(j.check_loss()){
//                 return -1000;
//             }else{
//                 return 1000;
//             }
//         }
//         if (depth == 0){
//             if(IA == 0){ //IA Facile
//                 if(bon_joueur){
//                     return j.getPlayer().TotCubesHand() - j.getPlayer(j.next_player()).TotCubesHand();
//                 }
//                 else{
//                     return j.getPlayer(j.next_player()).TotCubesHand() - j.getPlayer().TotCubesHand();
//                 }
//             }
//             if(IA == -1){ //IA specifique à la création de la pyramide
//                 int total = 0;
//                 for(Point compte : cubes_access){
//                     int current_possibilities = j.CubeAccessibleDestinations(j.getPlayer().get((int) compte.getX(),(int) compte.getY())).size();
//                     total+= current_possibilities;
//                 }
//                 return total;
//             }
//             if(IA==1){ //IA Medium
//                 int total_j1 = 0;
//                 for(Point compte : cubes_access){
//                     int current_possibilities = j.CubeAccessibleDestinations(j.getPlayer().get((int) compte.getX(),(int) compte.getY())).size();
//                     total_j1+= current_possibilities;
//                 }
//                 int total_j2 = 0;
//                 ArrayList<Point> cubes_access2 = j.Accessible_Playable(j.getCurrent(ArrayList<Point> cubes_access = j.Accessible_Playable(j.next_player());));
//                 for(Point compte : cubes_access2) {
//                     int current_possibilities = j.CubeAccessibleDestinations(j.getPlayer().get((int) compte.getX(),(int) compte.getY())).size();
//                     total_j2+= current_possibilities;
//                 }
//                 int total = total_j1 * 0.2 + j.getPlayer().TotCubesHand()*0.8 - 0.8 * j.getPlayer(j.next_player()).TotCubesHand() - total_j2 * 0.2;
//                 if(bon_joueur){
//                     return total; 
//                 }
//                 else{
//                     return -total;
//                 }
//             }
//         }
//         if(bon_joueur){
//             value = -1000;
//             for(Point depart : cubes_access){
//                 ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations(j.getPlayer().get((int) depart.getX(),(int) depart.getY()));
//                 for(Point arrivee : coups_jouables){
//                     j.add_central_pyramid((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY());
//                     value = Math.max(value,MinMaxIA(j,depth-1,player_max, alpha, beta, IA));
//                     if(alpha >= value){
//                         return value;
//                     }
//                     beta = Math.min(beta,value);
//                 }
//             }
//         }
//         else{
//             value = 1000;
//             for(Point depart : cubes_access){
//                 ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations(j.getPlayer().get((int) depart.getX(),(int) depart.getY()));
//                 for(Point arrivee : coups_jouables){
//                     j.add_central_pyramid((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY());
//                     value = Math.min(value,MinMaxIA(j,depth-1,player_max, alpha, beta, IA));
//                     if(beta <= value){
//                         return value;
//                     }
//                     alpha = Math.max(alpha,value);
//                 }
//             }
//         }
//         return value;
//     }

//     void add_central() {
//         throw new UnsupportedOperationException();
//     }
//     void ajoute(){
//         throw new UnsupportedOperationException();
//     }
// }
