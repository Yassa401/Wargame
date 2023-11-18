package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle {

	public enum TypeObstacle {
		ROCHER (IConfig.COULEUR_ROCHER), FORET (IConfig.COULEUR_FORET), EAU (IConfig.COULEUR_EAU);
		
		private final Color COULEUR;
		TypeObstacle(Color couleur) { COULEUR = couleur; }
		
		public static TypeObstacle getObstacleAlea() {
		return values()[(int)(Math.random()*values().length)];
		}
	}
    private Position position;
	private TypeObstacle TYPE;
	
	public Obstacle() { 
		this.TYPE = TYPE.getObstacleAlea(); 
        this.position = new Position(PanneauJeu.dimension);
	}
        
	public Position getPosition() {
    	return position;
    }
	public TypeObstacle getTypeObstacle() {
		return this.TYPE;
	}
}
