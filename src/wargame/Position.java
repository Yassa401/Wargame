package wargame;

import java.awt.* ;

public class Position implements IConfig{
	
	private int x, y;
	
	Position() {
	}
	
	Point getPoint() {
		Point p = new Point(this.getX(), this.getY());
		return p;
	}
	
	void getPosition(Point p) { this.setX((int)p.getX()); this.setY((int)p.getY());}
	
	void getPosition(int x, int y) { this.setX(x); this.setY(y); }
	
	public int getX() { return this.x; }

	public int getY() { return this.y; }
	
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
