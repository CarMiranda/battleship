package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import rmi.Serveur.IBateau;
import rmi.Serveur.IJeuDistant;
import vue.FenetreJeu;

public class Jeu extends UnicastRemoteObject implements IJeu {
	
	private static final long serialVersionUID = -1732761568274563L;
	private IJeuDistant jeuDistant;
	private IJoueur joueur;
	private IJoueur adversaire;
	private FenetreJeu fj;
	
	public Jeu(IUtilisateur utilisateur1, IUtilisateur utilisateur2, IJeuDistant jeuDistant) 
		throws RemoteException {
		this.jeuDistant = jeuDistant;
		joueur = new Joueur(this.jeuDistant.getJoueur(utilisateur1.getNom()), this);
	}
	
	public Jeu(IJeuDistant jeuDistant, IUtilisateur utilisateurLocal)
			throws RemoteException {
		this.jeuDistant = jeuDistant;
		jeuDistant.setJeuLocal(utilisateurLocal, this);
		joueur = new Joueur(this.jeuDistant.getJoueur(utilisateurLocal.getNom()), this);
		jeuDistant.getJoueur(utilisateurLocal.getNom()).setJoueurLocal(joueur);
		adversaire = new Joueur(this.jeuDistant.getAdversaire(utilisateurLocal.getNom()), this);
	}
	
	public void afficher() throws RemoteException {
		fj = new FenetreJeu(this);
	}
	
	public void placerFlotte() {
		fj.commencerPlacementFlotte();
		Iterator<IBateau> it;
		try {
			it = joueur.getFlotte().iterator();
			while (it.hasNext()) {
				fj.setBateauAPlacer(it.next());
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
