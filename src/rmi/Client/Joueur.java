package rmi.Client;

import java.util.List;

import utilities.Coordonnees;
/**
 * Cette classe représente un joueur  du jeu.
 * @author Cyril ANTOUN, Carlos MIRANDA
 *
 */
public class Joueur {

	private transient Jeu jeu;
	private final String nom;
	
	/**
	 * Constructeur
	 * @param jeu le jeu.
	 * @param nom nom du joueur.
	 */
	public Joueur(Jeu jeu, String nom) {
		this.jeu = jeu;
		this.nom = nom;
	}
	
	/**
	 * Permet d'attaquer le carreau de la carte avec les coordonnées indiquées.
	 * @param cc coordonnées à attaquer.
	 */
	public void attaquer(Coordonnees cc) {
		jeu.attaquer(cc);
	}
	
	/**
	 * Permet d'envoyer les coordonnées où l'on veut placer le bateau au serveur.
	 * @param coordonneesBateau liste de coordonnées où l'on veut placer le bateau
	 */
	public void placerBateau(List<Coordonnees> coordonneesBateau) {
		jeu.placerBateau(nom, coordonneesBateau);
	}
	
	/**
	 *Getter
	 * @return le nom du joueur
	 */
	public String getNom() { return nom; }

}

