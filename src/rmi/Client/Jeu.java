package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import rmi.Serveur.AttendPetitConException;
import rmi.Serveur.Coordonnees;
import rmi.Serveur.IJeuDistant;
import vue.Client;
import vue.FenetreJeu;
/**
 * Cette classe représente le jeu de la bataille navale.
 * @author Carlos MIRANDA
 *
 */
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
	/**
	 * Permet de commencer un jeu.
	 */
	public void jouer() throws RemoteException {
		jeuDistant.jouer();
	}
	
	/**
	 * Affichage de la fenêtre de jeu
	 */
	@Override
	public void afficher() {
		fj.setVisible(true);
	}
	
	@Override
	/**
	 * Permet de commencer à placer une flotte de bataux.
	 */
	public void commencerPlacementFlotte()
			throws RemoteException {
		fj.commencerPlacementFlotte();
	}
	/**
	 * Informer le serveur que le jeu est prêt.
	 */
	public void informerPret() {
		try {
			jeuDistant.informerPret();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Permet de finir le placement d'une flotte.
	 */
	public void finirPlacementFlotte()
			throws RemoteException {
		fj.finirPlacementFlotte();
	}
	
	// Méthodes appelées localement
	/**
	 * Cette méthode permet de placer un bateau aux coordonnées indiquées.
	 * 
	 * @param nom nom du bateau à placer
	 * @param coordonneesBateau coordonnées où l'on veut placer le bateau.
	 */
	public void placerBateau(String nom, List<Coordonnees> coordonneesBateau) {
		try {
			System.out.println("Envoi des coordonnees pour le bateau " + nom);
			jeuDistant.placerBateau(joueur.getNom(), nom, coordonneesBateau);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Permet de finir le jeu lors d'un forfait de l'un des joueurs.
	 */
	public void forfait() {
		try {
			jeuDistant.forfait(joueur.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'attquer le carreau de la carte avec les coordonnées indiquées.
	 * 
	 * @param c coordonnées à attaquer.
	 * @return true si l'attaque a réussi (on a touché une partie de bateau).
	 */
	public boolean attaquer(Coordonnees c) {
		if (!estMonTour) throw new AttendPetitConException();
		try {
			return jeuDistant.tour(joueur.getNom(), c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Getter
	 * @return le joueur du client.
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	/**
	 * 	Cette méthode permet de indiquer quel bateau il faut placer. 
	 *  @param taille int taille de bateau à placer.
	 *  @param nomBateau String nom du bateau à placer.
	 */
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
	
	/**
	 * getter
	 * Permet de savoir si c'est e tour du joueur utilisant ce jeu.
	 * @return
	 */
	public boolean estMonTour() {
		return estMonTour;
	}

	/**
	 * Getter
	 * @return Nom de l'adversaire
	 */
	public String getNomAdversaire() {
		try {
			return jeuDistant.getNomAdversaire(joueur.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * getter
	 * @param coules false si on veut le nombre de bateaux non coulés
	 * @return le nombre de bateaux coulés ou non coulés
	 */
	public int getNbBateaux(boolean coules) {
		try {
			return jeuDistant.getNbBateaux(joueur.getNom(), coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Getter
	 *  
	 * @param nomBateau nom du type de bateau dont on cehrche la quantité.
	 * @param coules false si on veut le nombre de bateaux non coulés.
	 * @return nombre de bateaux d'un type spécifiques coulés ou non.
	 */
	public int getNbBateaux(String nomBateau, boolean coules) {
		try {
			return jeuDistant.getNbBateaux(joueur.getNom(), nomBateau, coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Getter
	 * @param coules false si on veut le nombre de bateaux non coulés.
	 * @return nombre de bateaux ennemis coulés ou non
	 */
	public int getNbBateauxAdversaire(boolean coules) {
		try {
			return jeuDistant.getNbBateauxAdversaire(joueur.getNom(), coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Getter
	 * 
	 * @param nomBateau nom du type de bateau dont on cehrche la quantité.
	 * @param coules coules false si on veut le nombre de bateaux non coulés.
	 * @return nombre de bateaux ennemis d'un type spécifiques coulés ou non.
	 */
	public int getNbBateauxAdversaire(String nomBateau, boolean coules) {
		try {
			return jeuDistant.getNbBateauxAdversaire(joueur.getNom(), nomBateau, coules);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Getter
	 * @return hauteur de la carte.
	 */
	public int getHauteurCarte() {
		try {
			return jeuDistant.getHauteurCarte();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Getter
	 * @return Hauteur de la carte.
	 */
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
