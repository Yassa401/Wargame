package wargame;

import java.io.Serializable;
import java.util.HashMap;

public class sauvegarde_wargame implements Serializable {
    private static final long serialVersionUID = 1L; 

	    private HashMap<Integer, Soldat> listeSoldats;
	    private HashMap<Integer, Obstacle> listeObstacle;
	    private int[] tabCases;

	    public sauvegarde_wargame(HashMap<Integer, Soldat> listeSoldats, HashMap<Integer, Obstacle> listeObstacle, int[] tabCases) {
	        this.listeSoldats = listeSoldats;
	        this.listeObstacle = listeObstacle;
	        this.tabCases = tabCases;
	    }

		public HashMap<Integer, Soldat> getListeSoldats() {
			// TODO Auto-generated method stub
			return this.listeSoldats;
		}

		public HashMap<Integer, Obstacle> getListeObstacle() {
			// TODO Auto-generated method stub
			return this.listeObstacle;
		}

		public int[] getTabCases() {
			// TODO Auto-generated method stub
			return this.tabCases;
		}
}