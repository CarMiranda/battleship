package rmi.Serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Connection;
import java.util.HashMap;
import java.util.Map;

import modele.Difficulte;
import modele.UtilisateurInconnuException;

import rmi.Client.IUtilisateur;

/**
 * Cette classe implémente l'interface IUtilisateurDistant
 * @author Carlos MIRANDA
 *
 */
public class UtilisateurDistant extends UnicastRemoteObject implements IUtilisateurDistant {
	
	private static final long serialVersionUID;
	private static final Map<String, IUtilisateurDistant> utilisateurs;
	private final Map<String, IEntree> statistiques;
	private final Map<String, IJeuDistant> jeux;
	private String nom;
	private boolean connecte;
	private transient IUtilisateur utilisateurLocal;
	private int bddId;
	
	static {
		serialVersionUID = 1255597867363756474L;
		utilisateurs = new HashMap<String, IUtilisateurDistant>();
	}
	
	/**
	 * Constructeur
	 * @param bddId Id de la base de données
	 * @param nom nome de l'utilisateur
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public UtilisateurDistant(int bddId, String nom)
			throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
		super();
		this.bddId = bddId;
		this.nom = nom;
		this.connecte = false;
		jeux = new HashMap<String, IJeuDistant>();
		statistiques = new HashMap<String, IEntree>();
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(nom + "Distant", this);
		
	}
	
	@Override
	public void setUtilisateurLocal(IUtilisateur utilisateurLocal) throws RemoteException {
		this.utilisateurLocal = utilisateurLocal;
	}
	
	@Override
	public IUtilisateur getUtilisateurLocal() throws RemoteException {
		return utilisateurLocal;
	}
	
	/**
	 * Initialisation de l'ensemble des utilisateurs (i.e. lecture dans la base de données).
	 * @throws SQLException 
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @since 1.0.0
	 */
	public static void initUtilisateurs()
			throws SQLException, RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
		Connection conn = Serveur.getConnexionSQL();
		
		String query = "SELECT id, uname FROM users;";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		PreparedStatement stats = null;
		String preparedQuery = "SELECT win, lose, uname AS opponent FROM stats LEFT JOIN users ON id = fid WHERE uid = ?;";
		ResultSet rsp = null;
		
		while (rs.next()) {
			IUtilisateurDistant utilisateur = new UtilisateurDistant(rs.getInt("id"), rs.getString("uname"));
			utilisateurs.put(utilisateur.getNom(), utilisateur);
			stats = conn.prepareStatement(preparedQuery);
			stats.setInt(1, utilisateur.getBddId());
			rsp = stats.executeQuery();
			while (rsp.next()) {
				utilisateur.ajouterEntree(new Entree(rs.getString("uname"), rsp.getString("opponent"), rsp.getInt("win"), rsp.getInt("lose")));
			}
		}
		System.out.println("Initialisation de la liste d'utilisateurs.");
	}
	
	public String getNom() throws RemoteException { return nom; }
	public boolean estConnecte() throws RemoteException { return connecte; }
	public int getBddId() throws RemoteException { return bddId; }
	
	public static IUtilisateurDistant getUtilisateur(String nom) {
		for (IUtilisateurDistant u : utilisateurs.values()) {
			try {
				if (u.getNom().equals(nom)) return u;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		throw new UtilisateurInconnuException();
	}

	@Override
	public Map<String, IUtilisateurDistant> getUtilisateurs() throws RemoteException {
		return utilisateurs;
	}

	@Override
	public Map<String, IEntree> getStatistiques() throws RemoteException {
		return statistiques;
	}
	
	@Override
	public void finirJeu(String adversaire) throws RemoteException {
		String nomJeu;
		if (jeux.containsKey(nom + adversaire))
			nomJeu = nom + adversaire;
		else if (jeux.containsKey(adversaire + nom))
			nomJeu = adversaire + nom;
		else
			throw new IllegalArgumentException();
		jeux.remove(nomJeu);
	}
	
	@Override
	public void ajouterVictoire(String adversaire)
		throws RemoteException {
		IEntree entree = statistiques.get(adversaire);
		entree.ajouterVictoire();
	}
	
	@Override
	public void ajouterDefaite(String adversaire)
		throws RemoteException {
		IEntree entree = statistiques.get(adversaire);
		entree.ajouterDefaite();
	}

	@Override
	public void commencerJeu(IUtilisateurDistant adversaire, String diff) throws RemoteException {
		System.out.println(nom + " lance une partie contre " + adversaire.getNom());
		if (nom.equals(adversaire.getNom())) throw new AttendPetitConException();
		if (!adversaire.estConnecte()) throw new UtilisateurNonConnecteException();
		if (jeux.containsKey(nom + adversaire.getNom()) || jeux.containsKey(adversaire.getNom() + nom)) return;
		System.out.println("Jeu valide, création du jeu en cours...");
		Difficulte difficulte = Difficulte.DIFFICILE;
		IJeuDistant jeuDistant = new JeuDistant(this, adversaire, difficulte);
		jeux.put(nom + adversaire.getNom(), jeuDistant);
		notifierJeu(adversaire, jeuDistant);
		adversaire.notifierJeu(this, jeuDistant);
	}

	@Override
	public void deconnecter() throws RemoteException {
		connecte = false;
		for (IUtilisateurDistant iud : utilisateurs.values()) {
			if (iud != this)
			iud.informerConnection(this, false);
		}
		setUtilisateurLocal(null);
	}
	
	@Override
	public void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau)
			throws RemoteException {
		if (utilisateurLocal != null)
			utilisateurLocal.informerConnection(utilisateur, estNouveau);
	}
	
	public void connecter(boolean estNouveau) throws RemoteException {
		connecte = true;
		for (IUtilisateurDistant iud : utilisateurs.values()) {
			if (iud != this)
			iud.informerConnection(this, estNouveau);
		}
	}
	
	public static IUtilisateurDistant addUtilisateurDistant(int bddId, String nom) {
		try {
			IUtilisateurDistant u = new UtilisateurDistant(bddId, nom);
			utilisateurs.put(u.getNom(), u);
			return u;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean notifierJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu) throws RemoteException {
		utilisateurLocal.rejoindreJeu(utilisateur, jeu);
		return true;
	}

	@Override
	public int getVictoires(String adversaire) throws RemoteException {
		return statistiques.get(adversaire).getVictoires();
	}

	@Override
	public int getDefaites(String adversaire) throws RemoteException {
		return statistiques.get(adversaire).getDefaites();
	}

	@Override
	public void ajouterEntree(IEntree entree) throws RemoteException {
		statistiques.put(entree.getNom(), entree);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof IUtilisateurDistant)
			try {
				return this.nom.equals(((IUtilisateurDistant)o).getNom());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return false;
	}
}
