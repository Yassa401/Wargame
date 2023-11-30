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

	    public int getPoints() {
	        return pointsDeVie;
	    }

	    public int getTour() {
	        return tour;
	    }

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
	     * Methode qui renvoie le type enum du Monste
	     * @return type enum du Monste : TROLL, ORC, GOBELIN
	     */
	    protected abstract TypesM getTypeMonstre();
	    
	    /**
	     * Methode qui renvoie le type enum du Heros
	     * @return type enum du Monste : HUMAIN, NAIN, ELF, HOBBIT 
	     */
	    protected abstract TypesH getTypeHeros();
	    
	    
	    public void joueTour(int tour) {
	        this.tour = tour;
	    }

	    public void seDeplace(Position nouvelle_Pos) {
	        this.getPosition().setPosition(nouvelle_Pos);
	    }

	    public void combat(Soldat soldat) {
	        // code qui gere le combat
	    }
}
