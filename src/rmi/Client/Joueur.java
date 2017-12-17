package rmi.Client;

import java.util.List;

import rmi.Serveur.Coordonnees;

public class Joueur {

	private transient Jeu jeu;
	private final String nom;
	
	public Joueur(Jeu jeu, String nom) {
		this.jeu = jeu;
		this.nom = nom;
	}
	
	public void attaquer(Coordonnees cc) {
		jeu.attaquer(cc);
	}
	
	/**
	 * Envoyer les coordonnées du bateau placé
	 * @param coordonneesBateau
	 */
	public void placerBateau(List<Coordonnees> coordonneesBateau) {
		jeu.placerBateau(nom, coordonneesBateau);
	}

	/**
	 * Instruire au joueur de placer le bateau
	 * @param taille
	 * @param nom
	 * @param coordonneesBateau
	 * @return
	 */
	public List<Coordonnees> placerBateau(int taille, String nom, List<Coordonnees> coordonneesBateau){
		return null;
	}
	
	/**
	 *
	 * @return le nom du joueur
	 */
	public String getNom() { return nom; }

}

