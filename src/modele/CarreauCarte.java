package modele;

import java.util.ArrayList;
import java.util.List;

public class CarreauCarte {
	
	private final Coordonnes c;
	private PartieBateau sp = null;
	
	//Constructeur
	public CarreauCarte(int x, int y){
		this.c = new Coordonnes(x,y);
		this.c.setCarreauCarte(this);
	}
	
	public CarreauCarte(Coordonnes c){
		this.c = c;
		this.c.setCarreauCarte(this);
	}
	
	//Accesseurs
	public Coordonnes getCoordonnes() {
		return c;
	}
	
	
	//Methodes 
	/*This method strikes the ship's part contained in the SeaSquare
	 * if the SeaSquare is empty or the part is already hit it returns false*/
	public boolean attaquer(){
		if (this.sp == null || this.sp.estTouche()) {
			return false;
		} else {
			sp.attaquer();
			return true;
		}
	}
	
	/*returns true if this SquareSea is linked to a ship's part*/
	public boolean contientBateau(){
		return this.sp != null;
	}
	
	/*Links this SeaSquare to its Coordinates*/
	public void lierCoordonnes(){
		this.c.setCarreauCarte(this);
	}
	
	/*Links a ship's part to this SeaSquare*/
	public void lierPartieBateau(PartieBateau sp) throws CarreauUtiliseException {
		if (this.sp == null) {
			this.sp = sp;
		} else {
			throw new CarreauUtiliseException();
		}
	}
	
	public boolean equals(Coordonnes c) {
		return this.c.equals(c);
	}
	
	public String toString() {
		if (this.sp == null) {
			return " ";
		} else if (this.sp.estTouche()) {
			return "X";
		} else {
			return "S";
		}
	}
	
/*Checks if a list of SeaSquare is aligned*/
	public static final boolean aligne(List<CarreauCarte> lsq) {
		List<Coordonnes> lc = new ArrayList<Coordonnes>(lsq.size());
		for (CarreauCarte sq : lsq) {
			lc.add(sq.getCoordonnes());
		}
		return Coordonnes.aligne(lc);
	}
}
