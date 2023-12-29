package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe exécutable contenant la fenetre de jeu
 */
public class FenetreJeu extends JFrame{
    static String fullpathFolder="";

	static PanneauJeu carteJeu;
	static JButton menu;
	static JButton sauvgarder;
	public static JPanel panmenu ;
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
	 * @brief Renvoie le tableau de cases de la carte
	 */
	public int[] getTabCases() {
		return this.tabCases;
	}
	
	/**
	 * @brief Renvoie HashMap contenant les informations sur les soldats
	 * @return Hashmap de liste de soldats
	 */
	public HashMap<Integer, Soldat> getListeSoldats(){
		return this.listeSoldats;
	}
	
	/**
	 * @brief Renvoie HashMap contenant les informations sur les obstacles
	 * @return Hashmap de liste d'obstacles
	 */
	public HashMap<Integer,Obstacle> getListeObstacle(){
		return this.listeObstacle;
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
	
	/**
	 * @brief ajout de obstacle dans carte
	 * @param obstacle : l'objet obstacle créé à ajouter
	 */
	public void ajoutObstacle(Obstacle obstacle) {
		this.listeObstacle.put(FenetreJeu.indice, obstacle);
		this.tabCases[obstacle.getPosition().getNumeroCase()] = FenetreJeu.indice;
		FenetreJeu.indice += 1;
	}
	
	/**
	 * @brief Efface les soldats de la carte
	 */
	public void removeSoldats() {
		this.listeSoldats.clear();
	}
	
	/**
	 * @brief Efface les obstacles de la carte
	 */
	public void removeObstacles() {
		this.listeObstacle.clear();
	}
	
	public void nouvellePartie() {
		Soldat h;
		Obstacle o;
		FenetreJeu.indice = 0;
		
		// Efface les soldats et obstacles de la partie précédente
		this.removeSoldats();
		this.removeObstacles();
		for(int i=0; i<IConfig.HAUTEUR_CARTE*IConfig.LARGEUR_CARTE; i++) {
			this.tabCases[i] = -1; // toutes les cases de la carte sont vides au début
		}
		
		// Ajout de soldats ;
		for(int i = 0 ; i < IConfig.NB_HEROS;) {
			h = new Heros();
			if(this.tabCases[h.getPosition().getNumeroCase()] == -1) {
				this.ajoutSoldat(h);
				i++;
			}
		}
		for(int i = 0 ; i < IConfig.NB_MONSTRES;) {
			h = new Monstre();
			if(this.tabCases[h.getPosition().getNumeroCase()] == -1) {
				this.ajoutSoldat(h);
				i++;
			}
		}				
		// Ajout d'obstacles ;
		for(int i = 0 ; i < IConfig.NB_OBSTACLES;) {
			o = new Obstacle();
			if(this.tabCases[o.getPosition().getNumeroCase()] == -1) {
				this.ajoutObstacle(o);
				i++;
			}
		}
		
	}
	
	
	public void sauvegarderPartie() {
		sauvegarde_wargame gameState = new sauvegarde_wargame(listeSoldats, listeObstacle, tabCases);
        JFileChooser folderchooser = new JFileChooser();
        int cheminfol = folderchooser.showSaveDialog(null);
        fullpathFolder = folderchooser.getSelectedFile().getAbsolutePath();
        
        System.out.println("path de notre fichier " +fullpathFolder);
		
		try (FileOutputStream fileOut = new FileOutputStream(fullpathFolder);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameState);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
	
	
	public void chargerPartie() {
		JFileChooser folderchooser = new JFileChooser();
		int cheminfol = folderchooser.showSaveDialog(null);
		fullpathFolder = folderchooser.getSelectedFile().getAbsolutePath();

	    try (FileInputStream fileIn = new FileInputStream(fullpathFolder);
	         ObjectInputStream in = new ObjectInputStream(fileIn)) {
	        sauvegarde_wargame gameState = (sauvegarde_wargame) in.readObject();
	        
	        
	        System.out.println("contenu des tables :: ");
	        
	        //verifier le contenue de nos tables
	        System.out.println("Liste Soldats: " + gameState.getListeSoldats());
	        System.out.println("Liste Obstacles: " + gameState.getListeObstacle());
	        System.out.println("Tab Cases: " + Arrays.toString(gameState.getTabCases()));
	        
	        this.listeSoldats = gameState.getListeSoldats();
	        this.listeObstacle = gameState.getListeObstacle();
	        this.tabCases = gameState.getTabCases();
	        

	        System.out.println("path de notre fichier " + fullpathFolder);
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
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
                Image image = imageicon.getImage();
                
                
            	// Titre de la fenetre
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
                carteJeu = new PanneauJeu(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE, IConfig.NB_PIX_CASE,
						f.getTabCases(), f.getListeSoldats() , f.getListeObstacle());
                
                f.setIconImage(imageicon.getImage());
                f.setLayout(null) ;
               
                
                menu = new JButton(" Menu ");
                menu.setBounds(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/6,220,100,40);
                
                
                sauvgarder = new JButton(" Sauvegarder la Partie ");
                sauvgarder.setBounds(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5,300,170,40);
                
                
                //boutton1s à cliquer dans le menu principale
                JButton buttonNewGame = new JButton("Nouvelle partie");
                buttonNewGame.setSize(150, 30);
                buttonNewGame.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, IConfig.LONGUEUR_FENETRE/2);
                //buttonNewGame.setHorizontalAlignment(JButton.CENTER); buttonNewGame.setVerticalAlignment(JButton.EAST);
                buttonNewGame.setHorizontalTextPosition(JButton.CENTER); 
                buttonNewGame.setVerticalTextPosition(JButton.CENTER);
                buttonNewGame.setFocusable(false);
                //pour effectuer une action si on clique sur le button
                buttonNewGame.addActionListener(new ActionListener(){
					// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
					public void actionPerformed(ActionEvent e) {
						f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvgarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.repaint();
						
						// Initialise les soldats et obstacles
						f.nouvellePartie();
						
						System.out.println("Nouvelle partie !");
						
					}
                });
                
                JButton continue_partie = new JButton("Continue partie");
                continue_partie.setSize(150, 30);
                continue_partie.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, 400);
                continue_partie.setHorizontalTextPosition(JButton.CENTER); 
                continue_partie.setVerticalTextPosition(JButton.CENTER);
                continue_partie.setFocusable(false);
                continue_partie.addActionListener(new ActionListener(){
					// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
					public void actionPerformed(ActionEvent e) {
                		
						f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvgarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.repaint();
						
						System.out.println("Continue la partie !");
					}
                });
                
                
                JButton parti_sauv = new JButton("partie sauvegarder");
                parti_sauv.setSize(150, 30);
                parti_sauv.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, 450);
                parti_sauv.setHorizontalTextPosition(JButton.CENTER); 
                parti_sauv.setVerticalTextPosition(JButton.CENTER);
                parti_sauv.setFocusable(false);
                parti_sauv.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	f.chargerPartie() ;
                    	f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvgarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.repaint();
                        System.out.println("Lets finish this partie !");
                    }
                });
                
                
                JPanel panelCouverture = new JPanel() {
                	@Override 
                	public void paintComponent(Graphics g){
                		super.paintComponent(g);
                		g.drawImage(image, 0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE, null);
                	}
                };
                panelCouverture.setBackground(IConfig.COULEUR_VIDE);
                panelCouverture.setBounds(0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
                panelCouverture.setLayout(null);
                panelCouverture.add(buttonNewGame);
                panelCouverture.add(continue_partie);
                panelCouverture.add(parti_sauv);
                

                // Ajout des composantes a la fenetre
                f.add(panelCouverture);
                
                menu.addActionListener(new ActionListener(){
        			// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
        			public void actionPerformed(ActionEvent e) {
                		
						f.getContentPane().removeAll(); // efface la carte et les boutons menu et sauvegarde
                		f.repaint();
                		f.add(panelCouverture); // ajoute les boutons du menu principal
                		
        				System.out.println("Retour menu principal !");
        			}
                });
                sauvgarder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                        f.sauvegarderPartie();
                        System.out.println("Partie sauvegardée !");
                    }
                });
            }
        };
        //GUI must start on EventDispatchThread:
        SwingUtilities.invokeLater(gui);
    }
}