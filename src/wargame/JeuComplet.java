package wargame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JeuComplet extends JFrame {
	public HashMap<Integer, Soldat> listeSoldats = new HashMap<Integer, Soldat>();
	public HashMap<Integer,Obstacle> listeObstacle = new HashMap<Integer,Obstacle>();
	public int tabCases[];
	public ImageIcon imageicon;
	
	public JeuComplet(){
		this.tabCases = new int[IConfig.LARGEUR_CARTE * IConfig.HAUTEUR_CARTE];
        for (int i = 0; i < IConfig.HAUTEUR_CARTE * IConfig.LARGEUR_CARTE; i++) {
            this.tabCases[i] = -1;
        }
		imageicon = new ImageIcon("src/wargame/images/wargame2.jpg") ;
        this.setIconImage(imageicon.getImage());
        this.setLayout(null) ;
        
        //boutton1s à cliquer dans le menu principale
        JButton buttonNewGame = new JButton("Nouvelle partie");
        buttonNewGame.setSize(150, 30);
        buttonNewGame.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/11, IConfig.LONGUEUR_FENETRE/2);
        //buttonNewGame.setHorizontalAlignment(JButton.CENTER); buttonNewGame.setVerticalAlignment(JButton.EAST);
        buttonNewGame.setHorizontalTextPosition(JButton.CENTER); buttonNewGame.setVerticalTextPosition(JButton.CENTER);
        buttonNewGame.setFocusable(false);
        //pour effectuer une action si on clique sur le button
        buttonNewGame.addActionListener(new ActionListener(){
			// On ajoute le panneau de jeu sur lequel on va jouer
        	@Override
			public void actionPerformed(ActionEvent e) {
        		
        		Soldat h;
				Obstacle o;
				// Test ajout de soldat ;
				for(int i = 0 ; i < IConfig.NB_HEROS; i++) {
					h = new Heros();
					if(tabCases[h.getPosition().getNumeroCase()] == -1) {
						ajoutSoldat(h);
					}
				}
				for(int i = 0 ; i < IConfig.NB_MONSTRES; i++) {
					h = new Monstre();
					if(tabCases[h.getPosition().getNumeroCase()] == -1) {
						ajoutSoldat(h);
					}
				}
				for(int i = 0 ; i < IConfig.NB_OBSTACLES; i++) {
					o = new Obstacle();
					if(tabCases[o.getPosition().getNumeroCase()] == -1) {
						ajoutObstacle(o);
					}
				}
				
				System.out.println("Nouvelle partie !");
			}
        });
        
        JPanel panelCouverture = new JPanel() {
        	@Override 
        	public void paintComponent(Graphics g){
        		super.paintComponent(g);
        		g.drawImage(imageicon.getImage(), 0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE, null);
        	}
        };
        panelCouverture.setBackground(IConfig.COULEUR_VIDE);
        panelCouverture.setBounds(0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
        panelCouverture.setLayout(null);
        panelCouverture.add(buttonNewGame);
        

        // Ajout des composantes a la fenetre
        this.add(panelCouverture);
        
        
        
        
        this.setTitle("WarGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Position de la fenetre sur l'ecran
        this.setLocation(IConfig.POSITION_X, IConfig.POSITION_Y);
        // Dimensions de la fenetre
        this.setSize(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE );
        // Impossible de changer les dimensions de la fenetre
        this.setResizable(false);
        // Fenetre visible
        this.setVisible(true);
        
	}
	
	
	/**
	 * Renvoie le tableau de cases de la carte
	 */
	public int[] getTabCases() {
		return this.tabCases;
	}
	
	/**
	 * Renvoie HashMap contenant les informations sur les soldats
	 */
	public HashMap<Integer, Soldat> getListeSoldats(){
		return this.listeSoldats;
	}
	
	public HashMap<Integer,Obstacle> getListeObstacle(){
		return this.listeObstacle;
	}
	
	/**
	 * Renvoie le panel contenant la carte de jeu
	 */
	
	
	
	/**
	 * @brief ajout de soldat dans carte
	 * @param Soldat : l'objet soldat créé à ajouter
	 */
	public void ajoutSoldat(Soldat soldat) {
		this.listeSoldats.put(FenetreJeu.indice, soldat);
		this.tabCases[soldat.getPosition().getNumeroCase()] = FenetreJeu.indice; // ===> tabCases[numCase] = indiceHashmap;
		FenetreJeu.indice += 1;
	}
	public void ajoutObstacle(Obstacle obstacle) {
		this.listeObstacle.put(FenetreJeu.indice, obstacle);
		this.tabCases[obstacle.getPosition().getNumeroCase()] = FenetreJeu.indice;
		FenetreJeu.indice += 1;
	}
	
	/** 
	 * @brief affiche une fenetre vide
	 * @return  
	 */
	@Override
	public void repaint() {
		this.setBackground(IConfig.COULEUR_VIDE);
	}
	
}
