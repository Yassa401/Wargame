package wargame ;

import java.awt.Color ;

public interface IConfig {
	
	// en nombre de cases
	static final int LARGEUR_CARTE = 15; // columns
	static final int HAUTEUR_CARTE = 14; // rows
	
	// Taille d'un côté de l'hexagone
	static final int NB_PIX_CASE = 30;
	
	// Position de la fenêtre
	static final int POSITION_X = 400, POSITION_Y = 200;
	
	// Dimensions de la fenetre
	static final int LARGEUR_FENETRE = 1200, LONGUEUR_FENETRE = 700 ;
	
	int NB_HEROS = 6; int NB_MONSTRES = 15; int NB_OBSTACLES = 15;
	
	Color COULEUR_VIDE = Color.white, COULEUR_INCONNU = Color.lightGray;
	
	Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black;
	
	Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink;
	
	Color COULEUR_EAU = Color.blue, COULEUR_FORET = Color.green, COULEUR_ROCHER = Color.gray;

	// taille de l'image d'un caratere
	static final int SIZE_CHARACTER = 35;
}
