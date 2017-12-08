package modele;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Connection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;
/**
 * @author Carlos MIRANDA
 * @version 1.0.0
 * Dec 5, 2017
 */

/**
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public class UtilisateurDistant extends UnicastRemoteObject implements IUtilisateurDistant {
	
	private static final long serialVersionUID;
	private final static TreeSet<IUtilisateurDistant> utilisateurs;
	private String nom;
	private boolean connecte;
	
	static {
		serialVersionUID = 2842424207782534826L;
		utilisateurs = new TreeSet<IUtilisateurDistant>(new Comparator<IUtilisateurDistant>() {
			@Override
			public int compare(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2) {
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
			}
		});
	}
	
	/**
	 * Initialisation de l'ensemble des utilisateurs (i.e. lecture dans la base de données).
	 * @throws SQLException 
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 * @since 1.0.0
	 */
	public static void initUtilisateurs() throws SQLException, RemoteException, AlreadyBoundException {
		Connection conn = Serveur.getConnexionSQL();
		String query = "SELECT uname FROM users;";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			utilisateurs.add(new UtilisateurDistant(rs.getString("uname")));
		}
	}
	
	public String getNom() { return nom; }
	public boolean estConnecte() { return connecte; }
	
	public UtilisateurDistant(String nom) throws RemoteException, AlreadyBoundException {
		this(nom, false);
	}
	
	public UtilisateurDistant(String nom, boolean connecte) throws RemoteException, AlreadyBoundException {
		super();
		this.nom = nom;
		this.connecte = connecte;
		Registry registry = LocateRegistry.getRegistry(Serveur.PORT);
		registry.bind(nom, this);
	}
	
	public static IUtilisateurDistant getUtilisateur(String nom) {
		for (IUtilisateurDistant u : utilisateurs) {
			if (u.getNom().equals(nom)) return u;
		}
		throw new UtilisateurInconnuException();
	}

	@Override
	public TreeSet<IUtilisateurDistant> utilisateurs() throws RemoteException {
		return utilisateurs;
	}

	/**
	 * Recupération des statistiques associées à l'utilisateur.
	 * @since 1.0.0
	 */
	@Override
	public HashSet<IEntree> getStatistiques() throws RemoteException {
		return null;
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
		if (utilisateur.notifierJeu()) {
			return new JeuDistant(this, utilisateur, difficulte);
		}
		return null;
	}

	@Override
	public void deconnecter() throws RemoteException {
		connecte = false;
	}
	
	public void connecter() {
		connecte = true;
	}
	
	public static IUtilisateurDistant addUtilisateurDistant(String nom) {
		try {
			IUtilisateurDistant u = new UtilisateurDistant(nom);
			utilisateurs.add(u);
			return u;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean notifierJeu() throws RemoteException {
		
		return false;
	}

}
