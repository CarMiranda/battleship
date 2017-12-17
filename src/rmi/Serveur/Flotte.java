package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Flotte implements Iterable<Bateau> {

	private final List<Bateau> flotte;
	private boolean estDetruite;
	private short indiceBateauSuivant;
	
	public Flotte() {
		flotte = new ArrayList<>(TypesBateau.values().length);
		for (TypesBateau s : TypesBateau.values()) {
			flotte.add(new Bateau(s));
		}
		indiceBateauSuivant = -1;
		estDetruite = false;
	}
	
	public Bateau getBateau(String nom) {
		for (Bateau bateau : this) {
			if (bateau.getNom().equals(nom)) return bateau;
		}
		return null;
	}
	
	public Bateau getBateauSuivant() {
		indiceBateauSuivant += 1;
		return flotte.get(indiceBateauSuivant);
	}
	
	public boolean estDetruite() {
		if (estDetruite) return estDetruite;
		for (Bateau b : flotte) {
			if (!b.estCoule()) return false;
			System.out.println("Bateau " + b.getNom() + " est coulé.");
		}
		estDetruite = true;
		return estDetruite;
	}

	public Iterator<Bateau> iterator() {
		return flotte.iterator();
	}
	
	public int getNbBateaux(boolean nonCoules) {
		int counter = 0;
		if (nonCoules) {
			for (Bateau b : flotte) {
				if (!b.estCoule()) {
					counter += 1;
				}
			}
			return counter;
		} else {
			return flotte.size();
		}
	}
	
	public int getNbBateaux(String nomBateau, boolean nonCoules) {
		int counter = 0;
		if (nonCoules) {
			for (Bateau b : flotte) {
				if (b.getNom().equals(nomBateau) && !b.estCoule()) {
					counter += 1;
				}
			}
			return counter;
		} else {
			for (Bateau b : flotte) {
				if (b.getNom().equals(nomBateau)) {
					counter += 1;
				}
			}
			return counter;
		}
	}
}

