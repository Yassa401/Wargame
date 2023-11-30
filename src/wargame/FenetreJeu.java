/**
 * 
 */
package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.HashMap;

/**
 * Classe exécutable contenant la fenetre de jeu
 */
public class FenetreJeu extends JFrame{
	
	
	PanneauJeu carteJeu ;
	HashMap<Integer, Soldat> listeSoldats = new HashMap<Integer, Soldat>();
	HashMap<Integer,Obstacle> listeObstacle = new HashMap<Integer,Obstacle>();
	int tabCases[];
	// variable static qui s'incrémente à chaque ajout de soldat dans la hashmap
	static int indice = 0 ;
	
	/**
	 * @brief Constructeur qui fait appel a la classe mere JFrame
	 * @param nom  le nom de la fenetre qu'on va creer
	 */
	FenetreJeu(String nom){
		super(nom) ;
		// Initialise un nouveau tableau avec le nombre de cases defini dans l'interface IConfig
		this.tabCases = new int[IConfig.LARGEUR_CARTE*IConfig.HAUTEUR_CARTE];
		for(int i=0; i<IConfig.HAUTEUR_CARTE*IConfig.LARGEUR_CARTE; i++) {
			this.tabCases[i] = -1; // toutes les cases de la carte sont vides au début
		}
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
	public PanneauJeu getPanneau() {
		return this.carteJeu;
	}
	
	
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
	
	
	/*______________________________ PROGRAMME PRINCIPALE ___________________________________ */
	
	/**
	 * methode principale
	 * @param args - arguments en ligne de commande
	 * @return
	 */
	
	public static void main(final String[] args) {
		Runnable gui = new Runnable() {
            public void run() {
            	// Logo de la fenetre et debut du jeu
                ImageIcon imageicon = new ImageIcon("src/wargame/images/wargame2.jpg") ;
            	
            	// Titre de la fenetre
                JFrame.setDefaultLookAndFeelDecorated(true);
                final FenetreJeu f = new FenetreJeu("Wargame");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // Position de la fenetre sur l'ecran
                f.setLocation(IConfig.POSITION_X, IConfig.POSITION_Y);
                // Dimensions de la fenetre
                f.setSize(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE );
                // Impossible de changer les dimensions de la fenetre
                f.setResizable(false);
                // Fenetre visible
                f.setVisible(true);
                
                
                f.setIconImage(imageicon.getImage());
                f.setLayout(null) ;
                //f.add(new Hexagone(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE, IConfig.NB_PIX_CASE));
                
                //Ajout d'une couverture en début de jeu
                JLabel labelLogo = new JLabel();
                labelLogo.setIcon(imageicon);
                labelLogo.setHorizontalAlignment(JLabel.CENTER);
                labelLogo.setBackground(Color.BLUE);
                
                //boutton1s à cliquer dans le menu principale
                JButton buttonNewGame = new JButton("Nouvelle partie");
                buttonNewGame.setSize(200, 30);
                buttonNewGame.setHorizontalAlignment(JButton.CENTER);
                buttonNewGame.setVerticalAlignment(JButton.NORTH);
                buttonNewGame.setHorizontalTextPosition(JButton.CENTER);
                buttonNewGame.setVerticalTextPosition(JButton.CENTER);
                buttonNewGame.setFocusable(false);
                //pour effectuer une action si on clique sur le button
                buttonNewGame.addActionListener(new ActionListener(){
					// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
					public void actionPerformed(ActionEvent e) {
						f.carteJeu = new PanneauJeu(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE, IConfig.NB_PIX_CASE,
								f.getTabCases(), f.getListeSoldats() , f.getListeObstacle());
						
						f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.repaint();
						f.add(f.carteJeu); // Ajoute le panel avec la carte de jeu
						Soldat h;
						Obstacle o;
						// Test ajout de soldat ;
						for(int i = 0 ; i < IConfig.NB_HEROS; i++) {
							h = new Heros();
							if(f.tabCases[h.getPosition().getNumeroCase()] == -1) {
								f.ajoutSoldat(h);
							}
						}
						for(int i = 0 ; i < IConfig.NB_MONSTRES; i++) {
							h = new Monstre();
							if(f.tabCases[h.getPosition().getNumeroCase()] == -1) {
								f.ajoutSoldat(h);
							}
						}
						for(int i = 0 ; i < IConfig.NB_OBSTACLES; i++) {
							o = new Obstacle();
							if(f.tabCases[o.getPosition().getNumeroCase()] == -1) {
								f.ajoutObstacle(o);
							}
						}
						
						// Test ajout d'obstacle ;
						
						System.out.println("Nouvelle partie !");
					}
                });
                
                JPanel panelCouverture = new JPanel();
                panelCouverture.setBackground(IConfig.COULEUR_VIDE);
                panelCouverture.setBounds(0, 0, IConfig.LARGEUR_FENETRE, 2*IConfig.LONGUEUR_FENETRE/3);
                
                panelCouverture.add(labelLogo);
                
                JPanel panelMenu = new JPanel();
                panelMenu.setBackground(IConfig.COULEUR_VIDE);
                panelMenu.setBounds(0, 2*IConfig.LONGUEUR_FENETRE/3, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE/3);
				panelMenu.add(buttonNewGame);

                // Ajout des composantes a la fenetre
                f.add(panelCouverture);
                f.add(panelMenu);
                
             
                
     
                
                
            }
        };
        //GUI must start on EventDispatchThread:
        SwingUtilities.invokeLater(gui);
    }

}
