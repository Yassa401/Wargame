package wargame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.event.MouseInputAdapter;

public class PanneauJeu extends JPanel{
    private final Polygon hexagon = new Polygon();
    private final BasicStroke bs1 = new BasicStroke(1);
    private final BasicStroke bs3 = new BasicStroke(3);
    private final Point focusedHexagonLocation = new Point();
    final int rows, columns, side;
    private Position mousePosition = new Position() ;
    BufferedImage imageHobbit, imageHumain, imageElf, imageNain; // images Heros
    BufferedImage imageTroll, imageGobelin, imageOrc;
    BufferedImage imageRocher,imageEau,imageForet;
    
    Carte carte ;
    
    // number n'est pas private car utilisée dans d'autres classes (classe PanneauJeu ?)
    static int number, row, column;
    // PanneauJeu.dimension aussi !
    static Dimension dimension;
    
    /**
     * Construit un Panel avec les PanneauJeu.dimensions spécifiées et remplie la carte avec les soldats donnés en paramètre
     * @param rows Hauteur de la carte en cases
     * @param columns Largeur de la carte en cases
     * @param side Taille d'un côté de l'hexagone en pixels
     * @param tabCases[] les indices des cases de la carte, si -1 vide, sinon indice de l'objet à mettre dans la carte
     * @param listeSoldats Liste de soldats à ajouter dans la carte
     */
    public PanneauJeu(final int rows, final int columns, final int side, int tabCases[], HashMap<Integer, Soldat> listeSoldats,    HashMap<Integer, Obstacle> listeObstacle) {
        this.rows = rows;
        this.columns = columns;
        this.side = side;
        this.carte = new Carte(tabCases, listeSoldats, listeObstacle);
        
        //charger une image d'un soldat (en cours de test)
        try { imageHobbit = ImageIO.read(new File("src/wargame/images/hobbit.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du hobbit !");}
        try { imageHumain = ImageIO.read(new File("src/wargame/images/chevalier.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du humain !");}
        try { imageElf = ImageIO.read(new File("src/wargame/images/elf.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageNain = ImageIO.read(new File("src/wargame/images/nain.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageOrc = ImageIO.read(new File("src/wargame/images/orc.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageGobelin = ImageIO.read(new File("src/wargame/images/gobelin.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageTroll = ImageIO.read(new File("src/wargame/images/troll.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageEau = ImageIO.read(new File("src/wargame/images/eau.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du obstacle !");}
        try { imageRocher = ImageIO.read(new File("src/wargame/images/rocher.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du obstacle !");}
        try { imageForet = ImageIO.read(new File("src/wargame/images/arbre.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du obstacle !");}
        
        // On veut que ce panel contenant la carte du jeu couvre toute la fenetre (ou pas ?)
        this.setBounds(0, 0, IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5, IConfig.LONGUEUR_FENETRE - IConfig.LONGUEUR_FENETRE/8);
        
        PanneauJeu.dimension = getHexagon(0, 0).getBounds().getSize();
        //System.out.println("PanneauJeu.dimension est " + PanneauJeu.dimension.getHeight() + " " + PanneauJeu.dimension.getWidth()) ;
        MouseInputAdapter mouseHandler = new MouseInputAdapter() {
        	Position pos = null, posSoldat = null;
        	Soldat soldat = null ;
        	int cleSoldat;
            @Override
            public void mouseMoved(final MouseEvent e) {
                mousePosition.setPosition(e.getPoint());
                repaint();
            }
            @Override
            public void mousePressed(final MouseEvent e) {
            	mousePosition.setPosition(e.getPoint());
            	if (PanneauJeu.number != -1) {
                    System.out.println("Hexagon " + (PanneauJeu.number));
                    	cleSoldat = carte.getTabCases()[PanneauJeu.number];
                    	pos = new Position(); pos.setPosition(e.getPoint());
                    	soldat = carte.trouveHeros(pos); 
                    	if( soldat != null) { // soit c'est un obstacle ou monstre
                    		posSoldat = soldat.getPosition();
                    		carte.getTabCases()[posSoldat.getNumeroCase()] = -1 ;
                    		
                    	}
                }
                
            }
            @Override
            public void mouseDragged(final MouseEvent e) {
            	mousePosition.setPosition(e.getPoint());
            	if (soldat != null && pos != null) {
            		pos.setPosition(e.getPoint());
            		//carte.deplaceSoldat(pos, posSoldat, soldat); /*on deplace le soldat à la position finale lorsque la souris released donc pas besoin*/
            	}
            	repaint();
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
            	mousePosition.setPosition(e.getPoint());
            	if(soldat != null && carte.estPositionVide(pos))  {
            	// si soldat deplace alors on remplie la nouvelle position dans tableCases avec la cle soldat
            		if(carte.deplaceSoldat(pos, posSoldat, soldat)) { 
            			carte.getTabCases()[pos.getNumeroCase()] = cleSoldat;
            			System.out.println("nouvelle position soldat " + soldat.getPosition().getNumeroCase());
            		}
            		else { // si soldat non deplace on reemplit l'ancienne case dans tabCases avec la cle soldat
            			carte.getTabCases()[posSoldat.getNumeroCase()] = cleSoldat;
            		}
            	}
            	else if(posSoldat != null){ //  si case remplie on reemplit l'ancienne case dans tabCases avec la cle soldat
            		carte.getTabCases()[posSoldat.getNumeroCase()] = cleSoldat;
            	}
            	soldat = null ; pos = null ; posSoldat = null ;
            	
            	repaint();
            	
            }
            
        };
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.setStroke(bs1);
        PanneauJeu.number = -1;
        setBackground(IConfig.COULEUR_VIDE);
        // Dessine tous les hexagones de la carte
        for (int row = 0; row < rows; row +=2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * PanneauJeu.dimension.width,
                        (int) (row * side * 1.5));
                if (mousePosition !=null && hexagon.contains(mousePosition.getPoint())){
                    focusedHexagonLocation.x = column * PanneauJeu.dimension.width;
                    focusedHexagonLocation.y = (int) (row * side * 1.5);
                    PanneauJeu.row = row ; PanneauJeu.column = column ;
                    PanneauJeu.number = row * columns + column;
                }
                g2d.draw(hexagon);
                
            }
        }
        for (int row = 1; row < rows; row += 2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * PanneauJeu.dimension.width + PanneauJeu.dimension.width / 2,
                        (int) (row * side * 1.5 + 0.5));
                if (mousePosition!= null && hexagon.contains(mousePosition.getPoint())){
                    focusedHexagonLocation.x = column * PanneauJeu.dimension.width
                            + PanneauJeu.dimension.width / 2;
                    focusedHexagonLocation.y =(int) (row * side * 1.5 + 0.5);
                    PanneauJeu.row = row ; PanneauJeu.column = column ;
                    PanneauJeu.number = row * columns + column;
                }
                g2d.draw(hexagon);
                
            }
        }
        // Dessine les soldats de la carte
        paintAll(g2d);
        
        if (PanneauJeu.number != -1) {
            g2d.setColor(Color.red);
            g2d.setStroke(bs3);
            Polygon focusedHexagon = getHexagon(focusedHexagonLocation.x,
                    focusedHexagonLocation.y);
            g2d.draw(focusedHexagon);
        }
    }
    
    public Polygon getHexagon(final int x, final int y) {
        hexagon.reset();
        int h = side / 2;
        int w = (int) (side * (Math.sqrt(3) / 2));
        hexagon.addPoint(x, y + h);
        hexagon.addPoint(x + w, y);
        hexagon.addPoint(x + 2 * w, y + h);
        hexagon.addPoint(x + 2 * w, y + (int) (1.5 * side));
        hexagon.addPoint(x + w, y + 2 * side);
        hexagon.addPoint(x, y + (int) (1.5 * side));
        return hexagon;
    }
    
    public void paintAll(final Graphics2D g2d) {
    	for(int i=0; i < IConfig.LARGEUR_CARTE*IConfig.HAUTEUR_CARTE; i++) {
    		if(carte.getTabCases()[i] != -1) {
    			if (carte.getTabCases()[i]<IConfig.NB_HEROS+IConfig.NB_MONSTRES && carte.getListeSoldats().containsKey(carte.getTabCases()[i])) {
    				if(carte.getListeSoldats().get(carte.tabCases[i]).getPosition().getRow()%2 == 1) {
    			g2d.drawImage(getImageSoldat(carte.getListeSoldats().get(carte.tabCases[i])),
    					carte.getListeSoldats().get(carte.getTabCases()[i]).getPosition().getX() + (int)(side*1.13),
    					carte.getListeSoldats().get(carte.getTabCases()[i]).getPosition().getY() + (int)(side*0.47),
    					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
    			}else if(carte.getListeSoldats().get(carte.getTabCases()[i]).getPosition().getRow()%2 == 0) {
        			g2d.drawImage(getImageSoldat(carte.getListeSoldats().get(carte.getTabCases()[i])),
        					carte.getListeSoldats().get(carte.tabCases[i]).getPosition().getX() + (int)(side*0.3),
        					carte.getListeSoldats().get(carte.getTabCases()[i]).getPosition().getY() + (int)(side*0.47),
        					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
        			}
    			}
    			else if(carte.getListeObstacle().containsKey(carte.getTabCases()[i])){
    				if(carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getRow()%2 == 1) {
	    			g2d.drawImage(getImageObstacle(carte.getListeObstacle().get(carte.getTabCases()[i])),
	    					carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getX() + (int)(side*1.13),
	    					carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getY() + (int)(side*0.47),
	    					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
	    			}else if(carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getRow()%2 == 0) {
	        			g2d.drawImage(getImageObstacle(carte.getListeObstacle().get(carte.getTabCases()[i])),
	        					carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getX() + (int)(side*0.3),
	        					carte.getListeObstacle().get(carte.getTabCases()[i]).getPosition().getY() + (int)(side*0.47),
	        					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
	        			}
    			}
    			
    		}
    	}
    }
    
    public BufferedImage getImageSoldat(Soldat soldat) {
    	if( soldat instanceof Monstre) {
    		switch(soldat.getTypeMonstre()) {
    		case ORC : return this.imageOrc;
    		case GOBELIN : return this.imageGobelin;
    		default : return this.imageTroll;
    		}
    	}
    	else {
    		switch(soldat.getTypeHeros()) {
    		case HUMAIN: return this.imageHumain;
    		case NAIN: return this.imageNain;
    		case ELF: return this.imageElf;
    		default: return this.imageHobbit;
    		}
    	}
    } 
    public BufferedImage getImageObstacle(Obstacle obstacle) {
    	switch(obstacle.getTypeObstacle()) {
		case FORET : return this.imageForet;
		case ROCHER : return this.imageRocher;
		default : return this.imageEau;
		}
    } 

}
