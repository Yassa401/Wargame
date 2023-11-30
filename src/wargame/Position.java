package wargame;

import java.awt.* ;

public class Position implements IConfig{
	
	private int x, y;
	private int number; // numero de la case
	private int column, row;
	Dimension dimension;
	Position() {}
	
	Position(Dimension dimension) {
		this.dimension = dimension;
		this.row = (int) (Math.random()*IConfig.HAUTEUR_CARTE);
		this.column = (int) (Math.random()*IConfig.LARGEUR_CARTE);
		this.setX(column * dimension.width);
		this.setY((int)(row * IConfig.NB_PIX_CASE * 1.5));
		this.number = row * IConfig.LARGEUR_CARTE + column;
		System.out.println("Case soldat " + this.number);
	}
	
	Position(Dimension dimension,int hauteur_min, int largeur_min, int largeur_max, int hauteur_max){
		this.row = (int) (Math.random()*(hauteur_max-hauteur_min)) + hauteur_min;
		this.column = (int) (Math.random()*(largeur_max-largeur_min)) + largeur_min;
		this.setX(column * dimension.width);
		this.setY((int)(row * IConfig.NB_PIX_CASE * 1.5));
		this.number = row * IConfig.LARGEUR_CARTE + column;
		System.out.println("Case soldat " + this.number);
		
	}
	Point getPoint() {
		Point p = new Point(this.getX(), this.getY());
		return p;
	}
	
	void setPosition(Position p) {
		this.setX(p.getColumn() * this.dimension.width);
		this.setY((int)(p.getRow() * IConfig.NB_PIX_CASE * 1.5));
		this.setRow(p.getRow());
		this.setColumn(p.getColumn());
		this.setNumeroCase(p.getNumeroCase());
	}
	
	void setPosition(Point p) { 
		this.setX((int)p.getX()); this.setY((int)p.getY());
		this.setColumn((int)(p.getX()) / PanneauJeu.dimension.width );
		this.setRow((int)(p.getY() / ((int)IConfig.NB_PIX_CASE * 1.5)));
		this.number = row * IConfig.LARGEUR_CARTE + column;
	}
	
	void setPosition(int x, int y) { this.setX(x); this.setY(y); }
	
	public int getRow() { return this.row; }
	
	public int getColumn() { return this.column; }
	
	public int getX() {
		/*if(this.getRow()%2 == 0 )
			this.setX(this.getColumn() * PanneauJeu.dimension.width);
		else
			this.setX(this.getColumn() * PanneauJeu.dimension.width + PanneauJeu.dimension.width/2);
		*/
		//this.setX(this.getColumn() * PanneauJeu.dimension.width);
		return this.x;
	}

	public int getY() { 
		/*
		if(this.getRow()%2 == 0)
			this.setY((int)(this.getRow() * IConfig.NB_PIX_CASE * 1.5)); 
		else
			this.setY((int)(this.getRow() * IConfig.NB_PIX_CASE * 1.5 + 0.5 ));
		*/
		//this.setY((int)(this.getRow() * IConfig.NB_PIX_CASE * 1.5));
		return this.y; 
	}
	
	public int getNumeroCase() { return this.number; }
	
	public void setRow(int row) { this.row = row; }
	
	public void setColumn(int column) { this.column = column; }

	public void setNumeroCase(int number) { this.number = number; }

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
