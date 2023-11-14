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
	    
	    public Position getPosition() {
	    	return position;
	    }

	    public void joueTour(int tour) {
	        this.tour = tour;
	    }

	    public void seDeplace(Position nouvelle_Pos) {
	        this.position =  nouvelle_Pos;
	    }

	    public void combat(Soldat soldat) {
	        // code qui gere le combat
	    }
}
