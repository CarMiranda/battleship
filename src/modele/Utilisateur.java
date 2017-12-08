package modele;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
public class Utilisateur implements IUtilisateur {
	
	private String nom;
	private IUtilisateurDistant utilisateurDistant;

	public Utilisateur(String nom) {
		this.nom = nom;
	}

	@Override
	public IUtilisateurDistant authentification(String motDePasse) {
		IAuthentification auth;
		try {
			auth = (IAuthentification) Naming.lookup("rmi://localhost:" + Client.PORT + "/auth");
			if (auth.authentification(nom, motDePasse)) {
				utilisateurDistant = (IUtilisateurDistant) Naming.lookup("rmi://localhost:" + Client.PORT + "/" + nom);
				return utilisateurDistant;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) {
		try {
			return utilisateurDistant.commencerJeu(utilisateur, difficulte);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void finirUtilisation() {
		try {
			utilisateurDistant.deconnecter();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IUtilisateurDistant inscription(String motDePasse) {
		IAuthentification auth;
		try {
			auth = (IAuthentification) Naming.lookup("rmi://localhost:" + Client.PORT + "/auth");
			if (auth.inscription(nom, motDePasse)) {
				utilisateurDistant = (IUtilisateurDistant) Naming.lookup("rmi://localhost:" + Client.PORT + "/" + nom);
				return utilisateurDistant;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
