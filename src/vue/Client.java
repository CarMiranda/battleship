package vue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.WeakHashMap;

import rmi.Client.IUtilisateur;
import rmi.Serveur.IUtilisateurDistant;

public class Client {
	
	public static String REMOTEHOST;
	/*public static int REMOTEPORT, PORT;*/
	static {
		/*REMOTEHOST = "localhost";
		REMOTEPORT = 5002;
		PORT = 5002;*/
		//System.setProperty("java.rmi.server.hostname", REMOTEHOST);
		//System.setProperty("java.rmi.server.port", Integer.toString(PORT));
			
	}
	
	private FenetreLogin fl;
	private FenetreAccueil fa;
	//private FenetreJeu fj;
	private IUtilisateur utilisateur;
	private Map<String, IUtilisateurDistant> utilisateurs;	
	
	public Client(String remoteHost) {
		super();
		REMOTEHOST = remoteHost;
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
	
	public static void main(String[] args) throws RemoteException {
		LocateRegistry.getRegistry();
		System.setSecurityManager(new SecurityManager());
		Client client = new Client(args[0]);
		client.showLogin();
	}

	public void actualiserUtilisateurs() {
		this.fa.actualiserUtilisateurs();		
	}

}
