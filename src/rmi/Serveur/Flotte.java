package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Cette classe représente une flotte (ensemble de bateaux).
 * @author Carlos MIRANDA, Jorge OCHOA, Cyril ANTOUN
 *
 */
public class Flotte implements Iterable<Bateau> {

	private final List<Bateau> flotte;
	private boolean estDetruite;
	private short indiceBateauSuivant;
	
	/**
	 * Constructeur
	 */
	public Flotte() {
		flotte = new ArrayList<>(TypesBateau.values().length);
		for (TypesBateau s : TypesBateau.values()) {
			flotte.add(new Bateau(s));
		}
		indiceBateauSuivant = -1;
		estDetruite = false;
	}
	
	/**
	 * Getter.
	 * @param nom nom du bateau.
	 * @return le bateau correspondant au nom passé en paramètre.
	 */
	public Bateau getBateau(String nom) {
		for (Bateau bateau : this) {
			if (bateau.getNom().equals(nom)) return bateau;
		}
		return null;
	}
	
	/**
	 * @return bateau suivant de la flotte
	 */
	public Bateau getBateauSuivant() {
		indiceBateauSuivant += 1;
		return flotte.get(indiceBateauSuivant);
	}
	
	/**
	 * Permet de savoir si la flotte est detruite (tous les bateaux sont coulés).
	 * @return si la flotte est détruite.
	 */
	public boolean estDetruite() {
		if (estDetruite) return estDetruite;
		for (Bateau b : flotte) {
			if (!b.estCoule()) return false;
			System.out.println("Bateau " + b.getNom() + " est coulé.");
		}
		estDetruite = true;
		return estDetruite;
	}
	
	/**
	 * Itérateur sur les bateaux de la flotte.
	 */
	public Iterator<Bateau> iterator() {
		return flotte.iterator();
	}
	
	/**
	 * @param nonCoules true pour le nombre de bateaux non coulés.
	 * @return nombre de bateaux de la flotte qui sont coulés ou non.
	 */
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
	
	/**
	 * 
	 * @param nomBateau nom du bateau.
	 * @param nonCoules true pour le nombre de bateaux non coulés.
	 * @return nombre de bateaux de la flotte d'un type donné qui sont coulés ou non.
	 */
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

