package wargame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.event.MouseInputAdapter;

public class PanneauJeu extends JPanel{
    private final Polygon hexagon = new Polygon();
    private final BasicStroke bs1 = new BasicStroke(1);
    private final BasicStroke bs3 = new BasicStroke(3);
    private final Point focusedHexagonLocation = new Point();
    private final Dimension dimension;
    final int rows, columns, side;
    private Position mousePosition = new Position() ;
    
    // number n'est pas private car utilisée dans d'autres classes (classe PanneauJeu ?)
    int number;
        
    public PanneauJeu(final int rows, final int columns, final int side) {
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
                mousePosition.getPosition(e.getPoint());
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
                if (mousePosition !=null && hexagon.contains(mousePosition.getPoint())){
                    focusedHexagonLocation.x = column * dimension.width;
                    focusedHexagonLocation.y = (int) (row * side * 1.5);
                    number = row * columns + column;
                }
                g2d.draw(hexagon);
                
            }
        }
        for (int row = 1; row < rows; row += 2) {
            for (int column = 0; column < columns; column++) {
                getHexagon(column * dimension.width + dimension.width / 2,
                        (int) (row * side * 1.5 + 0.5));
                if (mousePosition!= null && hexagon.contains(mousePosition.getPoint())){
                    focusedHexagonLocation.x = column * dimension.width
                            + dimension.width / 2;
                    focusedHexagonLocation.y =(int) (row * side * 1.5 + 0.5);
                    number = row * columns + column;
                }
                g2d.draw(hexagon);
                
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

}
