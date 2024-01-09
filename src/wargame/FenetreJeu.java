package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe exécutable contenant la fenetre de jeu
 */
public class FenetreJeu extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String fullpathFolder="";

	static PanneauJeu carteJeu;
	static JButton menu;
	static JButton sauvegarder;
	HashMap<Integer, Soldat> listeSoldats = new HashMap<Integer, Soldat>();
	HashMap<Integer,Obstacle> listeObstacle = new HashMap<Integer,Obstacle>();
	int tabCases[];
	sauvegarde_wargame gameState;
	// variable static qui joue le son du jeu
	static Clip clip ;
	// variable static qui s'incrémente à chaque ajout de soldat dans la hashmap
	static int indice = 0 ;
	
	/**
	 * Joue le son donné en paramétre
	 * @param filePath : le chemin contenant le son à exécuter
	 */
	public static void playMusic(String filePath) {
	    if (clip != null && clip.isOpen()) {
	        clip.close();
	    }
	    try (InputStream audioSrc = FenetreJeu.class.getResourceAsStream(filePath);
	         BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc)) {
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
	        clip = AudioSystem.getClip();
	        clip.open(audioStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}



	
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
	 * @return renvoie un tableau de type int
	 */
	public int[] getTabCases() {
		return this.tabCases;
	}
	
	/**
	 * Renvoie HashMap contenant les informations sur les soldats
	 * @return Hashmap de liste de soldats
	 */
	public HashMap<Integer, Soldat> getListeSoldats(){
		return this.listeSoldats;
	}
	
	/**
	 * Renvoie HashMap contenant les informations sur les obstacles
	 * @return Hashmap de liste d'obstacles
	 */
	public HashMap<Integer,Obstacle> getListeObstacle(){
		return this.listeObstacle;
	}
	
	/**
	 * Ajoute soldat dans carte et dans liste des soldats
	 * @param soldat : l'objet soldat créé à ajouter
	 */
	public void ajoutSoldat(Soldat soldat) {
		this.listeSoldats.put(FenetreJeu.indice, soldat);
		this.tabCases[soldat.getPosition().getNumeroCase()] = FenetreJeu.indice; // ===> tabCases[numCase] = indiceHashmap;
		FenetreJeu.indice += 1;
	}
	
	/**
	 * Ajoute de obstacle dans carte
	 * @param obstacle : l'objet obstacle créé à ajouter
	 */
	public void ajoutObstacle(Obstacle obstacle) {
		this.listeObstacle.put(FenetreJeu.indice, obstacle);
		this.tabCases[obstacle.getPosition().getNumeroCase()] = FenetreJeu.indice;
		FenetreJeu.indice += 1;
	}
	
	/**
	 * Efface tous les soldats de la liste des soldats
	 */
	public void removeSoldats() {
		this.listeSoldats.clear();
	}
	
	/**
	 * Efface tous les obstacles de la liste des obstacles
	 */
	public void removeObstacles() {
		this.listeObstacle.clear();
	}
	
	/**
	 * Commence une nouvelle partie est initilisant à nouveau : liste des soldats, liste des obstacles
	 * Place les soldats et les obstacles aléatoirement dans la carte
	 */
	public void nouvellePartie() {
		Soldat h;
		Obstacle o;
		FenetreJeu.indice = 0;
		PanneauJeu.tour = 0 ;
		
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
	
	/**
	 * Ouvre un menu de sauvegarde
	 * Effectue une sauvegarde d'une partie et la stocke dans un fichier .ser dont l'utilisateur choisit le nom
	 */
	public void sauvegarderPartie() {
	    gameState = new sauvegarde_wargame(listeSoldats, listeObstacle, tabCases);
	    JFileChooser folderchooser = new JFileChooser(getClass().getResource("/wargame/sauvegardes").getFile());
	    folderchooser.showSaveDialog(null);
	    File selectedFile = folderchooser.getSelectedFile();
	    if (selectedFile != null) {
	        fullpathFolder = selectedFile.toURI().getPath();
	    }

	    try (FileOutputStream fileOut = new FileOutputStream(fullpathFolder);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	        out.writeObject(gameState);
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	}


	
	/**
	 * Charge une partie à partir d'une sauvegarde choisie dans le menu de sauvegarde ouvert
	 */
	public void chargerPartie() {
	    PanneauJeu.tour = 0;

	    JFileChooser folderchooser = new JFileChooser(getClass().getResource("/wargame/sauvegardes").getFile());
	    folderchooser.showSaveDialog(null);
	    File selectedFile = folderchooser.getSelectedFile();
	    if (selectedFile != null) {
	        fullpathFolder = selectedFile.toURI().getPath();
	    }

	    try (InputStream fileIn = getClass().getResourceAsStream(fullpathFolder)) {
	        if (fileIn != null) {
	            try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
	                this.gameState = (sauvegarde_wargame) in.readObject();

	                System.out.println("contenu des tables :: ");

	                //verifier le contenue de nos tables
	                System.out.println("Liste Soldats: " + this.gameState.getListeSoldats());
	                System.out.println("Liste Obstacles: " + this.gameState.getListeObstacle());
	                System.out.println("Tab Cases: " + Arrays.toString(this.gameState.getTabCases()));

	                this.listeSoldats = this.gameState.getListeSoldats();
	                this.listeObstacle = this.gameState.getListeObstacle();
	                this.tabCases = this.gameState.getTabCases();

	                System.out.println("path de notre fichier " + fullpathFolder);
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Cannot open file " + fullpathFolder);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}




	/*______________________________ PROGRAMME PRINCIPALE ___________________________________ */
	
	/**
	 * methode principale
	 * @param args - arguments en ligne de commande
	 */
	
	public static void main(final String[] args) {
		Runnable gui = new Runnable() {
            public void run() {
            	// Logo de la fenetre et debut du jeu
            	ImageIcon imageicon = new ImageIcon(getClass().getResource("/wargame/images/backwargame.jpg"));
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
                
                
                /*____________________// RETOUR AU MENU PRINCIPAL __________________________*/
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*__________________________________________________________________________*/
                menu = new JButton(" Menu ");
                menu.setBounds(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/6,220,100,40);
                menu.setBackground(new Color(49, 74, 51));
                menu.setForeground(Color.WHITE);
                
                Carte.action_Monstre.setBounds((int)(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5.5),480,170,40);
                Carte.action_Monstre.setForeground(new Color(49, 74, 51));
                Carte.action_Monstre.setFont(new Font("Tahoma", Font.BOLD, 15));
                
                /*______________________ SAUVEGARDER UNE PARTIE ____________________________*/
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*__________________________________________________________________________*/
                sauvegarder = new JButton(" Sauvegarder la Partie ");
                sauvegarder.setBounds(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5,300,170,40);
                sauvegarder.setBackground(new Color(49, 74, 51));
                sauvegarder.setForeground(Color.WHITE);
                sauvegarder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                        f.sauvegarderPartie();
                        System.out.println("Partie sauvegardée !");
                    }
                });                
                
                /*____________________ COMMENCER UNE NOUVELLE PARTIE _______________________*/
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*__________________________________________________________________________*/
                //boutton1s à cliquer dans le menu principale
                JButton buttonNewGame = new JButton("Nouvelle partie");
                buttonNewGame.setSize(150, 30);
                buttonNewGame.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, IConfig.LONGUEUR_FENETRE/2);
                //buttonNewGame.setHorizontalAlignment(JButton.CENTER); buttonNewGame.setVerticalAlignment(JButton.EAST);
                buttonNewGame.setHorizontalTextPosition(JButton.CENTER); 
                buttonNewGame.setVerticalTextPosition(JButton.CENTER);
                buttonNewGame.setFocusable(false);
                buttonNewGame.setBackground(new Color(49, 74, 51));
                buttonNewGame.setForeground(Color.WHITE);
                //pour effectuer une action si on clique sur le button
                buttonNewGame.addActionListener(new ActionListener(){
					// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
					public void actionPerformed(ActionEvent e) {
						f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvegarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.add(Carte.action_Monstre,BorderLayout.CENTER);
						f.repaint();
						
						// Initialise les soldats et obstacles
						f.nouvellePartie();
		                
						Carte.action_Monstre.setText(" Action Monstres ");
						System.out.println("Nouvelle partie !");
						
					    playMusic("/songs/deroulementPartie.wav");
					    
					}
                });
                
                
                /*_____________________ CONTINUER UNE PARTIE _______________________________*/
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*__________________________________________________________________________*/
                
                JButton continue_partie = new JButton("Continuer partie");
                continue_partie.setSize(150, 30);
                continue_partie.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, 400);
                continue_partie.setHorizontalTextPosition(JButton.CENTER); 
                continue_partie.setVerticalTextPosition(JButton.CENTER);
                continue_partie.setFocusable(false);
                continue_partie.setBackground(new Color(49, 74, 51));
                continue_partie.setForeground(Color.WHITE);
                continue_partie.addActionListener(new ActionListener(){
					// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
					public void actionPerformed(ActionEvent e) {
                		
						f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvegarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.add(Carte.action_Monstre,BorderLayout.CENTER);
						f.repaint();
						
		                Carte.action_Monstre.setText(" Action Monstres ");
						System.out.println("Continue la partie !");
						
					    playMusic("/songs/deroulementPartie.wav");

					}
                });
                
                /*_____________________ CHARGER UNE SAUVEGARDE _____________________________*/
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*                                                                          */
                /*__________________________________________________________________________*/
                
                JButton parti_sauv = new JButton("Charger partie");
                parti_sauv.setSize(150, 30);
                parti_sauv.setLocation(IConfig.LARGEUR_FENETRE/2 - IConfig.LARGEUR_FENETRE/13, 450);
                parti_sauv.setHorizontalTextPosition(JButton.CENTER); 
                parti_sauv.setVerticalTextPosition(JButton.CENTER);
                parti_sauv.setFocusable(false);
                parti_sauv.setBackground(new Color(49, 74, 51));
                parti_sauv.setForeground(Color.WHITE);
                parti_sauv.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	// récuper les données de la sauvegarde
                    	f.chargerPartie() ;
                    	carteJeu = new PanneauJeu(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE, IConfig.NB_PIX_CASE,
        						f.getTabCases(), f.getListeSoldats() , f.getListeObstacle());
                    	f.getContentPane().removeAll(); // Efface tous les panels du menu principale
						f.add(menu);
						f.add(sauvegarder);
						f.add(carteJeu); // Ajoute le panel avec la carte de jeu
						f.add(Carte.action_Monstre,BorderLayout.CENTER);
						f.repaint();
						
		                Carte.action_Monstre.setText(" Action Monstres ");
                        System.out.println("Lets finish this partie !");
                    }
                });
                
                JPanel panelCouverture = new JPanel() {
                	/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override 
                	public void paintComponent(Graphics g){
                		super.paintComponent(g);
                		g.drawImage(image, 0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE, null);
                	}
                };
                panelCouverture.setBackground(new Color(49, 74, 51));
                panelCouverture.setBounds(0, 0, IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
                panelCouverture.setLayout(null);
                panelCouverture.add(buttonNewGame);
                panelCouverture.add(continue_partie);
                panelCouverture.add(parti_sauv);

                              
                // Ajout des composantes a la fenetre
                f.add(panelCouverture);
                playMusic("/songs/debutJeu.wav");
                
                menu.addActionListener(new ActionListener(){
        			// On ajoute le panneau de jeu sur lequel on va jouer
                	@Override
        			public void actionPerformed(ActionEvent e) {
                		
						f.getContentPane().removeAll(); // efface la carte et les boutons menu et sauvegarde
                		f.repaint();
                		f.add(panelCouverture); // ajoute les boutons du menu principal
                		
        			    playMusic("/songs/debutJeu.wav");

                		
        				System.out.println("Retour menu principal !");
        			}
                });
                
                
            }
        };
        //GUI must start on EventDispatchThread:
        SwingUtilities.invokeLater(gui);
    }
}