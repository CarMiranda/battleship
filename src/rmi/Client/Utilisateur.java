package rmi.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;



import rmi.Serveur.IAuthentification;
import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;
import utilities.Difficulte;

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
			Registry remoteRegistry = LocateRegistry.getRegistry(Client.REMOTEHOST);
			auth = (IAuthentification) remoteRegistry.lookup("auth");
			if (auth.inscription(nom, motDePasse)) {
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
	public void commencerJeu(IUtilisateurDistant utilisateur, String difficulte)
			throws RemoteException {
		utilisateurDistant.commencerJeu(utilisateur, difficulte);
	}

	@Override
	public void rejoindreJeu(String nom)
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

	@Override
	public void informerNouvelleEntree(String nomAdversaire)
			throws RemoteException {
		client.actualiserStats();
	}

}
