package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.Serveur.IJeuDistant;
import vue.FenetreJeu;

public class Jeu extends UnicastRemoteObject implements IJeu {
	
	private static final long serialVersionUID = -1732761568274563L;
	private IJeuDistant jeuDistant;
	private IJoueur joueur;
	private IJoueur adversaire;
	
	public Jeu(IUtilisateur utilisateur1, IUtilisateur utilisateur2, IJeuDistant jeuDistant) 
		throws RemoteException {
		this.jeuDistant = jeuDistant;
		joueur = new Joueur(this.jeuDistant.getJoueur(utilisateur1.getNom()));
	}
	
	public Jeu(IJeuDistant jeuDistant, IUtilisateur utilisateurLocal)
			throws RemoteException {
		this.jeuDistant = jeuDistant;
		jeuDistant.setJeuLocal(utilisateurLocal, this);
		joueur = new Joueur(this.jeuDistant.getJoueur(utilisateurLocal.getNom()));
		jeuDistant.getJoueur(utilisateurLocal.getNom()).setJoueurLocal(joueur);
		adversaire = new Joueur(this.jeuDistant.getAdversaire(utilisateurLocal.getNom()));
	}
	
	public void afficher() throws RemoteException {
		new FenetreJeu(this);
	}
	
	@Override
	public void jouer() throws RemoteException {
		System.out.println("Début du jeu!");
		jeuDistant.jouer();
		System.out.println("Fin du jeu!");
	}

	@Override
	public IJoueur getJoueur()
			throws RemoteException {
		return joueur;
	}
	
	@Override
	public IJoueur getAdversaire()
			throws RemoteException {
		return adversaire;
	}
}
