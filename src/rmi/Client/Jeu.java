package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import rmi.Serveur.AttendPetitConException;
import rmi.Serveur.Coordonnees;
import rmi.Serveur.IJeuDistant;
import vue.Client;
import vue.FenetreJeu;

public class Jeu extends UnicastRemoteObject implements IJeu {
	
	private static final long serialVersionUID = -1732761568274563L;
	
	/**
	 * Attributs relatifs au côté serveur
	 */
	private final IJeuDistant jeuDistant;
	
	/**
	 * Attributs relatifs au côté client
	 */
	private transient final Joueur joueur;
	private transient FenetreJeu fj;
	private transient boolean estMonTour;
	
	/**
	 * Constructeur de jeu
	 * @param jeuDistant		le jeu distant auquel on fait référence
	 * @param utilisateurLocal	l'utilisateur local
	 * @throws RemoteException
	 */
	public Jeu(IJeuDistant jeuDistant, IUtilisateur utilisateurLocal,Client leClient)
			throws RemoteException {
		this.jeuDistant = jeuDistant;
		this.jeuDistant.setJeuLocal(this, utilisateurLocal);
		joueur = new Joueur(this, utilisateurLocal.getNom());
		fj = new FenetreJeu(this, leClient);
		estMonTour = false;
		informerPret();
	}
	
	// Méthodes appelées à distance
	
	@Override
	public void jouer() throws RemoteException {
		System.out.println("Début du jeu!");
		jeuDistant.jouer();
		System.out.println("Fin du jeu!");
	}
	
	/**
	 * Affichage de la fenêtre de jeu
	 */
	@Override
	public void afficher() {
		fj.setVisible(true);
	}
	
	@Override
	public void commencerPlacementFlotte()
			throws RemoteException {
		fj.commencerPlacementFlotte();
	}
	
	public void informerPret() {
		try {
			jeuDistant.informerPret();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finirPlacementFlotte()
			throws RemoteException {
		fj.finirPlacementFlotte();
	}
	
	// Méthodes appelées localement
	
	public void placerBateau(String nom, List<Coordonnees> coordonneesBateau) {
		try {
			System.out.println("Envoi des coordonnees pour le bateau " + nom);
			jeuDistant.placerBateau(joueur.getNom(), nom, coordonneesBateau);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void forfait() {
		try {
			jeuDistant.forfait(joueur.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public boolean attaquer(Coordonnees c) {
		if (!estMonTour) throw new AttendPetitConException();
		try {
			return jeuDistant.tour(joueur.getNom(), c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	public void placerBateau(int taille, String nomBateau)
			throws RemoteException {
		fj.setBateauAPlacer(taille, nomBateau);
	}

	@Override
	public void informerPlacementFlotte() throws RemoteException {
		fj.informerPlacementFlotte();
	}

	@Override
	public void informerTour() throws RemoteException {
		estMonTour = true;
		fj.informerTour();
	}

	@Override
	public void informerVictoire(boolean parForfait) throws RemoteException {
		fj.informerVictoire(parForfait);
	}

	@Override
	public void informerDefaite(boolean parForfait) throws RemoteException {
		fj.informerDefaite(parForfait);
	}

	@Override
	public void informerAttaque(Coordonnees coordonneesAttaquees) throws RemoteException {
		fj.informerAttaque(coordonneesAttaquees);
	}

	public boolean estMonTour() {
		return estMonTour;
	}

	public String getNomAdversaire() {
		try {
			return jeuDistant.getNomAdversaire(joueur.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getNbBateaux(boolean coules) {
		try {
			return jeuDistant.getNbBateaux(joueur.getNom(), coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getNbBateaux(String nomBateau, boolean coules) {
		try {
			return jeuDistant.getNbBateaux(joueur.getNom(), nomBateau, coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getNbBateauxAdversaire(boolean coules) {
		try {
			return jeuDistant.getNbBateauxAdversaire(joueur.getNom(), coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getNbBateauxAdversaire(String nomBateau, boolean coules) {
		try {
			return jeuDistant.getNbBateauxAdversaire(joueur.getNom(), nomBateau, coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getHauteurCarte() {
		try {
			return jeuDistant.getHauteurCarte();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getLargeurCarte() { 
		try {
			return jeuDistant.getLargeurCarte();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void informerDebutJeu(String nomJoueur) throws RemoteException {
		fj.informerDebutJeu(nomJoueur);
	}
}
