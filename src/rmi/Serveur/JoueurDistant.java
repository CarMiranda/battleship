package rmi.Serveur;

import java.util.List;

import modele.Difficulte;

public class JoueurDistant {

	private final String nom;
	private final Carte carte;
	private final Flotte flotte;
	private Bateau bateauAPlacer;

	public JoueurDistant(String nom, Difficulte difficulte) {
		this.nom = nom;
		carte = new Carte(difficulte);
		flotte = new Flotte();
	}
	
	/**
	 * 
	 */
	public Bateau getBateauAPlacer() {
		try {
			bateauAPlacer = flotte.getBateauSuivant();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return bateauAPlacer;
	}
	
	/**
	 * Attaque le carreau de la carte associé aux coordonnées passées en paramètre
	 * @return si on a touché un bateau ou pas
	 */
	public boolean attaquer(Coordonnees c) {
		return carte.toCoordonneesServeur(c).getCarreauCarte().attaquer();
	}

	/**
	 * Place un bateau dans la carte du joueur
	 * @param bateau le bateau a placer
	 * @param nom
	 * @param lcc 
	 */
	public void placerBateau(String nomBateau, List<Coordonnees> lcc) {
		Bateau bateau = flotte.getBateau(nomBateau);
		System.out.println("Bateau " + nomBateau + " se positionne");
		bateau.placer(lcc);
	}

	/**
	 * 
	 * @return la flotte du joueur
	 */
	public Flotte getFlotte() { return flotte; }

	/**
	 * 
	 * @return la carte du joueur
	 */
	public Carte getCarte() { return carte; }

	/**
	 * 
	 * @return le nom du joueur
	 */
	public String getNom() { return nom; }
	
	/**
	 * 
	 */
	public boolean aPerdu() {
		return flotte.estDetruite();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof JoueurDistant) return ((JoueurDistant) o).getNom().equals(nom);
		return false;
	}

}