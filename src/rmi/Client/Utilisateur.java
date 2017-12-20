package rmi.Client;

import java.rmi.AlreadyBoundException;
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

/**
 * Cette classe implémente l'interface IUtilisateur.
 * @author Carlos MIRANDA.
 *
 */
public class Utilisateur extends UnicastRemoteObject  implements IUtilisateur {
	
	private static final long serialVersionUID = 2650680778390438018L;
	private String nom;
	private IUtilisateurDistant utilisateurDistant;
	private transient Client client;
	private transient Map<String, Jeu> jeux;

	/**
	 * Constructeur
	 * @param nom nom de l'utilisateur
	 * @param client le client qui crée l'utilisateur
	 * @throws RemoteException
	 */
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
			Registry localRegistry = LocateRegistry.getRegistry();
			Registry remoteRegistry = LocateRegistry.getRegistry(Client.REMOTEHOST);
			auth = (IAuthentification) remoteRegistry.lookup("auth");
			if (auth.authentification(nom, motDePasse)) {
				localRegistry.bind(nom, this);
				utilisateurDistant = (IUtilisateurDistant) remoteRegistry.lookup(nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(this);
				utilisateurDistant.connecter(false);
				return utilisateurDistant;
			}
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
			Registry localRegistry = LocateRegistry.getRegistry();
			Registry remoteRegistry = LocateRegistry.getRegistry(Client.REMOTEHOST);
			auth = (IAuthentification) remoteRegistry.lookup("auth");
			if (auth.inscription(nom, motDePasse)) {
				localRegistry.bind(nom, this);
				utilisateurDistant = (IUtilisateurDistant) remoteRegistry.lookup(nom + "Distant");
				utilisateurDistant.setUtilisateurLocal(this);
				utilisateurDistant.connecter(true);
				return utilisateurDistant;
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
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
	public void rejoindreJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu)
			throws RemoteException {
		Jeu jeuLocal = new Jeu(jeu, this, client);
		jeux.put(utilisateur.getNom(), jeuLocal);
		jeuLocal.afficher();
		//jeuLocal.jouer();
	}

	@Override
	public void finirUtilisation()
			throws RemoteException {
		for (Jeu j : jeux.values()) {
			j.forfait();
		}
		utilisateurDistant.deconnecter();
		Registry registry = LocateRegistry.getRegistry();
		try {
			registry.unbind(nom);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
