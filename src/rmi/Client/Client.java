package rmi.Client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.WeakHashMap;

import rmi.Serveur.IUtilisateurDistant;
/**
 * Cette classe représente un client du serveur RMI.
 * @author Carlos MIRANDA
 *
 */
public class Client {

	public static String REMOTEHOST;

	private FenetreLogin fl;
	private FenetreAccueil fa;
	private IUtilisateur utilisateur;
	private ListeUtilisateurs utilisateurs;	
	
	/**
	 * Contructeur.
	 * @param remoteHost le Host distant
	 */
	public Client(String remoteHost) {
		super();
		REMOTEHOST = remoteHost;
		fl = new FenetreLogin(this);
	}
	
	/**
	 * Permet de récuperer la liste d'utilisateurs contenue dans le serveur.
	 */
	public void setUtilisateurs() {
		try {
			Map<String, IUtilisateurDistant> utilisateursWeak = new WeakHashMap<String, IUtilisateurDistant>(utilisateur.getUtilisateurs());
			utilisateurs = new ListeUtilisateurs();
			for (String nom : utilisateursWeak.keySet()) {
				if (nom.equals(utilisateur.getNom())) continue;
				utilisateurs.add((IUtilisateurDistant) LocateRegistry.getRegistry(REMOTEHOST).lookup(nom + "Distant"));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter
	 * @return la liste des utilisateurs
	 */	
	public ListeUtilisateurs getUtilisateurs() {
		return utilisateurs;
	}
	
	/**
	 * Getter
	 * @return l'utilisateur asscié au client.
	 */
	public IUtilisateur getUtilisateur() {
		return utilisateur;
	}
	
	/**
	 * Setter
	 * @param utilisateur l'utilisateur à associer au client.
	 */
	public void setUtilisateur(IUtilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	/**
	 * Permet d'initialiser la fenêtre d'accueil.
	 * @throws RemoteException
	 */
	public void accueil() throws RemoteException {
		setUtilisateurs();
		fa = new FenetreAccueil(this);
		fl.setVisible(false);
	}
	
	/**
	 * Rend visible la fenêtre d'accueil.
	 */
	public void showLogin() {
		fl.setVisible(true);
	}
	
	/**
	 * Main. Lance le programme côté client.
	 * @param args argument du programme.
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
		LocateRegistry.getRegistry();
		System.setSecurityManager(new SecurityManager());
		Client client = new Client(args[0]);
		client.showLogin();
	}
	
	/**
	 * Permet d'actualiser la liste d'utilisateurs.
	 * @param utilisateurDistant utilisateur sur lequel on a réalisé une modification.
	 * @param estNouveau true si l'utilisateur est nouveau
	 */
	public void actualiserUtilisateurs(IUtilisateurDistant utilisateurDistant, boolean estNouveau) {
		if (estNouveau) {
			utilisateurs.add(utilisateurDistant);
		} else {
			utilisateurs.update(utilisateurDistant);
		}
		fa.actualiserUtilisateurs(utilisateurDistant);
	}
	
	/**
	 * Permet d'actualiser les statistiques du jeu.
	 */
	public void actualiserStats(){
		fa.actualiserStats();
	}
	
	public void ajouterStats(String nomAdversaire) {
		fa.ajouterStats(nomAdversaire);
	}

}
