package wargame;

import java.awt.Color;
import java.io.Serializable;

public class Obstacle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum TypeObstacle {
		ROCHER (IConfig.COULEUR_ROCHER), FORET (IConfig.COULEUR_FORET), EAU (IConfig.COULEUR_EAU);
		
		TypeObstacle(Color couleur) { }
		
		public static TypeObstacle getObstacleAlea() {
		return values()[(int)(Math.random()*values().length)];
		}
	}
    private Position position;
	private TypeObstacle TYPE;
	
	public Obstacle() { 
		this.TYPE = TypeObstacle.getObstacleAlea(); 
        this.position = new Position(PanneauJeu.dimension);
	}
        
	public Position getPosition() {
    	return position;
    }
	public TypeObstacle getTypeObstacle() {
		return this.TYPE;
	}
}
