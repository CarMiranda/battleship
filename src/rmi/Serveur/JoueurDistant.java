package rmi.Serveur;

import java.util.List;

import modele.Difficulte;
/**
 * Cette classe représente un joueur dans le serveur RMI.
 * @author Carlos MIRANDA
 *
 */
public class JoueurDistant {

	private final String nom;
	private final Carte carte;
	private final Flotte flotte;
	private Bateau bateauAPlacer;

	/**
	 * Constructeur
	 * @param nom nom du joueur.
	 * @param difficulte diificulté du jeu.
	 */
	public JoueurDistant(String nom, Difficulte difficulte) {
		this.nom = nom;
		carte = new Carte(difficulte);
		flotte = new Flotte();
	}
	
	/**
	 * Getter
	 * @return bateau a placer
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
	 * @param c coordonnees a attaque
	 * @return si on a touché un bateau ou pas
	 */
	public boolean attaquer(Coordonnees c) {
		return carte.toCoordonneesServeur(c).getCarreauCarte().attaquer();
	}

	/**
	 * Place un bateau dans la carte du joueur
	 * @param nomBateau le bateau a placer
	 * @param lcc liste de coordonnees ou l'on veut placer le bateau
	 */
	public void placerBateau(String nomBateau, List<Coordonnees> lcc) {
		Bateau bateau = flotte.getBateau(nomBateau);
		System.out.println("Bateau " + nomBateau + " se positionne");
		bateau.placer(lcc);
	}

	/**
	 * Getter
	 * @return la flotte du joueur
	 */
	public Flotte getFlotte() { return flotte; }

	/**
	 * Getter
	 * @return la carte du joueur
	 */
	public Carte getCarte() { return carte; }

	/**
	 * Getter
	 * @return le nom du joueur
	 */
	public String getNom() { return nom; }
	
	/**
	 * Getter
	 * @return si le joueur a perdu.
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