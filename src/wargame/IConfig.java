package wargame ;

import java.awt.Color ;

public interface IConfig {
	
	// en nombre de cases
	static final int LARGEUR_CARTE = 23, HAUTEUR_CARTE = 20; 
	
	// Taille d'un côté de l'hexagone
	static final int NB_PIX_CASE = 20;
	
	// Position de la fenêtre
	static final int POSITION_X = 100, POSITION_Y = 20;
	
	// Dimensions de la fenetre
	static final int LARGEUR_FENETRE = 1000, LONGUEUR_FENETRE = 700 ;
	
	int NB_HEROS = 6; int NB_MONSTRES = 15; int NB_OBSTACLES = 20;
	
	Color COULEUR_VIDE = Color.white, COULEUR_INCONNU = Color.lightGray;
	
	Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black;
	
	Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink;
	
	Color COULEUR_EAU = Color.blue, COULEUR_FORET = Color.green, COULEUR_ROCHER = Color.gray;

}
