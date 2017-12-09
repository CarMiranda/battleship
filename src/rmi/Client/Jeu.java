package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import modele.Difficulte;

import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;

public class Jeu extends UnicastRemoteObject implements IJeu {
	
	private static final long serialVersionUID = -1732761568274563L;
	private IJeuDistant jeuDistant;
	private IJoueur joueur;
	
	public Jeu(IUtilisateur utilisateur1, IUtilisateurDistant utilisateur2, Difficulte difficulte) 
		throws RemoteException {
		try {
			utilisateur1.commencerJeu(utilisateur2, difficulte);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Jeu(IJeuDistant jeuDistant, IUtilisateur utilisateurLocal)
			throws RemoteException {
		this.jeuDistant = jeuDistant;
		jeuDistant.setJeuLocal(utilisateurLocal, this);
		try {
			joueur = this.jeuDistant.getJoueur(utilisateurLocal.getNom()).getJoueurLocal();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IJoueur getJoueur()
			throws RemoteException {
		return joueur;
	}
	
	@Override
	public IJoueur getAdversaire()
			throws RemoteException {
		return jeuDistant.getAdversaire(joueur.getNom()).getJoueurLocal();
	}
}
