package modele;

/**
 * @author Carlos MIRANDA
 */
public class Jeu extends Thread {
	
	public static final Difficulte DIFFICULTE = Difficulte.FACILE;
	
	public void run() {
		Joueur p1 = new Joueur("Carlos");
		Joueur p2 = new Joueur("Jorge");
	}

}
