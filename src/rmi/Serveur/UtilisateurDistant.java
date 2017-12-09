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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import modele.Difficulte;
import modele.UtilisateurInconnuException;

import rmi.Client.IUtilisateur;

public class UtilisateurDistant extends UnicastRemoteObject implements IUtilisateurDistant {
	
	private static final long serialVersionUID;
	private static final Set<IUtilisateurDistant> utilisateurs;
	private final Map<String, IEntree> statistiques;
	private final Map<String, IJeuDistant> jeux;
	private String nom;
	private boolean connecte;
	private IUtilisateur utilisateurLocal;
	private int bddId;
	
	static {
		serialVersionUID = 2842424207782534826L;
		utilisateurs = new TreeSet<IUtilisateurDistant>(new Comparator<IUtilisateurDistant>() {
			@Override
			public int compare(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2) {
				try {
					if (utilisateur1.estConnecte() == utilisateur2.estConnecte()) {
						// Les deux utilisateurs ont même état de connexion donc l'ordre est lexicographique
						return utilisateur1.getNom().compareTo(utilisateur2.getNom());
					} else {
						if (utilisateur1.estConnecte()) {
							// Le premier utilisateur est connecte, il sera donc prioritaire
							return 1;
						} else {
							// Le deuxieme utilisateur est connecte, il sera donc prioritaire
							return -1;
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	
	public UtilisateurDistant(int bddId, String nom)
			throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
		super();
		this.bddId = bddId;
		this.nom = nom;
		this.connecte = false;
		jeux = new HashMap<String, IJeuDistant>();
		statistiques = new HashMap<String, IEntree>();
		Registry registry = LocateRegistry.getRegistry(Serveur.HOST, Serveur.PORT);
		registry.bind(nom + "Distant", this);
		
	}
	
	@Override
	public void setUtilisateurLocal(IUtilisateur utilisateurLocal) throws RemoteException {
		this.utilisateurLocal = utilisateurLocal;
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
			utilisateurs.add(utilisateur);
			stats = conn.prepareStatement(preparedQuery);
			stats.setInt(1, utilisateur.getBddId());
			rsp = stats.executeQuery();
			while (rsp.next()) {
				utilisateur.ajouterEntree(new Entree(rs.getString("uname"), rsp.getString("opponent"), rsp.getInt("win"), rsp.getInt("lose")));
			}
		}
	}
	
	public String getNom() throws RemoteException { return nom; }
	public boolean estConnecte() throws RemoteException { return connecte; }
	public int getBddId() throws RemoteException { return bddId; }
	
	public static IUtilisateurDistant getUtilisateur(String nom) {
		for (IUtilisateurDistant u : utilisateurs) {
			try {
				if (u.getNom().equals(nom)) return u;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		throw new UtilisateurInconnuException();
	}

	@Override
	public Set<IUtilisateurDistant> getUtilisateurs() throws RemoteException {
		return utilisateurs;
	}

	/**
	 * Recupération des statistiques associées à l'utilisateur.
	 * @since 1.0.0
	 */
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

	/**
	 * Commence un jeu contre l'utilisateur passé en paramètre
	 * @param utilisateur L'adversaire défié
	 * @return une instance distante du jeu
	 */
	@Override
	public IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException {
		if (!utilisateur.estConnecte()) {
			throw new UtilisateurNonConnecteException();
		}
		if (jeux.containsKey(nom + utilisateur.getNom()) || jeux.containsKey(utilisateur.getNom() + nom)) return null;
		IJeuDistant jeu = new JeuDistant(this, utilisateur, difficulte);
		jeux.put(nom + utilisateur.getNom(), jeu);
		utilisateur.notifierJeu(this, jeu);
		return jeu;
	}

	@Override
	public void deconnecter() throws RemoteException {
		connecte = false;
	}
	
	public void connecter() {
		connecte = true;
	}
	
	public static IUtilisateurDistant addUtilisateurDistant(int bddId, String nom) {
		try {
			IUtilisateurDistant u = new UtilisateurDistant(bddId, nom);
			u.connecter();
			utilisateurs.add(u);
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
}
