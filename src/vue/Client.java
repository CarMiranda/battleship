package vue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.WeakHashMap;

import rmi.Client.IUtilisateur;
import rmi.Serveur.IUtilisateurDistant;

public class Client {
	
	public static String REMOTEHOST;
	
	private FenetreLogin fl;
	private FenetreAccueil fa;
	//private FenetreJeu fj;
	private IUtilisateur utilisateur;
	private ListeUtilisateurs utilisateurs;	
	
	public Client(String remoteHost) {
		super();
		REMOTEHOST = remoteHost;
		fl = new FenetreLogin(this);
	}
	
	public void setUtilisateurs() {
		try {
			Map<String, IUtilisateurDistant> utilisateursWeak = new WeakHashMap<String, IUtilisateurDistant>(utilisateur.getUtilisateurs());
			utilisateurs = new ListeUtilisateurs();
			utilisateurs.addAll(utilisateursWeak.values());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public ListeUtilisateurs getUtilisateurs() {
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

	public void actualiserUtilisateurs(IUtilisateurDistant utilisateurDistant, boolean estNouveau) {
		if (estNouveau) {
			utilisateurs.add(utilisateurDistant);	
		} else {
			utilisateurs.update(utilisateurDistant);
		}
		fa.actualiserUtilisateurs();
	}
	
	public void actualiserStat(){
		this.fa.actualiserStats();
	}

}
