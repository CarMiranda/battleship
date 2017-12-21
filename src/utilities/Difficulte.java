package utilities;

/**
 * Cette énumération représente les différentes difficultés du jeu (tailles de la carte).
 * @author Cyril ANTOUN
 */
public enum Difficulte {
	FACILE(5,6),
	MOYEN(7,8), 
	DIFFICILE(10,10);
	
	public final int HAUTEUR, LARGEUR;
	
	Difficulte(int hauteur, int largeur) {
		HAUTEUR = hauteur;
		LARGEUR = largeur;
	}
}
