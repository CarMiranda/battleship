package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

public class Coordonnes {
	//Attributs
	
	private int x;
	private int y;
	private PartieBateau pieceBateau;
	
	//constructeurs
	
	public Coordonnes(int x0, int y0, PartieBateau p){
		this.x = x0;
		this.y = y0;
		this.pieceBateau = p;
	}
	
	//accesseurs
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	
	public PartieBateau getPartieBateau(){
		return this.pieceBateau;
	}
	
	public void setX(int absc){
		this.x = absc;
	}
	
	public void setY(int ord){
		this.y = ord;
	}
	
	public void setPartieBateau(PartieBateau piece){
		this.pieceBateau = piece;
	}
}
