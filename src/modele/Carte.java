package modele;

/**
 * Cette classe représente une partie d'un bateau (état et coordonnées).
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carte implements ICarte {
	
	private final List<CarreauCarte> carte = new ArrayList<>(Jeu.DIFFICULTE.HAUTEUR * Jeu.DIFFICULTE.LARGEUR);
	
	public Carte() {
		int i, j;
		for (i = 0; i < Jeu.DIFFICULTE.HAUTEUR; i++) {
			for (j = 0; j < Jeu.DIFFICULTE.LARGEUR; j++) {
				carte.add(new CarreauCarte(i, j));
			}
		}
	}
	
	public CarreauCarte getCarreauCarte(int x, int y) {
		return carte.get(carte.indexOf(new Coordonnees(x, y)));
	}
	
	public Iterator<CarreauCarte> iterator() { return carte.iterator(); }
}
