package modele;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * @author Carlos MIRANDA
 * @version 1.0.0
 * Dec 5, 2017
 */

/**
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public class JeuDistant extends UnicastRemoteObject implements IJeuDistant {

	private static final long serialVersionUID = 6582546444277100505L;
	private IJoueur joueur1;
	private IJoueur joueur2;
	public final Difficulte difficulte;
	
	public JeuDistant(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2, Difficulte difficulte) throws RemoteException {
		if (UtilisateurDistant.getUtilisateur(utilisateur1.getNom()).estConnecte() 
				&& UtilisateurDistant.getUtilisateur(utilisateur2.getNom()).estConnecte()) {
			this.difficulte = difficulte;
			joueur1 = new Joueur(utilisateur1.getNom(), difficulte);
			joueur2 = new Joueur(utilisateur2.getNom(), difficulte);
		}
		throw new IllegalArgumentException();
	}
	
	public IJoueur getJoueur(String nom) {
		if (joueur1.getNom().equals(nom)) {
			return joueur1;
		} else {
			return joueur2;
		}
	}
	
}
