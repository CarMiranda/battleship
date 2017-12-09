package rmi.Client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;

import modele.Difficulte;


import rmi.Serveur.IAuthentification;
import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;

public class Utilisateur extends UnicastRemoteObject  implements IUtilisateur {
	
	private static final long serialVersionUID = 2650680778390438018L;
	private String nom;
	private IUtilisateurDistant utilisateurDistant;
	//private TreeSet<IJeu> jeux;

	public Utilisateur(String nom)
			throws RemoteException {
		this.nom = nom;		
	}
	
	@Override
	public IUtilisateurDistant authentification(String motDePasse) 
			throws RemoteException {
		IAuthentification auth;
		try {
			auth = (IAuthentification) Naming.lookup("rmi://" + Client.HOST + ":" + Client.PORT + "/auth");
			if (auth.authentification(nom, motDePasse)) {
				Registry registry = LocateRegistry.getRegistry(Client.HOST, Client.PORT);
				registry.bind(nom, this);
				utilisateurDistant = (IUtilisateurDistant) registry.lookup(nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(this);
				return utilisateurDistant;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public IUtilisateurDistant inscription(String motDePasse)
			throws RemoteException {
		IAuthentification auth;
		try {
			auth = (IAuthentification) Naming.lookup("rmi://" + Client.HOST + ":" + Client.PORT + "/auth");
			if (auth.inscription(nom, motDePasse)) {
				Registry registry = LocateRegistry.getRegistry(Client.HOST, Client.PORT);
				registry.bind(nom, this);
				utilisateurDistant = (IUtilisateurDistant) registry.lookup("rmi://" + Client.HOST + ":" + Client.PORT + "/" + nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(this);
				return utilisateurDistant;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte)
			throws RemoteException {
		return utilisateurDistant.commencerJeu(utilisateur, difficulte);
	}
	
	@Override
	public boolean rejoindreJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu)
			throws RemoteException {
		new Jeu(jeu, this);
		return true;
	}

	@Override
	public void finirUtilisation()
			throws RemoteException {
		utilisateurDistant.deconnecter();
	}

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public Set<IUtilisateurDistant> getUtilisateurs()
			throws RemoteException {
		return utilisateurDistant.getUtilisateurs();
	}

	@Override
	public Map<String, IEntree> getStatistiques()
			throws RemoteException {
		return utilisateurDistant.getStatistiques();
	}
	
}
