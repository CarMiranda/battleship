package vue;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.WeakHashMap;

import rmi.Client.IUtilisateur;
import rmi.Serveur.IUtilisateurDistant;

public class Client {
	
	public static String HOST = "localhost";
	public static int PORT = 5002;
	
	private FenetreLogin fl;
	private FenetreAccueil fa;
	private FenetreJeu fj;
	private IUtilisateur utilisateur;
	private Map<String, IUtilisateurDistant> utilisateurs;	
	
	public Client() {
		super();
		fl = new FenetreLogin(this);
	}
	
	public void setUtilisateurs() {
		try {
			this.utilisateurs = new WeakHashMap<String, IUtilisateurDistant>(utilisateur.getUtilisateurs());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, IUtilisateurDistant> getUtilisateurs() {
		return utilisateurs;
	}
	
	public IUtilisateur getUtilisateur() {
		return utilisateur;
	}
	
	public void setUtilisateur(IUtilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public void accueil() throws RemoteException {
		setUtilisateurs();
		fa = new FenetreAccueil(this);
		fl.setVisible(false);
	}
	
	public void showLogin() {
		fl.setVisible(true);
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.showLogin();
	}

	public void actualiserUtilisateurs() {
		this.fa.actualiserUtilisateurs();		
	}

}
