package rmi.Serveur;

import java.io.Serializable;
import java.util.List;

public class Coordonnees implements Serializable {

	private static final long serialVersionUID = 7956510708633831885L;
	private final int x;
	private final int y;
	private transient CarreauCarte cc;
	
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
		cc = null;
	}
	
	private static boolean aligne(Coordonnees c1, Coordonnees c2, Coordonnees c3) {
		int dirX1, dirX2, dirY1, dirY2;
		dirX1 = c2.getX() - c1.getX();
		dirX2 = c3.getX() - c1.getX();
		dirY1 = c2.getY() - c1.getY();
		dirY2 = c3.getY() - c1.getY();
		return (dirX1 * dirY2 - dirX2 * dirY1) == 0;
	}
	
	public static boolean aligne(List<Coordonnees> lc) {
		int len = lc.size() - 2, i = 0;
		boolean isAligned = true;
		while (isAligned && i < len) {
			isAligned = aligne(lc.get(i), lc.get(i+1), lc.get(i+2));
			i++;
		}
		return isAligned;
	}
	
	public int getX() { return this.x; }
	
	public int getY() { return this.y; }
	
	public CarreauCarte getCarreauCarte() { return this.cc; }
	
	public void setCarreauCarte(CarreauCarte cc) { this.cc = cc; }
	
	public boolean equals(Object o) {
		if (o instanceof Coordonnees) return ((ICoordonnees) o).getX() == x && ((Coordonnees) o).getY() == y;
		if (o instanceof CarreauCarte) return ((CarreauCarte) o).getCoordonnees().getX() == x && ((CarreauCarte) o).getCoordonnees().getY() == y;
		return false;
	}
	
}
