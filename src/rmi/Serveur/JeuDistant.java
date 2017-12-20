package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.util.List;

import modele.Difficulte;

import rmi.Client.IJeu;
import rmi.Client.IUtilisateur;

/**
 * Cette classe implémente l'interface IJeuDistant.
 * @author Carlos MIRANDA.
 *
 */
public class JeuDistant extends UnicastRemoteObject implements IJeuDistant {

	private static final long serialVersionUID = 6582546444277100505L;
	private transient final JoueurDistant joueur1, joueur2;
	private transient JoueurDistant joueurCourant;
	private transient IJeu jeuLocal1, jeuLocal2, jeuLocalCourant;
	private transient boolean commence;
	private transient int joueursPrets;
	private transient final Difficulte difficulte;
	
	/**
	 * Constructeur
	 * @param utilisateur1 utilisateur 1. 
	 * @param utilisateur2 utilisateur 2.
	 * @param difficulte difficulté du jeu.
	 * @throws RemoteException
	 */
	public JeuDistant(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2, Difficulte difficulte)
			throws RemoteException {
		joueur1 = new JoueurDistant(utilisateur1.getNom(), difficulte);
		joueur2 = new JoueurDistant(utilisateur2.getNom(), difficulte);
		commence = false;
		this.difficulte = difficulte;
		joueursPrets = 0;
	}
	
	@Override
	public void setJeuLocal(IJeu jeuLocal, IUtilisateur utilisateur)
			throws RemoteException {
		if (utilisateur.getNom().equals(joueur1.getNom())) {
			jeuLocal1 = jeuLocal;
		} else {
			jeuLocal2 = jeuLocal;
		}
	}
	
	/**
	 * Méthode interne pour gérer le placement de bateaux, notamment le changement de joueur et la fin du placement,
	 * donc le début du jeu
	 * @param joueur
	 * @param jeuLocal
	 */
	private void placementFlotte(JoueurDistant joueur, IJeu jeuLocal) {
		Bateau bateauAPlacer = joueur.getBateauAPlacer();
		try {
			if (bateauAPlacer == null && joueur.equals(joueur1)) {
				System.out.println(joueur1.getNom() + " n'a plus de bateaux à placer.");
				joueurCourant = joueur2;
				jeuLocalCourant = jeuLocal2;
				jeuLocal1.finirPlacementFlotte();
				jeuLocal2.commencerPlacementFlotte();
				jeuLocal1.informerPlacementFlotte();
				placementFlotte(joueur2, jeuLocal2);
			} else if (bateauAPlacer == null && joueur.equals(joueur2)) {
				joueurCourant = joueur1;
				jeuLocalCourant = jeuLocal1;
				jeuLocal2.finirPlacementFlotte();
				jeuLocal1.informerDebutJeu(joueur1.getNom());
				jeuLocal2.informerDebutJeu(joueur1.getNom());
				jeuLocal1.informerTour();
			} else {
				jeuLocalCourant.placerBateau(bateauAPlacer.getTaille(), bateauAPlacer.getNom());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean tour(String nom, Coordonnees coordonneesAttaquees) throws RemoteException {
		if (!nom.equals(joueurCourant.getNom())) throw new AttendPetitConException();
		JoueurDistant joueurPrecedent = joueurCourant;
		IJeu jeuLocalPrecedent = jeuLocalCourant;
		if (joueurCourant.equals(joueur1)) {
			joueurCourant = joueur2;
			jeuLocalCourant = jeuLocal2;
		} else {
			joueurCourant = joueur1;
			jeuLocalCourant = jeuLocal1;
		}
		boolean resultat = joueurCourant.attaquer(coordonneesAttaquees);
		try {
			jeuLocalCourant.informerAttaque(coordonneesAttaquees);
			if (joueurCourant.aPerdu()) {
				ajouterResultat(joueurPrecedent, joueurCourant);
				jeuLocalCourant.informerDefaite(false);
				jeuLocalPrecedent.informerVictoire(false);
				return true;
			} else {
				jeuLocalCourant.informerTour();
				return resultat;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void placementFlotte(String nom) throws RemoteException {
		if (!nom.equals(joueurCourant.getNom())) throw new AttendPetitConException();
		placementFlotte(joueurCourant, jeuLocalCourant);
	}
	
	
	@Override
	public void placerBateau(String nomJoueur, String nomBateau, List<Coordonnees> bateau) 
			throws RemoteException {
		if (!nomJoueur.equals(joueurCourant.getNom())) throw new AttendPetitConException();
		System.out.println("Placement du bateau " + nomBateau + " par " + nomJoueur);
		List<Coordonnees> lc = joueurCourant.getCarte().toCoordonneesServeur(bateau);
		joueurCourant.placerBateau(nomBateau, lc);
		placementFlotte(joueurCourant, jeuLocalCourant);
	}
	
	@Override
	public void jouer() throws RemoteException {
		try {
			if (!commence) {
				commence = true;
				jeuLocal1.commencerPlacementFlotte();
				jeuLocal2.informerPlacementFlotte();
				joueurCourant = joueur1;
				jeuLocalCourant = jeuLocal1;
				placementFlotte(joueur1, jeuLocal1);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Actualise la base de données suite à un jeu
	 * @param gagnant	le joueur ayant gagné le jeu
	 * @param perdant	le joueur ayant perdu le jeu
	 */
	private void ajouterResultat(JoueurDistant gagnant, JoueurDistant perdant) {
		IUtilisateurDistant gagnantDistant = UtilisateurDistant.getUtilisateur(gagnant.getNom());
		IUtilisateurDistant perdantDistant = UtilisateurDistant.getUtilisateur(perdant.getNom());
		try {
			gagnantDistant.ajouterVictoire(perdant.getNom());
			perdantDistant.ajouterDefaite(gagnant.getNom());
			Connection conn = Serveur.getConnexionSQL();
			int nb = gagnantDistant.getVictoires(perdant.getNom());
			String query1 = "UPDATE stats SET  win=" + nb + " WHERE uid=" + gagnantDistant.getBddId() + " AND fid=" + perdantDistant.getBddId() + ";";
			String query2 = "UPDATE stats SET lose=" + nb + " WHERE uid=" + perdantDistant.getBddId() + " AND fid=" + gagnantDistant.getBddId() + ";";
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(query1);
				stmt.executeUpdate(query2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void forfait(String nom) throws RemoteException {
		if (joueur1.getNom().equals(nom)) {
			ajouterResultat(joueur2, joueur1);
			jeuLocal1.informerDefaite(true);
			jeuLocal2.informerVictoire(true);
		} else {
			ajouterResultat(joueur1, joueur2);
			jeuLocal2.informerDefaite(true);
			jeuLocal1.informerVictoire(true);
		}
	}
	
	@Override
	public String getNomAdversaire(String nom) throws RemoteException {
		if (nom.equals(joueur1.getNom())) {
			return joueur2.getNom();
		} else {
			return joueur1.getNom();
		}
	}
	
	private JoueurDistant getJoueur(String nom) {
		if (joueur1.getNom().equals(nom)) return joueur1;
		else return joueur2;
	}
	
	private JoueurDistant getAdversaire(String nom) {
		if (joueur1.getNom().equals(nom)) return joueur2;
		else return joueur1;
	}

	@Override
	public int getNbBateaux(String nom, boolean coules) throws RemoteException {
		return getJoueur(nom).getFlotte().getNbBateaux(coules);
	}

	@Override
	public int getNbBateaux(String nom, String nomBateau, boolean coules)
			throws RemoteException {
		return getJoueur(nom).getFlotte().getNbBateaux(nomBateau, coules);
	}

	@Override
	public int getNbBateauxAdversaire(String nom, boolean coules)
			throws RemoteException {
		return getAdversaire(nom).getFlotte().getNbBateaux(coules);
	}

	@Override
	public int getNbBateauxAdversaire(String nom, String nomBateau,
			boolean coules) throws RemoteException {
		return getAdversaire(nom).getFlotte().getNbBateaux(nomBateau, coules);
	}

	@Override
	public int getHauteurCarte() throws RemoteException {
		return difficulte.HAUTEUR;
	}

	@Override
	public int getLargeurCarte() throws RemoteException {
		return difficulte.LARGEUR;
	}

	@Override
	public void informerPret() throws RemoteException {
		joueursPrets += 1;
		if (joueursPrets == 2) jouer();
	}
}
