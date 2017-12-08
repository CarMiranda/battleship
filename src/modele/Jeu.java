package modele;

import java.rmi.RemoteException;

/**
 * @author Carlos MIRANDA
 */
public class Jeu implements IJeu {
	
	private IJeuDistant jeuDistant;
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Jeu(Utilisateur utilisateur1, Utilisateur utilisateur2, Difficulte difficulte) {
		
	}

	@Override
	public Joueur getJoueur(String nom) throws RemoteException {
		
		return null;
	}

	@Override
	public void finirJeu() throws RemoteException {
				
	}

}
