package wargame ;

import java.awt.Graphics ;

public interface ICarte {
	
	Element getElement(Position pos);
	
	// Trouve aléatoirement une position vide sur la carte
	Position trouvePositionVide(); 
	
	// Trouve une position vide choisie aléatoirement parmi les 8 positions adjacentes de pos
	Position trouvePositionVide(Position pos); 
	
	// Trouve aléatoirement un héros sur la carte
	Heros trouveHeros(); 
	
	//Trouve un héros choisi aléatoirement parmi les 8 positions adjacentes de pos
	Heros trouveHeros(Position pos); // 
	
	boolean deplaceSoldat(Position pos, Soldat soldat);
	
	void mort(Soldat perso);
	
	boolean actionHeros(Position pos, Position pos2);
	
	void jouerSoldats(PanneauJeu pj);
	
	void toutDessiner(Graphics g);

}
