package rmi.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import modele.Difficulte;


import rmi.Serveur.IAuthentification;
import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;
import vue.Client;

public class Utilisateur extends UnicastRemoteObject  implements IUtilisateur {

	private static final long serialVersionUID = 2650680778390438018L;
	private String nom;
	private IUtilisateurDistant utilisateurDistant;
	private transient Client client;
	private transient Map<String, Jeu> jeux;

	public Utilisateur(String nom, Client client)
			throws RemoteException {
		this.nom = nom;
		this.client = client;
		jeux = new HashMap<String, Jeu>();
	}

	@Override
	public void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException {
		client.actualiserUtilisateurs(utilisateur, estNouveau);
	}

	@Override
	public IUtilisateurDistant authentification(String motDePasse)
			throws RemoteException {
		IAuthentification auth;
		try {
			Registry remoteRegistry = LocateRegistry.getRegistry(Client.REMOTEHOST);
			auth = (IAuthentification) remoteRegistry.lookup("auth");
			if (auth.authentification(nom, motDePasse)) {
				utilisateurDistant = (IUtilisateurDistant) remoteRegistry.lookup(nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(nom, this);
				utilisateurDistant.connecter(false);
				return utilisateurDistant;
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IUtilisateurDistant inscription(String motDePasse)
			throws RemoteException {
		IAuthentification auth;
		try {
			//Registry localRegistry = LocateRegistry.getRegistry();
			Registry remoteRegistry = LocateRegistry.getRegistry(Client.REMOTEHOST);
			auth = (IAuthentification) remoteRegistry.lookup("auth");
			if (auth.inscription(nom, motDePasse)) {
				System.out.println("Connexion satisfactoire");
				utilisateurDistant = (IUtilisateurDistant) remoteRegistry.lookup(nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(nom, this);
				utilisateurDistant.connecter(true);
				return utilisateurDistant;
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte)
			throws RemoteException {
		utilisateurDistant.commencerJeu(utilisateur, "");
	}

	@Override
	public IJeu rejoindreJeu(String nom)
			throws RemoteException {
		IJeuDistant jeu = null;
		try {
			jeu = (IJeuDistant) LocateRegistry.getRegistry(Client.REMOTEHOST).lookup(this.nom + nom + "Distant");
		} catch (NotBoundException e) {
			try {
				jeu = (IJeuDistant) LocateRegistry.getRegistry(Client.REMOTEHOST).lookup(nom + this.nom + "Distant");
			} catch (NotBoundException ee) {
				ee.printStackTrace();
			}
		}
		Jeu jeuLocal = new Jeu(jeu, this, client);
		jeux.put(nom, jeuLocal);
		jeuLocal.afficher();
		return null;
	}

	@Override
	public void finirUtilisation()
			throws RemoteException {
		for (Jeu j : jeux.values()) {
			j.forfait();
		}
		utilisateurDistant.deconnecter();
	}

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public Map<String, IUtilisateurDistant> getUtilisateurs()
			throws RemoteException {
		return utilisateurDistant.getUtilisateurs();
	}

	@Override
	public Map<String, IEntree> getStatistiques()
			throws RemoteException {
		return utilisateurDistant.getStatistiques();
	}

}
