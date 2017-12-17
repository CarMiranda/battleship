package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Difficulte;

public class Carte implements Iterable<CarreauCarte> {
	
	private final List<CarreauCarte> carte;
	private final Difficulte difficulte;
	
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
	
	public List<Coordonnees> toCoordonneesServeur(List<Coordonnees> lc) {
		List<Coordonnees> lcs = new ArrayList<>(lc.size());
		for (Coordonnees c : lc) {
			lcs.add(carte.get(c.getX() + c.getY() * difficulte.LARGEUR).getCoordonnees());
		}
		return lcs;
	}
	
	public Coordonnees toCoordonneesServeur(Coordonnees c) {
		return carte.get(c.getX() + c.getY() * difficulte.LARGEUR).getCoordonnees();
	}
	
	public CarreauCarte getCarreauCarte(int x, int y) {
		try {
			return carte.get(carte.indexOf(new Coordonnees(x, y)));
		} catch (IndexOutOfBoundsException e) {
			System.err.println(x + ", " + y);
			e.printStackTrace();
		}
		return null;
	}
	
	public Difficulte getDifficulte() { return difficulte; }

	public Iterator<CarreauCarte> iterator() { return carte.iterator(); }
}
