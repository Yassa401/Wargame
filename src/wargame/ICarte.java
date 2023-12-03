package wargame ;

import java.awt.Graphics ;

public interface ICarte {
	
	//Element getElement(Position pos);
	
	// Trouve aléatoirement une position vide sur la carte
	Position trouvePositionVide(); 
	
	// Trouve une position vide choisie aléatoirement parmi les 8 positions adjacentes de pos
	Position trouvePositionVide(Position pos); 
	
	// Trouve aléatoirement un héros sur la carte
	Heros trouveHeros(); 
	
	/**
	 * Verifie si un heros existe à la position en parametre
	 * @param pos : position où chercher
	 * @return renvoie le heros à la position s'il existe, sinon null
	 */
	Heros trouveHeros(Position pos);
	
	
	/**
	 * Deplace le soldat dans la nouvelle position si deplacement possible
	 * @param pos : nouvelle position à laquelle le soldat va se déplacer
	 * @param posSoldat : ancienne position du soldat pour comparer si déplacement possible
	 * @param soldat : soldat à déplacer, on change son attribut position en cas de deplacement
	 * @return Vrai : si déplacement effectué. Faux : si déplacement impossible
	 */
	boolean deplaceSoldat(Position pos, Position posSoldat, Soldat soldat);
	
	void mort(Soldat perso);
	
	boolean actionHeros(Position pos, Position posSoldat, Soldat soldat);
	
	void jouerSoldats(PanneauJeu pj);
	
	void toutDessiner(Graphics g);

}
