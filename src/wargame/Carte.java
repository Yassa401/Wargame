package wargame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

public class Carte implements ICarte {
	HashMap<Integer, Soldat> listeSoldats;
    HashMap<Integer, Obstacle> listeObstacle;
    int tabCases[];
    private Random random;
    
	Carte(int tabCases[], HashMap<Integer, Soldat> listeSoldats,    HashMap<Integer, Obstacle> listeObstacle) {
		this.tabCases = tabCases;
        this.listeSoldats = listeSoldats;
        this.listeObstacle = listeObstacle;
        random = new Random(); // initialise un objet random qu'on va utiliser dans plusieurs methodes(trouverPositionVide, trouverMonstre ...)
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
		if(PanneauJeu.number == -1) {return false;}
		return tabCases[pos.getNumeroCase()] == -1;
	}
	
	@Override
	public Position trouvePositionVide() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Trouve une case vide adjacente à la position en paramètre
	 */
	@Override
	public Position trouvePositionVide(Position pos) {
		int acc = 0 ;
		Position posVide;
		int numCase, row = 0, column = 0; // ils seront initiés pour la nouvelle position qui sera vide et renvoyé
		ArrayList<Integer> posAdjacentes = new ArrayList<Integer>(); 
		posAdjacentes.add(pos.getNumeroCase()+1); posAdjacentes.add(pos.getNumeroCase()-1);
		posAdjacentes.add(pos.getNumeroCase()+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()-IConfig.LARGEUR_CARTE);
		if(pos.getRow()%2==0) {
			posAdjacentes.add(pos.getNumeroCase()-1+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()-1-IConfig.LARGEUR_CARTE);
		}else {
			posAdjacentes.add(pos.getNumeroCase()+1+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()+1-IConfig.LARGEUR_CARTE);
		}
		numCase = posAdjacentes.get(random.nextInt(posAdjacentes.size()));
		while( numCase < 0 || numCase >= IConfig.LARGEUR_CARTE*IConfig.HAUTEUR_CARTE || tabCases[numCase] != -1) { // pour ne pas sortir des cases possible de la carte
			if(acc > posAdjacentes.size()) {break ;}
			numCase = posAdjacentes.get(random.nextInt(posAdjacentes.size()));
			acc += 1 ; // si acc depasse la taille des posAdjacentes alors il n'y a pas de pos adjacente vide 
			System.out.println("boucle " + numCase );
		}
		// quand on trouve une case vide, remplit les champs row et column correspondant pour la variable posVide
		// si arrive à la fin de ligne, passe à ligne suivante en premiere colonne
		if(numCase == (pos.getNumeroCase()+1)) { 
			row = pos.getRow();
			column = pos.getColumn()+1;
		}
		// si debut de ligne, revient à ligne precedente en derniere colonne
		else if(numCase == (pos.getNumeroCase()-1)) {
			row = pos.getRow();
			column = pos.getColumn()-1 ; 
		}
		
		else if(numCase == (pos.getNumeroCase()+IConfig.LARGEUR_CARTE)) { row = pos.getRow()+1; column = pos.getColumn();}
		else if(numCase == (pos.getNumeroCase()-IConfig.LARGEUR_CARTE)) { row = pos.getRow()-1; column = pos.getColumn();}
		// si ligne impaire
		else if(numCase == (pos.getNumeroCase()+1+IConfig.LARGEUR_CARTE) && pos.getRow()%2==1) { row = pos.getRow()+1; column = pos.getColumn()+1;}
		else if(numCase == (pos.getNumeroCase()+1-IConfig.LARGEUR_CARTE) && pos.getRow()%2==1) { row = pos.getRow()-1; column = pos.getColumn()+1;}
		// si ligne paire
		else if(numCase == (pos.getNumeroCase()-1+IConfig.LARGEUR_CARTE) && pos.getRow()%2==0) { row = pos.getRow()+1; column = pos.getColumn()-1;}
		else if(numCase == (pos.getNumeroCase()-1-IConfig.LARGEUR_CARTE) && pos.getRow()%2==0) { row = pos.getRow()-1; column = pos.getColumn()-1;}
		else {
			return null; // toutes les positions adjacentes sont remplies (rare mais ça peut arriver)
		}
		if(row < 0 || row >= IConfig.HAUTEUR_CARTE || column < 0 || column >= IConfig.LARGEUR_CARTE) {
			return trouvePositionVide(pos);
		}
		posVide = new Position(PanneauJeu.dimension);
		posVide.setNumeroCase(numCase); posVide.setColumn(column); posVide.setRow(row);
		System.out.println("posVide : numCase " + posVide.getNumeroCase() + " row " + posVide.getRow() + " column " + posVide.getColumn());
		return posVide;
	}
	
	/**
	 * Renvoie un monstre aléatoirement choisi parmi les monstres disponibles
	 * @return Monstre : monstre choisi
	 */
	public Monstre trouveMonstre() {
		Integer cle = (Integer)listeSoldats.keySet().toArray()[random.nextInt(listeSoldats.size())];
		Soldat monstre = listeSoldats.get(cle);
		while( !(monstre instanceof Monstre)) {
			cle = (Integer)listeSoldats.keySet().toArray()[random.nextInt(listeSoldats.size())];
			monstre = listeSoldats.get(cle);
		}
		System.out.println("Monstre choisie à la case " + monstre.getPosition().getNumeroCase() + " de type " + monstre.getTypeMonstre() );
		return (Monstre)monstre;
	}
	
	/**
	 * Trouve un heros à attaquer dans les positions adjacentes à la position en parametre
	 * @param pos : position du monstre qui effectue l'attaque
	 * @return renvoie un Heros s'il trouve, sinon null
	 */
	public Heros trouveHerosAdverse(Position pos) {
		
		// Arraylist contenant toutes les positions adjacentes à pos
		ArrayList<Integer> posAdjacentes = new ArrayList<Integer>(); 
		posAdjacentes.add(pos.getNumeroCase()+1); posAdjacentes.add(pos.getNumeroCase()-1);
		posAdjacentes.add(pos.getNumeroCase()+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()-IConfig.LARGEUR_CARTE);
		if(pos.getRow()%2==0) {
			posAdjacentes.add(pos.getNumeroCase()-1+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()-1-IConfig.LARGEUR_CARTE);
		}else {
			posAdjacentes.add(pos.getNumeroCase()+1+IConfig.LARGEUR_CARTE); posAdjacentes.add(pos.getNumeroCase()+1-IConfig.LARGEUR_CARTE);
		}
		for(int i = 0 ; i < posAdjacentes.size(); i++) {
			if(posAdjacentes.get(i) >= 0 && posAdjacentes.get(i) < IConfig.LARGEUR_CARTE*IConfig.HAUTEUR_CARTE) {
				if(tabCases[posAdjacentes.get(i)] != -1 && listeSoldats.get(tabCases[posAdjacentes.get(i)]) instanceof Heros) {
					return (Heros) listeSoldats.get(tabCases[posAdjacentes.get(i)]) ;
				}
			}
		}
		
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
	
	/**
	 * Deplace le soldat dans l'une des positions adjacentes si pos est vide
	 */
	@Override
	public boolean deplaceSoldat(Position pos, Position posSoldat, Soldat soldat) {
		// ajouter des conditions pour valider la position avant de deplacer le soldat
		int numCaseSoldat = posSoldat.getNumeroCase();
		int numCasePos = pos.getNumeroCase();
		if(numCasePos == numCaseSoldat) { return false;/* on fait rien le soldat est resté dans sa position */}
		else if(numCasePos == (numCaseSoldat+1)) {soldat.seDeplace(pos);} // à droite
		else if(numCasePos == (numCaseSoldat-1)) {soldat.seDeplace(pos);} // à gauche
		else if(numCasePos == (numCaseSoldat+IConfig.LARGEUR_CARTE)) {soldat.seDeplace(pos);} // en bas
		else if(numCasePos == (numCaseSoldat-1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) {soldat.seDeplace(pos);} // en bas
		else if(numCasePos == (numCaseSoldat+1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) {soldat.seDeplace(pos);} // en bas
		else if(numCasePos == (numCaseSoldat-IConfig.LARGEUR_CARTE)) {soldat.seDeplace(pos);} // en haut
		else if(numCasePos == (numCaseSoldat-1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) {soldat.seDeplace(pos);} // en haut 
		else if(numCasePos == (numCaseSoldat+1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) {soldat.seDeplace(pos);} // en haut
		else { return false; } // deplacement impossible
		return true;
	}
	
	/**
	 * Attaque monstre dans l'une des positions adjacentes si pos contient un monstre
	 */
	@Override
	public boolean combatSoldat(Position posSoldatAdverse, Position posSoldat, Soldat soldat, Soldat soldatAdverse) {
		int numCaseSoldat = posSoldat.getNumeroCase();
		int numCaseSoldatAdverse = posSoldatAdverse.getNumeroCase();
		
		// Attaque proche
		if(numCaseSoldatAdverse == (numCaseSoldat+1)) 
		{soldat.combat(soldatAdverse);} // à droite
		else if(numCaseSoldatAdverse == (numCaseSoldat-1)) 
		{soldat.combat(soldatAdverse);} // à gauche
		else if(numCaseSoldatAdverse == (numCaseSoldat+IConfig.LARGEUR_CARTE)) 
		{soldat.combat(soldatAdverse);} // en haut
		else if(numCaseSoldatAdverse == (numCaseSoldat-1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) 
		{soldat.combat(soldatAdverse);} // en haut
		else if(numCaseSoldatAdverse == (numCaseSoldat+1+IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) 
		{soldat.combat(soldatAdverse);} // en haut
		else if(numCaseSoldatAdverse == (numCaseSoldat-IConfig.LARGEUR_CARTE)) 
		{soldat.combat(soldatAdverse);} // en bas
		else if(numCaseSoldatAdverse == (numCaseSoldat-1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==0) 
		{soldat.combat(soldatAdverse);} // en bas 
		else if(numCaseSoldatAdverse == (numCaseSoldat+1-IConfig.LARGEUR_CARTE) && posSoldat.getRow()%2==1) 
		{soldat.combat(soldatAdverse);} // en bas
		else { // attaque à distance
			for(int i=0 ; i< soldat.getPortee() +1; i++) {
				if(numCaseSoldatAdverse <= (numCaseSoldat + IConfig.LARGEUR_CARTE*i  + soldat.getPortee()+i) && 
						numCaseSoldatAdverse >= numCaseSoldat + IConfig.LARGEUR_CARTE*i  - soldat.getPortee()-i) {
					soldat.combatDistance(soldatAdverse);
					return true;
				}
				else {
					continue;
				}
			}
			for(int i=0 ; i< soldat.getPortee() +1; i++) {
				if(numCaseSoldatAdverse <= (numCaseSoldat - IConfig.LARGEUR_CARTE*i  + soldat.getPortee()+i) && 
						numCaseSoldatAdverse >= numCaseSoldat - IConfig.LARGEUR_CARTE*i  - soldat.getPortee()-i) {
					soldat.combatDistance(soldatAdverse);
					return true ;
				}
				else {
					continue;
				}
			}
			
			return false ;
		}
		return true;
	}

	@Override
	public boolean mort(Soldat perso) {
		return perso.getPoints() <= 0 ;
		
	}

	
	/**
	 * Permet de deplacer le soldat ou attaquer un ennemi selon si la case est vide ou pas
	 * Si case vide, appelle la méthode deplaceSoldat(). Si case remplie par un monstre, appelle la méthode combatSoldat()
	 * Sinon rien car déplacement impossible ou ennemi loin (la portée n'est pas encore intégrée)
	 * @param pos : nouvelle position pointée par la souris
	 * @param posSoldat : position du soldat avant déplacement
	 * @param soldat : soldat à déplacer ou combattre avec un ennemi 
	 */
	@Override
	public boolean actionHeros(Position pos, Position posSoldat, Soldat soldat) {
		int cleHeros;
		
		if(pos.getNumeroCase() == posSoldat.getNumeroCase() ) { // si clique sur soldat sans déplacement, le soldat doit se reposer
			if(soldat.getPoints() < soldat.getTypeHeros().getPoints()) { // si points de vie soldat sont au maximum, le soldat ne se repose pas, le tour n'est pas joué 
				soldat.seReposer();
				return true ;
			}
		}
		
		if(estPositionVide(pos)) {
			// Recupere dans HashMap pour la déplacer
			cleHeros = getTabCases()[posSoldat.getNumeroCase()];
			// Vide la case où se trouver
			getTabCases()[posSoldat.getNumeroCase()] = -1 ;
	
			if(deplaceSoldat(pos, posSoldat, soldat)) { 
				// posSoldat a changé
				getTabCases()[posSoldat.getNumeroCase()] = cleHeros;
				return true; // action effectuée donc tour joué
			}
			else { 
				// posSoldat n'a pas changé
				getTabCases()[posSoldat.getNumeroCase()] = cleHeros;
				return false; // pas d'action effectuée donc tour non joué
			}
		}
		else if((PanneauJeu.number != -1) && listeSoldats.get(tabCases[pos.getNumeroCase()]) instanceof Monstre) {
			Soldat monstre = getListeSoldats().get( getTabCases()[pos.getNumeroCase()] );
			System.out.println("Monstre " + monstre.getTypeMonstre());
			//System.out.println("Points de vie du monstre avant l'attaque est " + monstre.getPoints());
			
			if(combatSoldat(pos, posSoldat, soldat, monstre)) { // si vrai le tour de heros est joué
				
				System.out.println("Points de vie du monstre après l'attaque est " + monstre.getPoints());
				if(mort(monstre)) { /* points de vie inferieure à 0*/
					getListeSoldats().remove(tabCases[pos.getNumeroCase()]); // supprime le monstre du HashMap
					getTabCases()[pos.getNumeroCase()] = -1 ; // vide la case dans la carte
					System.out.println("le monstre est mort") ;
				}
				pos.setNumeroCase(posSoldat.getNumeroCase()); // revient à la position initiale apres attaque
				return true; // attaque effectuee donc tour joué
			}
		}           
		
		return false; // pas deplacement ni attaque (tour n'est pas encore joué)
		
	}
	
	/**
	 * Général ordinateur qui joue le role Monstre
	 * Deplace un Monstre ou attaque un Heros
	 * @return
	 */
	public boolean actionMonstre() {
		Soldat monstre, heros;
		Position posMonstre, pos;
		int cleMonstre;
		// Choisie un monstre dans la carte
		monstre = trouveMonstre();
		// Recupere sa position
		posMonstre = monstre.getPosition();
		// Trouve un heros en cases adjacentes
		heros = trouveHerosAdverse(posMonstre);
		
		if(heros != null) { // Attaque effectue si heros se trouve en case adjacente
			System.out.println("Heros " + heros.getTypeHeros());
			//System.out.println("Points de vie du heros avant l'attaque est " + heros.getPoints());
			monstre.combat(heros);
			System.out.println("Points de vie du heros après l'attaque est " + heros.getPoints());
			if(mort(heros)) { /* points de vie inferieure à 0*/
				getListeSoldats().remove(tabCases[heros.getPosition().getNumeroCase()]); // supprime le heros du HashMap
				getTabCases()[heros.getPosition().getNumeroCase()] = -1 ; //vide la case dans la carte
				System.out.println("le heros est mort");
			}
		}
		else { // deplacement sinon
			// Trouve une position vide adjacente
			pos = trouvePositionVide(posMonstre);
			// Recupere dans HashMap pour la déplacer
			cleMonstre = getTabCases()[posMonstre.getNumeroCase()];
			// Vide la case où se trouver
			getTabCases()[posMonstre.getNumeroCase()] = -1 ;
		
			// Teste si deplacement possible (fonction utilisé aussi pour heros, mais dans ce cas c'est toujours vrai)
			if(this.deplaceSoldat(pos, posMonstre, monstre)){
				// Place la cle de monstre dans la nouvelle case
				getTabCases()[posMonstre.getNumeroCase()] = cleMonstre;
			}
		}
		return true;
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