package wargame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

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
    BufferedImage imageMap; // arriere plan de la carte de jeu
    JLabel statusLabel;

    Carte carte ;
    
    
    // number n'est pas private car utilisée dans d'autres classes (classe PanneauJeu ?)
    static int number, row, column;
    // PanneauJeu.dimension aussi !
    static Dimension dimension;
    static Position posSoldat = null;
	static Soldat soldat = null ;
	static int tour = 0 ; // tour heros = 0 , tour monstre = 1
    
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
        try { imageHumain = ImageIO.read(new File("src/wargame/images/chevalier.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du humain !");}
        try { imageElf = ImageIO.read(new File("src/wargame/images/elf.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du soldat !");}
        try { imageNain = ImageIO.read(new File("src/wargame/images/nain.png"));}
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
        try { imageForet = ImageIO.read(new File("src/wargame/images/arbre.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du obstacle !");}
        try { imageMap = ImageIO.read(new File("src/wargame/images/mipui2.png"));}
        catch(Exception e) { System.out.println("Erreur pour charger l'image du obstacle !");}
        
        setLayout(null);

        // On veut que ce panel contenant la carte du jeu couvre toute la fenetre (ou pas ?)
        this.setBounds(0, 0, (int)(IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/4.5) , IConfig.LONGUEUR_FENETRE );
        
        statusLabel = new JLabel(" ");
	    statusLabel.setBounds(200, (int)(IConfig.LONGUEUR_FENETRE - IConfig.LONGUEUR_FENETRE/15.5),  400, 35);
	    statusLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    statusLabel.setForeground(Color.WHITE);
        statusLabel.setText("status Heros");
        

        this.add(statusLabel, BorderLayout.SOUTH);
        PanneauJeu.dimension = getHexagon(0, 0).getBounds().getSize();
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
            		if(SwingUtilities.isRightMouseButton(e)) {
            			System.out.println("clic droit");
	                	if(listeSoldats.get(tabCases[PanneauJeu.number]) != null) {
	                		statusLabel.setText(" Point : " + listeSoldats.get(tabCases[PanneauJeu.number]).getPoints() + ", Portee : " +listeSoldats.get(tabCases[PanneauJeu.number]).getPortee()+", Tir : "+listeSoldats.get(tabCases[PanneauJeu.number]).getTir()+", puissance : "+listeSoldats.get(tabCases[PanneauJeu.number]).getPuissance());
	                	}
            		}
            		else {
            			System.out.println("clic gauche");
	                	pos = new Position(); pos.setPosition(e.getPoint());
	                	soldat = carte.trouveHeros(pos);
	                	if( soldat != null) { // heros trouvé à la position du clique de la souris                 		
	                		posSoldat = soldat.getPosition();
	                		PanneauJeu.soldat = soldat;
	                		PanneauJeu.posSoldat = posSoldat;
	                	}
            		}
	                	
                }
            	
            }
            @Override
            public void mouseDragged(final MouseEvent e) {
            	mousePosition.setPosition(e.getPoint());
            	if (soldat != null && pos != null) {
            		pos.setPosition(e.getPoint());
            	}
            	repaint();
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
            	mousePosition.setPosition(e.getPoint());            	
            	
            		if(soldat != null && PanneauJeu.tour == 0)  {
                		// si soldat deplace alors on remplie la nouvelle position dans tableCases avec la cle soldat
                		if(carte.actionHeros(pos, posSoldat, soldat)) { // si deplacement possible ou attaque effectue
                			System.out.println("nouvelle position soldat " + soldat.getPosition().getNumeroCase());
                			if(MonstreTousMorts()) {
                        		JOptionPane.showMessageDialog(null,"Bravoo les HEROS!");
                        		PanneauJeu.tour = -1 ;
                			}
                			else {
                				PanneauJeu.tour = 1 ; // tour joué et monstre(s) encore existant(s)
                			}
                		}
                	}
                	soldat = null ; pos = null ; posSoldat = null ;
                	PanneauJeu.soldat = null ; PanneauJeu.posSoldat = null ;
                	repaint();
                	
                	if(PanneauJeu.tour == 1) {
                		carte.actionMonstre();
                		PanneauJeu.tour = 0 ;
                		
                		if(HerosTousMorts()) {
                			JOptionPane.showMessageDialog(null,"Bravoo les MONSTRES!");
                			PanneauJeu.tour = -1 ;
                		}
                		else {
                			PanneauJeu.tour = 0 ; // tour joué et Héro(s) encore existant(s)
                		}
                	}
                	
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
        setBackground(new Color(49, 74, 51));
        // Dessine arriere plan
        g2d.drawImage(imageMap, 0, 0, 
        		IConfig.LARGEUR_FENETRE-IConfig.LARGEUR_FENETRE/4 + IConfig.NB_PIX_CASE, IConfig.LONGUEUR_FENETRE-IConfig.LONGUEUR_FENETRE/14,
        		null);
        
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
        //si Soldat selectionné, dessine les cases à sa portée
        if(PanneauJeu.soldat != null) {
        	paintPortee(g2d, PanneauJeu.posSoldat, PanneauJeu.soldat);
        }
        
        if (PanneauJeu.number != -1) {
            g2d.setColor(Color.red);
            g2d.setStroke(bs3);
            Polygon focusedHexagon = getHexagon(focusedHexagonLocation.x,
                    focusedHexagonLocation.y);
            g2d.draw(focusedHexagon);
        }
    }
    
    /**
     * Renvoie une case hexagone avec les bonnes dimensions
     * @param x : coordonnée x de l'hexagone à dessiner
     * @param y : coordonnée y de l'hexagone à dessiner
     * @return Polygon : renvoie l'objet polygone avec ces points bien définie, dans ce cas c'est un hexagone
     */
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
    
    /**
     * Redessine tous les soldats et les obstacles en choisissant les bonnes images de chaque objet
     * Utilise les méthodes getImageSoldat() et getImageObstacle() pour renvoyer les images des objets 
     * @param g2d : graphics de la carte
     */
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
    
    /**
     * Renvoie l'image correspondante au soldat (soit monstre ou heros) 
     * @param soldat : soldat qui est un objet heros ou monstre
     * @return BufferedImage : image chargée du soldat
     */
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
    
    /**
     * Renvoie l'image correspondante à l'obstacle
     * @param obstacle : obstacle qui est un objet Obstacle
     * @return BufferedImage : image chargée de l'obstacle
     */
    public BufferedImage getImageObstacle(Obstacle obstacle) {
    	switch(obstacle.getTypeObstacle()) {
		case FORET : return this.imageForet;
		case ROCHER : return this.imageRocher;
		default : return this.imageEau;
		}
    }
    
    /**
     * Dessine Les cases à la portée du soldat pour effectuer une attaque à distance
     * @param soldat
     */
    public void paintPortee(final Graphics2D g2d, Position pos, Soldat soldat) {
    	int row = pos.getRow(), column = pos.getColumn();
    	int portee = soldat.getPortee();
    	int x = 0, y = 0;
    	Polygon focusedHexagon;
    	
    	g2d.setColor(Color.cyan);
        g2d.setStroke(bs3);
        
    	if(row%2 == 1 ) {
    		x = column * PanneauJeu.dimension.width + PanneauJeu.dimension.width / 2;
        	y = (int) (row * side * 1.5 + 0.5);
        	
        	
    		for(int i = 1 ; i <= soldat.getPortee(); i++) { // à droite
    			if ((column+i) >= IConfig.LARGEUR_CARTE){
    				continue;
    			}
    			x = (column+i) * PanneauJeu.dimension.width + PanneauJeu.dimension.width / 2;
    			focusedHexagon = getHexagon(x,y);
    	        g2d.draw(focusedHexagon);
    		}
    		for(int i = 1 ; i <= soldat.getPortee(); i++) { // à gauche
    			if ((column-i) < 0) {
            		continue;
    			}
    			x = (column-i) * PanneauJeu.dimension.width + PanneauJeu.dimension.width / 2;
    			focusedHexagon = getHexagon(x,y);
    	        g2d.draw(focusedHexagon);
    		}
    		for (int j=1; j <= soldat.getPortee(); j++) {
	    		if((row+j) < IConfig.HAUTEUR_CARTE) {
	    			y = (int) ((row+j) * side * 1.5); // en bas
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en bas à droite
	    				if ((column+i) >= IConfig.LARGEUR_CARTE) {
	            			continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column+i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column+i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    				
	    			}
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en bas à gauche
	    				if ((column-i) < 0) {
	            			continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column-i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column-i+1) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    			}
	    		}
	    		
	    		if((row-j) >= 0) { 
	    			y = (int) ((row-j) * side * 1.5); // en haut
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en haut à droite
	    				if ((column+i) >= IConfig.LARGEUR_CARTE) {
	            			continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column+i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column+i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    				
	    			}
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en haut à gauche
	    				if ((column-i) < 0) {
	            			continue;
	            			}
	    				if((row+j)%2 == 1) {
	    					x = (column-i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column-i+1) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    				
	    			}
	    		}
    		}
    		
    	}
    	else if(row%2 == 0) {
    		x = column * PanneauJeu.dimension.width;
            y = (int) (row * side * 1.5);
            for(int i = 1 ; i <= soldat.getPortee(); i++) { // à droite
            	if ((column+i) >= IConfig.LARGEUR_CARTE) {
            		continue;
            	}
            	
    			x = (column+i) * PanneauJeu.dimension.width;
    			focusedHexagon = getHexagon(x,y);
    	        g2d.draw(focusedHexagon);
    		}
    		for(int i = 1 ; i <= soldat.getPortee(); i++) { // à gauche
    			if ((column-i) < 0) {
            		continue;
    			}
    			
    			x = (column-i) * PanneauJeu.dimension.width;
    			focusedHexagon = getHexagon(x,y);
    	        g2d.draw(focusedHexagon);
    		}
    		
    		for (int j=1; j <= soldat.getPortee(); j++) {
	
	    		if((row+j) < IConfig.HAUTEUR_CARTE) {
	    			y = (int) ((row+j) * side * 1.5); // en bas
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en bas à droite
	    				if ((column+i) >= IConfig.LARGEUR_CARTE) {
	            			continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column+i-1) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column+i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    			}
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en bas à gauche
	    				if ((column-i) < 0) {
	            			continue;
	    				}
	    			
	    				if((row+j)%2 == 1) {
	    					x = (column-i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column-i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    			}
	    		}
	    		
	    		if((row-j) >= 0) {
	    			y = (int) ((row-j) * side * 1.5); // en haut
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en haut à droite
	    				if ((column+i) >= IConfig.LARGEUR_CARTE) {
	    					continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column+i-1) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column+i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    			}
	    			for(int i = 0 ; i <= soldat.getPortee()-j+1; i++) { // en haut à gauche
	    				if ((column-i) < 0) {
	    					continue;
	    				}
	    				if((row+j)%2 == 1) {
	    					x = (column-i) * PanneauJeu.dimension.width+ PanneauJeu.dimension.width / 2;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}else {
	    					x = (column-i) * PanneauJeu.dimension.width;
		    				focusedHexagon = getHexagon(x,y);
		    	        	g2d.draw(focusedHexagon);
	    				}
	    				
	    			}
	    		}
    		}
    	}
    }
    
    public boolean HerosTousMorts() {
    	for (Entry<Integer, Soldat> entry : carte.listeSoldats.entrySet()) {
    	    Integer key = entry.getKey();
    	    Soldat value = entry.getValue();
    	    if(value instanceof Heros) {
    	    	return false;
    	    }
    	}
    	return true ;
    }
    
    public boolean MonstreTousMorts() {
    	for (Entry<Integer, Soldat> entry : carte.listeSoldats.entrySet()) {
    	    Integer key = entry.getKey();
    	    Soldat value = entry.getValue();
    	    if(value instanceof Monstre) {
    	    	return false;
    	    }
    	}
    	return true ;
    	
    }
}