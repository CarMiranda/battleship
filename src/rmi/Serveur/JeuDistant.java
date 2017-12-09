package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import java.sql.Statement;

import modele.Difficulte;

import rmi.Client.IJeu;
import rmi.Client.IUtilisateur;

@SuppressWarnings("unused")
public class JeuDistant extends UnicastRemoteObject implements IJeuDistant {

	private static final long serialVersionUID = 6582546444277100505L;
	private IJoueurDistant joueur1, joueur2, gagnant, perdant;
	private IJeu jeuLocal1, jeuLocal2;
	private boolean fini, finiParForfait;
	
	public JeuDistant(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2, Difficulte difficulte)
			throws RemoteException {
		if (UtilisateurDistant.getUtilisateur(utilisateur1.getNom()).estConnecte() 
				&& UtilisateurDistant.getUtilisateur(utilisateur2.getNom()).estConnecte()) {
			joueur1 = new JoueurDistant(utilisateur1.getNom(), difficulte);
			joueur2 = new JoueurDistant(utilisateur2.getNom(), difficulte);
			fini = false;
			finiParForfait = false;
		}
		throw new UtilisateurNonConnecteException();
	}
	
	@Override
	public void setJeuLocal(IUtilisateur utilisateur, IJeu jeuLocal)
			throws RemoteException {
		if (utilisateur.getNom().equals(joueur1.getNom())) {
			jeuLocal1 = jeuLocal;
		} else {
			jeuLocal2 = jeuLocal;
		}
	}
	
	@Override
	public void jouer() throws RemoteException {
		try {
			joueur1.placerFlotte();
			joueur2.placerFlotte();
			IJoueurDistant joueurCourant = joueur1;
			while (!joueur1.getFlotte().estDetruite() && !joueur2.getFlotte().estDetruite()
					&& !joueur1.forfait() && !joueur2.forfait()) {
				joueurCourant.attaquer(joueurCourant.getAttaque());
			}
			if (joueur1.getFlotte().estDetruite() || joueur1.forfait()) {
				finiParForfait = joueur1.forfait();
				perdant = joueur1;
				gagnant = joueur2;
				joueur1.aPerdu(joueur1.forfait());
				joueur2.aGagne(joueur1.forfait());
				ajouterResultat(joueur2, joueur1);
			} else {
				perdant = joueur2;
				gagnant = joueur1;
				joueur2.aPerdu(joueur2.forfait());
				joueur1.aGagne(joueur2.forfait());
				ajouterResultat(joueur1, joueur2);
			}
			UtilisateurDistant.getUtilisateur(joueur1.getNom()).finirJeu(joueur2.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
	private void ajouterResultat(IJoueurDistant gagnant, IJoueurDistant perdant)
			throws RemoteException {
		IUtilisateurDistant gagnantDistant = UtilisateurDistant.getUtilisateur(gagnant.getNom());
		IUtilisateurDistant perdantDistant = UtilisateurDistant.getUtilisateur(perdant.getNom());
		gagnantDistant.ajouterVictoire(perdant.getNom());
		perdantDistant.ajouterDefaite(gagnant.getNom());
		Connection conn = Serveur.getConnexionSQL();
		String query = "";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public IJoueurDistant getJoueur(String nom)
			throws RemoteException {
		if (joueur1.getNom().equals(nom)) {
			return joueur1;
		} else {
			return joueur2;
		}
	}
	
	@Override
	public IJoueurDistant getAdversaire(String nom)
			throws RemoteException {
		if (joueur1.getNom().equals(nom)) {
			return joueur2;
		} else {
			return joueur1;
		}
	}
	
}