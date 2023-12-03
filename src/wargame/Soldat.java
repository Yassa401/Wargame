package wargame;

abstract class Soldat implements ISoldat {
	 private int pointsDeVie;
	    private int tour;
	    private int portee;
	    private Position position;

	    
	    Soldat(int pointsDeVie, int portee) {
	        this.pointsDeVie = pointsDeVie;
	        this.tour = 0;
	        this.portee = portee;
	        this.position = new Position(PanneauJeu.dimension);
	    }

	    /**
	     * Renvoie les points de vie du soldat
	     * @return pointsDevie : points de vie du soldat
	     */
	    public int getPoints() {
	        return pointsDeVie;
	    }

	    
	    public int getTour() {
	        return tour;
	    }

	    /**
	     * Renvoie la portee du soldat
	     * @return portee : portee du soldat
	     */
	    public int getPortee() {
	        return portee;
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
	    
	    @Override
	    public void joueTour(int tour) {
	        this.tour = tour;
	    }

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
}
