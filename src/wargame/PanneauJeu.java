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
    HashMap<Integer, Soldat> listeSoldats;
	int tabCases[];
    BufferedImage imageHobbit, imageHumain, imageElf, imageNain; // images Heros
    BufferedImage imageTroll, imageGobelin, imageOrc;
    BufferedImage imageDragon; // image Dragon
    
    // number n'est pas private car utilisée dans d'autres classes (classe PanneauJeu ?)
    static int number;
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
    public PanneauJeu(final int rows, final int columns, final int side, int tabCases[], HashMap<Integer, Soldat> listeSoldats) {
        this.rows = rows;
        this.columns = columns;
        this.side = side;
        this.tabCases = tabCases;
        this.listeSoldats = listeSoldats;
        
        
        //charger une image d'un soldat (en cours de test)
        try { imageHobbit = ImageIO.read(new File("src/wargame/hobbit.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du hobbit !");}
        try { imageHumain = ImageIO.read(new File("src/wargame/chevalier.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du humain !");}
        try { imageElf = ImageIO.read(new File("src/wargame/elf.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageNain = ImageIO.read(new File("src/wargame/nain.jpg"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageOrc = ImageIO.read(new File("src/wargame/orc.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageGobelin = ImageIO.read(new File("src/wargame/gobelin.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageTroll = ImageIO.read(new File("src/wargame/troll.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try {imageDragon = ImageIO.read(new File("src/wargame/dragon.png"));}
        catch(Exception e) {System.out.println("Erreur pour charger l'image du soldat !");}
        
        
        // On veut que ce panel contenant la carte du jeu couvre toute la fenetre (ou pas ?)
        this.setBounds(0, 0, IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5, IConfig.LONGUEUR_FENETRE - IConfig.LONGUEUR_FENETRE/8);
        
        PanneauJeu.dimension = getHexagon(0, 0).getBounds().getSize();
        //System.out.println("PanneauJeu.dimension est " + PanneauJeu.dimension.getHeight() + " " + PanneauJeu.dimension.getWidth()) ;
        MouseInputAdapter mouseHandler = new MouseInputAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                mousePosition.getPosition(e.getPoint());
                repaint();
            }
            @Override
            public void mousePressed(final MouseEvent e) {
                if (PanneauJeu.number != -1) {
                    System.out.println("Hexagon " + (PanneauJeu.number));
                }
            }
        };
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
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
    
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.setStroke(bs1);
        PanneauJeu.number = -1;
        
        // Dessine tous les hexagones de la carte
        for (int row = 0; row < rows; row +=2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * PanneauJeu.dimension.width,
                        (int) (row * side * 1.5));
                if (mousePosition !=null && hexagon.contains(mousePosition.getPoint())){
                    focusedHexagonLocation.x = column * PanneauJeu.dimension.width;
                    focusedHexagonLocation.y = (int) (row * side * 1.5);
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
                    PanneauJeu.number = row * columns + column;
                }
                g2d.draw(hexagon);
                
            }
        }
        // Dessine les soldats de la carte
        paintSoldat(g2d);
        
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
    
    public void paintSoldat(final Graphics2D g2d) {
    	for(int i=0; i < IConfig.LARGEUR_CARTE*IConfig.HAUTEUR_CARTE; i++) {
    		if(this.tabCases[i] != -1) {
    			//System.out.println(" soldat is instance of Monstre " + (this.getListeSoldats().get(this.tabCases[i]) instanceof Monstre)) ;
    			//System.out.println(" soldat est de type specifique " + (this.getListeSoldats().get(this.tabCases[i]).getTypeMonstre())) ;
    			if(this.getListeSoldats().get(this.tabCases[i]).getPosition().getRow()%2 == 1) {
    			g2d.drawImage(getImage(this.getListeSoldats().get(this.tabCases[i])),
    					this.getListeSoldats().get(this.tabCases[i]).getPosition().getX() + (int)(side*1.13),
    					this.getListeSoldats().get(this.tabCases[i]).getPosition().getY() + (int)(side*0.47),
    					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
    			}else if(this.getListeSoldats().get(this.tabCases[i]).getPosition().getRow()%2 == 0) {
        			g2d.drawImage(getImage(this.getListeSoldats().get(this.tabCases[i])),
        					this.getListeSoldats().get(this.tabCases[i]).getPosition().getX() + (int)(side*0.3),
        					this.getListeSoldats().get(this.tabCases[i]).getPosition().getY() + (int)(side*0.47),
        					IConfig.SIZE_CHARACTER, IConfig.SIZE_CHARACTER, this);
        			}
    		}
    	}
    }
    
    public BufferedImage getImage(Soldat soldat) {
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

}
