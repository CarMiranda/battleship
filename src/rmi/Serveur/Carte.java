package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Difficulte;

/**
 * Cette classe représente la carte de jeu.
 * @author Carlos MIRANDA, Cyril ANTOUN.
 *
 */
public class Carte implements Iterable<CarreauCarte> {
	
	private final List<CarreauCarte> carte;
	private final Difficulte difficulte;
	
	/**
	 * Constructeur.
	 * @param difficulte la difficulté
	 */
	public Carte(Difficulte difficulte) {
		this.difficulte = difficulte;
		carte = new ArrayList<>(difficulte.HAUTEUR * difficulte.LARGEUR);
		int i, j;
		for (i = 0; i < difficulte.HAUTEUR; i++) {
			for (j = 0; j < difficulte.LARGEUR; j++) {
				carte.add(new CarreauCarte(i, j));
			}
		}
	}
	/**
	 * Permet de récuperer la liste de coordonnées passées en paramètre dans le serveur.
	 * @param lc liste de coordonnées.
	 * @return coordonnées correspondantes dans le serveur.
	 */
	public List<Coordonnees> toCoordonneesServeur(List<Coordonnees> lc) {
		List<Coordonnees> lcs = new ArrayList<>(lc.size());
		for (Coordonnees c : lc) {
			lcs.add(carte.get(c.getX() + c.getY() * difficulte.LARGEUR).getCoordonnees());
		}
		return lcs;
	}
	
	/**
	 * Permet de récuperer une coordonnée passée en paramètre dans le serveur.
	 * @param c coordonnée
	 * @return coordonnée correspondante dans le serveur.
	 */
	public Coordonnees toCoordonneesServeur(Coordonnees c) {
		return carte.get(c.getX() + c.getY() * difficulte.LARGEUR).getCoordonnees();
	}
	
	/**
	 * Getter
	 * @param x abscisse
	 * @param y ordonnée
	 * @return le carreau de la carte aux coodonnées fournies.
	 */
	public CarreauCarte getCarreauCarte(int x, int y) {
		try {
			return carte.get(carte.indexOf(new Coordonnees(x, y)));
		} catch (IndexOutOfBoundsException e) {
			System.err.println(x + ", " + y);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Getter.
	 * @return la difficulté.
	 */
	public Difficulte getDifficulte() { return difficulte; }

	/**
	 * Itérateur sur les carreaux de la carte.
	 */
	public Iterator<CarreauCarte> iterator() { return carte.iterator(); }
}
