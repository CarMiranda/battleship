package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

/**
 * Cette classe représente une paire de coordonnées.
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos - Refactoring

import java.util.List;

public class Coordonnees {
	
	private final int x;
	private final int y;
	private CarreauCarte cc;
	
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
		cc = null;
	}
	
	private static boolean aligne(Coordonnees c1, Coordonnees c2, Coordonnees c3) {
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
	
	public static final boolean aligne(List<Coordonnees> lc) {
		int len = lc.size() - 1, i = 0;
		boolean isAligned = true;
		while (isAligned && i < len) {
			isAligned = aligne(lc.get(i), lc.get(i+1), lc.get(i+2));
		}
		return isAligned;
	}
	
	public int getX() { return this.x; }
	
	public int getY(){ return this.y; }
	
	public CarreauCarte getCarreauCarte() { return this.cc; }
	
	public void setCarreauCarte(CarreauCarte cc) { this.cc = cc; }
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordonnees) return ((Coordonnees) o).getX() == x && ((Coordonnees) o).getY() == y;
		if (o instanceof CarreauCarte) return ((CarreauCarte) o).getCoordonnees().getX() == x && ((CarreauCarte) o).getCoordonnees().getY() == y;
		return false;
	}
	
}
