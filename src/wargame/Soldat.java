package wargame;

import java.io.Serializable;

abstract class Soldat implements ISoldat,Serializable {
	 private int pointsDeVie;
	    private int portee;
	    private int tir;
	    private int puissance;
	    private Position position;
	    protected TypesM monstre;
	    protected TypesH heros;
	    
	    /**
	     * Recupere tous les donnees du enum Monstre pour les stocker dans les variables associées
	     * @param typeMonstre : enum Monstre choisi
	     */
	    Soldat(TypesM typeMonstre){
	    	this.monstre= typeMonstre;
	    	this.pointsDeVie = typeMonstre.getPoints();
	    	this.tir=typeMonstre.getTir();
	    	this.puissance=typeMonstre.getPuissance();
	    	this.portee = typeMonstre.getPortee();
	    	this.position = new Position(PanneauJeu.dimension);
	    }
	    
	    /**
	     * Recupere tous les donnees du enum Heros pour les stocker dans les variables associées
	     * @param typeHeros : enum Heros choisi
	     */
	    Soldat(TypesH typeHeros){
	    	this.heros = typeHeros;
	    	this.pointsDeVie = typeHeros.getPoints();
	    	this.tir=typeHeros.getTir();
	    	this.puissance=typeHeros.getPuissance();
	    	this.portee = typeHeros.getPortee();
	    	this.position = new Position(PanneauJeu.dimension);
	    }
	    
	    /**
	     * Renvoie les points de vie du soldat
	     * @return pointsDevie : points de vie du soldat
	     */
	    public int getPoints() {
	        return pointsDeVie;
	    }

	    /**
	     * Renvoie la portee du soldat
	     * @return portee : portee du soldat
	     */
	    public int getTir() {
	        return tir;
	    }
	    public int getPortee() {
	        return portee;
	    }
	    public int getPuissance() {
	        return puissance;
	    }
	    
	    
	    /**
	     * Renvoie la Position de l'objet Soldat
	     * @return Position: (x,y) ,numero de l'hexagone, ligne et colonne
	     */
	    public Position getPosition() {
	    	return position;
	    }
	    
	    
	    /**
	     * Change les points de vie du soldat
	     * @param pointDevie : nouvelle valeur des points de vie
	     */
	    public void setPoints(int pointDevie) {
	    	this.pointsDeVie = pointDevie;
	    }
	    
	    /**
	     * Methode qui renvoie le type enum du Monste
	     * @return type enum du Monste : TROLL, ORC, GOBELIN
	     */
	    protected abstract TypesM getTypeMonstre();
	    
	    /**
	     * Methode qui renvoie le type enum du Heros
	     * @return type enum du Monste : HUMAIN, NAIN, ELF, HOBBIT 
	     */
	    protected abstract TypesH getTypeHeros();
	    

	    /**
	     * Deplace le soldat choisie à la position de la souris en Drag&Drop
	     * @param nouvellePos : la nouvelle position correspondant à celle de la souris
	     */
	    @Override
	    public void seDeplace(Position nouvellePos) {
	        this.getPosition().setPosition(nouvellePos);
	    }

	    /**
	     * Attaque le soldat adverse s'il se trouve à la position de la souris
	     * @param soldat : soldat à attaquer (dans ce case Monstre)
	     */
	    @Override
	    public void combat(Soldat soldat) {
	    	// On definit cette methode dans Heros et Monstre pour recuperer les puissance
	    }
	    
	    public void combatDistance(Soldat soldat) {
	    	// On definit cette methode dans Heros et Monstre pour recuperer les puissance
	    }
	    
	    /**
		 * Ajoute des points de vie lorsqu'on clique sur un heros sans le deplacer
		 */
		public void seReposer() {
			int point = (int)Math.random() * 2 + 1 ;
			this.setPoints(this.getPoints()+ point);
		}
}
