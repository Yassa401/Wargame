package wargame ;

import java.awt.Graphics ;

public interface ICarte {
	
	/**
	 * Trouve une position vide choisie aléatoirement parmi les 8 positions adjacentes de pos
	 * @param pos : la position à chercher autour
	 * @return Position : si une case vide adjacente est vide, null sinon
	 */
	Position trouvePositionVide(Position pos); 
	
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
	
	/**
	 * Attaque monstre dans l'une des positions adjacentes si pos contient un monstre
	 * Décide si attaque corps à corps ou attaque à distance selon la position de soldatAdverse
	 */
	boolean combatSoldat(Position posSoldatAdverse, Position posSoldat, Soldat soldat, Soldat soldatAdverse);
	
	/**
	 * Verifie si un soldat est mort
	 * @param perso : le soldat à tester
	 * @return true : si points de vie inférieure à 0, false sinon 
	 */
	boolean mort(Soldat perso);
	
	boolean actionHeros(Position pos, Position posSoldat, Soldat soldat);
	
	void toutDessiner(Graphics g);

}
