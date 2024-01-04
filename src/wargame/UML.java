package wargame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import uml.aggregation;
import wargame.ISoldat.TypesH;
import wargame.ISoldat.TypesM;
import wargame.Obstacle.TypeObstacle;

@startuml Hello world

package "WarGame" #DDDDDD {
	class Carte implements ICarte {
		+ HashMap<Integer, Soldat> listeSoldats
	    + HashMap<Integer, Obstacle> listeObstacle
	    + int tabCases[]
	    - Random random
		+ Carte(int tabCases[], HashMap<Integer, Soldat> listeSoldats,HashMap<Integer, Obstacle> listeObstacle)
		+ int[] getTabCases()
		+ HashMap<Integer, Soldat> getListeSoldats()
		+ HashMap<Integer,Obstacle> getListeObstacle()
		+ boolean estPositionVide(Position pos)
		+ Position trouvePositionVide()
		+ Position trouvePositionVide(Position pos) 
		+ Monstre trouveMonstre() 
		+ Heros trouveHerosAdverse(Position pos)
		+ Heros trouveHeros() 
		+ Heros trouveHeros(Position pos) 
		+ boolean deplaceSoldat(Position pos, Position posSoldat, Soldat soldat)
		+ boolean combatSoldat(Position posSoldatAdverse, Position posSoldat, Soldat soldat, Soldat soldatAdverse)
		+ boolean mort(Soldat perso) 
		+ boolean actionHeros(Position pos, Position posSoldat, Soldat soldat) 
		+ boolean actionMonstre()
		+ void jouerSoldats(PanneauJeu pj) 
		+ void toutDessiner(Graphics g) 
	}
	class FenetreJeu extends JFrame{
		+ {static} String fullpathFolder
		+ {static} PanneauJeu carteJeu
		+ {static} JButton menu
		+ {static} JButton sauvgarder
		+ {static} JPanel panmenu 
		+ HashMap<Integer, Soldat> listeSoldats 
		+ HashMap<Integer,Obstacle> listeObstacle 
		+ int tabCases[]
		+ {static} int indice
		+ FenetreJeu(String nom)
		+ int[] getTabCases()
		+ HashMap<Integer, Soldat> getListeSoldats()
		+ HashMap<Integer,Obstacle> getListeObstacle()
		+ void ajoutSoldat(Soldat soldat) 
		+ void ajoutObstacle(Obstacle obstacle)
		+ void removeSoldats() 
		+ void removeObstacles() 
		+ void nouvellePartie() 
		+ void sauvegarderPartie() 
		+ void chargerPartie() 
		+ {static} void main(final String[] args)
	}
	Soldat o-- FenetreJeu 
	Obstacle o-- FenetreJeu
	PanneauJeu o-- FenetreJeu

	class Heros extends Soldat{
		- TypesH typeHerosAlea
		+ Heros()
		+ TypesH getTypeHeros()
		# TypesM getTypeMonstre() 
		+ void combat(Soldat soldat)
		+ void combatDistance(Soldat soldat) 
	}

	interface ICarte {
		Position trouvePositionVide()
		Position trouvePositionVide(Position pos) 
		Heros trouveHeros()
		Heros trouveHeros(Position pos)
		boolean deplaceSoldat(Position pos, Position posSoldat, Soldat soldat)
		boolean combatSoldat(Position posMonstre, Position posSoldat, Soldat soldat, Soldat monstre)
		boolean mort(Soldat perso)
		boolean actionHeros(Position pos, Position posSoldat, Soldat soldat)
		void jouerSoldats(PanneauJeu pj)
		void toutDessiner(Graphics g)
	}
	interface IConfig {
		{static} ~ int LARGEUR_CARTE = 15
		{static} ~ int HAUTEUR_CARTE = 14
		{static} ~ int NB_PIX_CASE = 30
		{static} ~ int POSITION_X = 400, POSITION_Y = 200
		{static} ~ int LARGEUR_FENETRE = 1000, LONGUEUR_FENETRE = 700 
		int NB_HEROS = 6 
		int NB_MONSTRES = 15
		int NB_OBSTACLES = 7
		Color COULEUR_VIDE = Color.white, COULEUR_INCONNU = Color.lightGray
		Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black
		Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink
		Color COULEUR_EAU = Color.blue, COULEUR_FORET = Color.green, COULEUR_ROCHER = Color.gray
		{static} ~ int SIZE_CHARACTER = 35
	}
	interface ISoldat {
		+ {static} enum TypesH 
		+ {static} enum TypesM 
		int getPoints() 
		int getTour() 
		int getPortee()
		void joueTour(int tour)
		void combat(Soldat soldat)
		void seDeplace(Position newPos)
	}
	class Monstre extends Soldat {
		- TypesM typeMonstreAlea
		+ Monstre()
		+ TypesM getTypeMonstre()
		# TypesH getTypeHeros()
		+ void combat(Soldat soldat)
		+ void combatDistance(Soldat soldat) 
	}
	class Obstacle implements Serializable{
		+ enum TypeObstacle 
	    - Position position
		- TypeObstacle TYPE
		+ Obstacle() 
		+ Position getPosition() 
		+ TypeObstacle getTypeObstacle() 
	}
	Position o-- Obstacle

	class PanneauJeu extends JPanel{
	    ~ Polygon hexagon 
	    ~ BasicStroke bs1 
	    ~ BasicStroke bs3 
	    ~ Point focusedHexagonLocation 
	    ~ int rows, columns, side
	    - Position mousePosition  
	    - BufferedImage imageHobbit, imageHumain, imageElf, imageNain
	    - BufferedImage imageTroll, imageGobelin, imageOrc
	    - BufferedImage imageRocher,imageEau,imageForet
	    - BufferedImage imageMap 
	    - JLabel statusLabel
	    - JButton menu
	    - JButton sauvgarder
	    - Carte carte
	    - {static} int number, row, column
	    - {static} Dimension dimension
	    - {static} Position posSoldat = null
	    - {static} Soldat soldat = null 
	    + PanneauJeu(final int rows, final int columns, final int side, int tabCases[], HashMap<Integer, Soldat> listeSoldats,    HashMap<Integer, Obstacle> listeObstacle)
	    + void paintComponent(final Graphics g)
	    + Polygon getHexagon(final int x, final int y)
	    + void paintAll(final Graphics2D g2d)
	    + BufferedImage getImageSoldat(Soldat soldat) 
	    + BufferedImage getImageObstacle(Obstacle obstacle)
	    + void paintPortee(final Graphics2D g2d, Position pos, Soldat soldat) 
	    
	}
	Carte"1" o-- PanneauJeu

	class Position implements IConfig,Serializable{
		- int x, y;
		- int number; 
		- int column, row;
		Dimension dimension;
		Position()
		Position(Dimension dimension)
		Position(Dimension dimension,int hauteur_min, int largeur_min, int largeur_max, int hauteur_max)
		Point getPoint() 
		void setPosition(Position p) 
		void setPosition(Point p) 
		void setPosition(int x, int y)
		+ int getRow() 
		+ int getColumn() 
		+ int getX() 
		+ int getY() 
		+ int getNumeroCase() 
		+ void setRow(int row) 
		+ void setColumn(int column) 
		+ void setNumeroCase(int number) 
		+ void setX(int x) 
		+ void setY(int y) 
		+ boolean estValide() 
		+ String toString() 
		+ boolean estVoisine(Position pos) 

	}
	class sauvegarde_wargame implements Serializable {
			~{static} long serialVersionUID = 1L 
		    - HashMap<Integer, Soldat> listeSoldats
		    - HashMap<Integer, Obstacle> listeObstacle
		    - int[] tabCases
		    + sauvegarde_wargame(HashMap<Integer, Soldat> listeSoldats, HashMap<Integer, Obstacle> listeObstacle, int[] tabCases) 
			+ HashMap<Integer, Soldat> getListeSoldats() 
			+ HashMap<Integer, Obstacle> getListeObstacle() 
			+ int[] getTabCases() 
	}
	abstract class Soldat implements ISoldat,Serializable {
		 	- int pointsDeVie
		    - int tour
		    - int portee
		    - Position position
		    # TypesM monstre
		    # TypesH heros
		    Soldat(TypesM typeMonstre)
		    Soldat(TypesH typeHeros)
		    + int getPoints() 
		    + int getTour() 
		    + int getPortee() 
		    + Position getPosition() 
		    + void setPoints(int pointDevie) 
		    # {abstract} TypesM getTypeMonstre()
		    # {abstract} TypesH getTypeHeros()
		    + void joueTour(int tour) 
		    + void seDeplace(Position nouvellePos) 
		    + void combat(Soldat soldat) 
		    + void combatDistance(Soldat soldat) 
	}
	
	Position o-- Soldat



}
@enduml

public class UML {

}
