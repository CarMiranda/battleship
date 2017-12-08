package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.TreeSet;
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
public interface IUtilisateurDistant extends Remote {

	TreeSet<IUtilisateurDistant> utilisateurs() throws RemoteException;
	HashSet<IEntree> getStatistiques() throws RemoteException;
	IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException;
	void deconnecter() throws RemoteException;
	boolean notifierJeu() throws RemoteException;

	//static IUtilisateurDistant getUtilisateur(String nom);
	void connecter();
	boolean estConnecte();
	String getNom();
}
