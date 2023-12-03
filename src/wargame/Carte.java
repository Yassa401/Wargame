package wargame;

import java.awt.Graphics;
import java.util.HashMap;

public class Carte implements ICarte {
	HashMap<Integer, Soldat> listeSoldats;
    HashMap<Integer, Obstacle> listeObstacle;
    int tabCases[];
    
	Carte(int tabCases[], HashMap<Integer, Soldat> listeSoldats,    HashMap<Integer, Obstacle> listeObstacle) {
		this.tabCases = tabCases;
        this.listeSoldats = listeSoldats;
        this.listeObstacle = listeObstacle;
		
	}
	
	/**
	 * Renvoie le tableau de cases de la carte
	 * @return tabCases : tableau de toutes les cases de la carte
	 */
	public int[] getTabCases() {
		return tabCases;
	}
	
	/**
	 * Renvoie HashMap contenant les informations sur les soldats
	 * @return listeSoldats : HashMap contenant les Heros et Monstre
	 */
	public HashMap<Integer, Soldat> getListeSoldats(){
		return listeSoldats;
	}
	
	/**
	 * Renvoie HashMap contenant les informations sur les obstacles
	 * @return listeSoldats : HashMap contenant les obstacles
	 */
	public HashMap<Integer,Obstacle> getListeObstacle(){
		return listeObstacle;
	}
	
	public boolean estPositionVide(Position pos) {
		return tabCases[pos.getNumeroCase()] == -1 ;
	}
	
	@Override
	public Position trouvePositionVide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position trouvePositionVide(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros(Position pos) {
		if(!estPositionVide(pos) && !listeObstacle.containsKey(tabCases[pos.getNumeroCase()]) && (listeSoldats.get(tabCases[pos.getNumeroCase()]) instanceof Heros )) {
			return (Heros)listeSoldats.get(tabCases[pos.getNumeroCase()]) ;
		}
		return null;
	}
	
	@Override
	public boolean deplaceSoldat(Position pos, Position posSoldat, Soldat soldat) {
		// ajouter des conditions pour valider la position avant de deplacer le soldat
		int numCaseSoldat = posSoldat.getNumeroCase();
		int numCasePos = pos.getNumeroCase();
		if(numCasePos == numCaseSoldat) { /* on fait rien le soldat est resté dans sa position */}
		else if(numCasePos == (numCaseSoldat+1)) {soldat.seDeplace(pos);} // à droite
		else if(numCasePos == (numCaseSoldat-1)) {soldat.seDeplace(pos);} // à gauche
		else if(numCasePos == (numCaseSoldat+IConfig.LARGEUR_CARTE)) {soldat.seDeplace(pos);} // en haut
		else if(numCasePos == (numCaseSoldat-1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) {soldat.seDeplace(pos);} // en haut
		else if(numCasePos == (numCaseSoldat+1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) {soldat.seDeplace(pos);} // en haut
		else if(numCasePos == (numCaseSoldat-IConfig.LARGEUR_CARTE)) {soldat.seDeplace(pos);} // en bas
		else if(numCasePos == (numCaseSoldat-1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) {soldat.seDeplace(pos);} // en bas 
		else if(numCasePos == (numCaseSoldat+1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) {soldat.seDeplace(pos);} // en bas
		else { return false; } // deplacement impossible
		return true;
	}

	@Override
	public void mort(Soldat perso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean actionHeros(Position pos, Position posSoldat, Soldat soldat) {
		if(estPositionVide(pos)) {
			return deplaceSoldat(pos, posSoldat, soldat);
		}
		return false;
	}

	@Override
	public void jouerSoldats(PanneauJeu pj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toutDessiner(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
