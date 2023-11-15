package wargame;

import java.awt.* ;

public class Position implements IConfig{
	
	private int x, y;
	private int number; // numero de la case
	private int column, row;
	Position() {}
	
	Position(Dimension dimension) {
		this.row = (int) (Math.random()*IConfig.HAUTEUR_CARTE);
		this.column = (int) (Math.random()*IConfig.LARGEUR_CARTE);
		this.setX(column * dimension.width);
		this.setY((int)(row * IConfig.NB_PIX_CASE * 1.5));
		this.number = row * IConfig.LARGEUR_CARTE + column;
		System.out.println("Case soldat " + this.number);
		
	}
	Point getPoint() {
		Point p = new Point(this.getX(), this.getY());
		return p;
	}
	
	void getPosition(Point p) { this.setX((int)p.getX()); this.setY((int)p.getY());}
	
	void getPosition(int x, int y) { this.setX(x); this.setY(y); }
	
	public int getRow() { return this.row; }
	
	public int getColumn() { return this.column; }
	
	public int getX() { return this.x; }

	public int getY() { return this.y; }
	
	public int getNumeroCase() { return this.number; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public boolean estValide() {
		if (x<0 || x>=LARGEUR_CARTE || y<0 || y>=HAUTEUR_CARTE) return false;
		else return true;
	}
	
	public String toString() { return "("+x+","+y+")"; }
	
	public boolean estVoisine(Position pos) {
		return ((Math.abs(x-pos.x)<=1) && (Math.abs(y-pos.y)<=1));
	}

}
