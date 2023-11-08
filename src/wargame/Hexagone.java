package wargame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
/** Classe contenant la carte de jeu representee avec une tuile d'hexagones
 * subclass de Jpanel
 */
public class Hexagone extends JPanel {
    private final Polygon hexagon = new Polygon();
    private final BasicStroke bs1 = new BasicStroke(1);
    private final BasicStroke bs3 = new BasicStroke(3);
    private final Point focusedHexagonLocation = new Point();
    private final Dimension dimension;
    final int rows, columns, side;
    private Point mousePosition;
    
    
     
    // number n'est pas private car utilisée dans d'autres classes (classe PanneauJeu ?)
    int number;
    
    
    private final Image image1, image2;
    
    public Hexagone(final int rows, final int columns, final int side) {
        this.rows = rows;
        this.columns = columns;
        this.side = side;
        
        // On veut que ce panel contenant la carte du jeu couvre toute la fenetre (ou pas ?)
        this.setBounds(0, 0, IConfig.LARGEUR_FENETRE - IConfig.LARGEUR_FENETRE/5, IConfig.LONGUEUR_FENETRE - IConfig.LONGUEUR_FENETRE/8);
        this.setBackground(Color.GREEN);
        
        dimension = getHexagon(0, 0).getBounds().getSize();
        //System.out.println("dimension est " + dimension.getHeight() + " " + dimension.getWidth()) ;
        MouseInputAdapter mouseHandler = new MouseInputAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                mousePosition = e.getPoint();
                repaint();
            }
            @Override
            public void mousePressed(final MouseEvent e) {
                if (number != -1) {
                    System.out.println("Hexagon " + (number));
                }
            }
        };
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
        //HEROS
        image1 = getTestImage(Color.ORANGE);
        //MONSTRE
        image2 = getTestImage(Color.LIGHT_GRAY);
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.setStroke(bs1);
        number = -1;
        for (int row = 0; row < rows; row +=2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * dimension.width,
                        (int) (row * side * 1.5));
                if (mousePosition !=null && hexagon.contains(mousePosition)){
                    focusedHexagonLocation.x = column * dimension.width;
                    focusedHexagonLocation.y = (int) (row * side * 1.5);
                    number = row * columns + column;
                }
                g2d.draw(hexagon);
                
                //pour dessiner une image choisie dans une case de  polygone 
                /*g2d.drawImage(image1,(int)(hexagon.getBounds().x + side*0.5),
                        (int) (hexagon.getBounds().y + side * 0.5), this);*/
            }
        }
        for (int row = 1; row < rows; row += 2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * dimension.width + dimension.width / 2,
                        (int) (row * side * 1.5 + 0.5));
                if (mousePosition!= null && hexagon.contains(mousePosition)){
                    focusedHexagonLocation.x = column * dimension.width
                            + dimension.width / 2;
                    focusedHexagonLocation.y =(int) (row * side * 1.5 + 0.5);
                    number = row * columns + column;
                }
                g2d.draw(hexagon);
                
                //pour dessiner une image choisie dans une case de  polygone 
                /*g2d.drawImage(image2,(int)(hexagon.getBounds().x + side*0.5),
                        (int) (hexagon.getBounds().y + side * 0.5), this);*/
            }
        }
        if (number != -1) {
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
    
    private Image getTestImage(final Color color1) {
        BufferedImage img = new BufferedImage(side, side,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, side, side);
        g2d.setColor(color1);
        g2d.fillOval(0, 0, side - 9, side - 3);
        g2d.drawOval(0, 0, side - 9, side - 3);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(4, 9, 5, 7);
        g2d.setColor(color1);
        g2d.drawOval(4, 9, 5, 7);
        g2d.fillOval(6, 12, 3, 3);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(14, 9, 5, 7);
        g2d.setColor(color1);
        g2d.drawOval(14, 9, 5, 7);
        g2d.fillOval(16, 12, 3, 3);
        g2d.setColor(Color.RED);
        g2d.fillOval(8, 20, 6, 3);
        g2d.setColor(color1);
        g2d.drawOval(8, 20, 6, 3);
        return img;
    }
}

