/**
 * 
 */
package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Classe exécutable contenant la fenetre de jeu
 */
public class FenetreJeu extends JFrame{
	
	/*
	 * Constructeur qui fait appel a la classe mere JFrame
	 * @param nom - le nom de la fenetre qu'on va creer
	 */
	FenetreJeu(String nom){
		super(nom) ;
	}
	
	/* 
	 * affiche une fenetre vide
	 * @return  
	 */
	@Override
	public void repaint() {
		this.setBackground(IConfig.COULEUR_VIDE);
	}
	/*
	 * methode principale
	 * @param args - arguments en ligne de commande
	 * @return
	 */
	
	public static void main(final String[] args) {
		Runnable gui = new Runnable() {
            public void run() {
            	// Logo de la fenetre et debut du jeu
                ImageIcon imageicon = new ImageIcon("src/wargame/wargame2.jpg") ;
            	
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
                
                //Bouttons à cliquer dans le menu principale
                JButton buttonNewGame = new JButton("Nouvelle partie");
                buttonNewGame.setSize(200, 30);
                buttonNewGame.setHorizontalAlignment(JButton.CENTER);
                buttonNewGame.setVerticalAlignment(JButton.NORTH);
                buttonNewGame.setHorizontalTextPosition(JButton.CENTER);
                buttonNewGame.setVerticalTextPosition(JButton.CENTER);
                buttonNewGame.setFocusable(false);
                //pour effectuer une action si on clique sur le button
                buttonNewGame.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Nouvelle partie !");
						f.getContentPane().removeAll();
						f.repaint();
						/*try {
						Thread.sleep(2);
						}
						catch(InterruptedException exception) {
							System.out.println("L'execution a ete interrompu") ;
						}*/
						
						//on ajoute la carte du jeu (probleme: la carte ne s'affiche pas !)	
						f.add(new PanneauJeu(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE, IConfig.NB_PIX_CASE));
						System.out.println("ici");
					}
                });
                
                JPanel panelCouverture = new JPanel();
                panelCouverture.setBackground(Color.BLUE);
                panelCouverture.setBounds(0, 0, IConfig.LARGEUR_FENETRE, 2*IConfig.LONGUEUR_FENETRE/3);
                
                panelCouverture.add(labelLogo);
                
                JPanel panelMenu = new JPanel();
                panelMenu.setBackground(Color.RED);
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
