package utilities;

import java.io.Serializable;
import java.util.List;

import rmi.Serveur.CarreauCarte;
import rmi.Serveur.ICoordonnees;

/**
 * Cette classe implémente l'interface ICoordonnes
 * @author Carlos MIRANDA, Jorge OCHOA
 *
 */
public class Coordonnees implements Serializable {

	private static final long serialVersionUID = 7956510708633831885L;
	private final int x;
	private final int y;
	private transient CarreauCarte cc;
	
	/**
	 * Constructeur.
	 * @param x abscisse.
	 * @param y	ordonnée.
	 */
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
		cc = null;
	}
	
	/**
	 * Vérifie si trois points sont alignés.
	 * @param c1
	 * @param c2
	 * @param c3
	 * @return true si les points sont alignés.
	 */
	private static boolean aligne(Coordonnees c1, Coordonnees c2, Coordonnees c3) {
		int dirX1, dirX2, dirY1, dirY2;
		dirX1 = c2.getX() - c1.getX();
		dirX2 = c3.getX() - c1.getX();
		dirY1 = c2.getY() - c1.getY();
		dirY2 = c3.getY() - c1.getY();
		return (dirX1 * dirY2 - dirX2 * dirY1) == 0;
	}
	
	/**
	 * Verifie si les coordonnées de la liste sont alignées. 
	 * @param lc liste de coordonnées.
	 * @return vrai si les coordonnées de la liste sont alignés.
	 */
	public static boolean aligne(List<Coordonnees> lc) {
		int len = lc.size() - 2, i = 0;
		boolean isAligned = true;
		while (isAligned && i < len) {
			isAligned = aligne(lc.get(i), lc.get(i+1), lc.get(i+2));
			i++;
		}
		return isAligned;
	}
	/**
	 * Getter
	 * @return abscisse
	 */
	public int getX() { return this.x; }
	
	/**
	 * Getter
	 * @return ordonnéee.
	 */
	public int getY() { return this.y; }
	
	public CarreauCarte getCarreauCarte() { return this.cc; }
	
	/**
	 * Permet d'associer un carreau de la carte aux coordonnées en question.
	 * @param cc carreau de la carte.
	 */
	public void setCarreauCarte(CarreauCarte cc) { this.cc = cc; }
	
	
	/**
	 * Redéfinition de equals.
	 */
	public boolean equals(Object o) {
		if (o instanceof Coordonnees) return ((ICoordonnees) o).getX() == x && ((Coordonnees) o).getY() == y;
		if (o instanceof CarreauCarte) return ((CarreauCarte) o).getCoordonnees().getX() == x && ((CarreauCarte) o).getCoordonnees().getY() == y;
		return false;
	}
	
}
