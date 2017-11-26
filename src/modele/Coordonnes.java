package modele;

import java.util.List;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

public class Coordonnes {
	//Attributs
	
	private int x;
	private int y;
	private CarreauCarte sq = null;
	
	//constructeurs
	
	public Coordonnes(int x0, int y0){
		this.x = x0;
		this.y = y0;
	}
	
	//accesseurs
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	
	public CarreauCarte getCarreauCarte(){
		return this.sq;
	}
	
	public void setX(int absc){
		this.x = absc;
	}
	
	public void setY(int ord){
		this.y = ord;
	}
	
	public void setCarreauCarte(CarreauCarte sq0){
		this.sq = sq0;
	}
	
	//Methodes
	
	/*this method returns true if the two coordinates (x,y) are the same*/
	public boolean equals(Coordonnes pos){
		return pos.getX() == this.x && pos.getY() == this.y;
		}
	
	/*this method returns true if the coordinates (x,y) from the SeaSquare
	 * are the same than the ones defined in this object*/
	public boolean equals(CarreauCarte cc){
		return cc.getCoordonnes().getX() == this.x && cc.getCoordonnes().getY() == this.y;
		}
	
	/*checks if 3 points are aligned*/
	private static boolean aligne(Coordonnes c1, Coordonnes c2, Coordonnes c3) {
		int dirX1, dirX2, dirY1, dirY2;
		dirX1 = c2.getX() - c1.getX();
		dirX2 = c3.getX() - c1.getX();
		if (dirX1 == dirX2) {
			dirY1 = c2.getY() - c1.getY();
			dirY2 = c3.getY() - c1.getY();
			return (dirX1 * dirY2 - dirX2 * dirY1) == 0;
		}
		
		return false;
	}
	
	/*Checks if a list of points is aligned*/
	public static final boolean aligne(List<Coordonnes> lc) {
		int len = lc.size() - 1, i = 0;
		boolean isAligned = true;
		while (isAligned && i < len) {
			isAligned = aligne(lc.get(i), lc.get(i+1), lc.get(i+2));
		}
		return isAligned;
	}
	
	}
