package rmi.Serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import utilities.UtilisateurInconnuException;

import com.mysql.jdbc.Connection;

/**
 * Cette classe est l'implémentation de l'interface IAuthentification.
 * @author Jorge OCHOA
 *
 */
public class Authentification extends UnicastRemoteObject implements IAuthentification {

	private static final long serialVersionUID = -1773982318381197725L;
	
	/**
	 * Constructeur.
	 * @throws RemoteException
	 */
	protected Authentification()
			throws RemoteException {
		super();
	}
	
	/**
	 * Initialise le service d'authentification dans le serveur RMI.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void initAuthentification()
			throws RemoteException, AlreadyBoundException {
		Authentification auth = new Authentification();
		Registry registry = LocateRegistry.getRegistry();
		registry.bind("auth", auth);
		System.out.println("Service d'authentification initialisé correctement.");
	}

	@Override
	public boolean authentification(String nom, String motDePasse)
			throws RemoteException {
		IUtilisateurDistant u = UtilisateurDistant.getUtilisateur(nom);
		if (u != null && u.estConnecte()) return false;
		if ((nom != null && !nom.isEmpty()) && (motDePasse != null && !motDePasse.isEmpty())) {
			System.out.println("Tentative de connexion de " + nom);
			return validate(nom, motDePasse);
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean inscription(String nom, String motDePasse)
			throws RemoteException {
		try {
			UtilisateurDistant.getUtilisateur(nom);
			throw new IllegalArgumentException();
		} catch (UtilisateurInconnuException e) {}
		if (nom != null && !nom.isEmpty() && motDePasse != null && !motDePasse.isEmpty()) {
			return add(nom, motDePasse);
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Valide les identifiants fournis.
	 * @param nom
	 * @param motDePasse
	 * @return true si les identifiants sont valides.
	 */
	private boolean validate(String nom, String motDePasse) {
		Connection conn = Serveur.getConnexionSQL();
		String query = "SELECT password FROM users WHERE uname='" + nom + "' LIMIT 1;";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return (rs.next() && rs.getString("password").equals(motDePasse));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * Créer un nouveau compte utilisateur.
	 * @param nom
	 * @param motDePasse
	 * @return true si la création a réussi.
	 */
	private boolean add(String nom, String motDePasse) {
		Connection conn = Serveur.getConnexionSQL();
		String query = "INSERT INTO users (uname, password) VALUES ('" + nom + "', '" + motDePasse + "')";
		Statement stmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmt = null;
		String preparedQuery = "INSERT INTO stats (uid, fid, win, lose) VALUES (?, ?, 0, 0);";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			rs.next();
			IUtilisateurDistant nouvelUtilisateur = UtilisateurDistant.addUtilisateurDistant(rs.getInt(1), nom);
			pstmt = conn.prepareStatement(preparedQuery);
			for (IUtilisateurDistant iud : nouvelUtilisateur.getUtilisateurs().values()) {
				if (iud.equals(nouvelUtilisateur)) continue;
				pstmt.setInt(1, iud.getBddId());
				pstmt.setInt(2, nouvelUtilisateur.getBddId());
				pstmt.executeUpdate();
				iud.ajouterEntree(new Entree(iud.getNom(), nouvelUtilisateur.getNom(), 0, 0));
				pstmt.setInt(1, nouvelUtilisateur.getBddId());
				pstmt.setInt(2, iud.getBddId());
				pstmt.executeUpdate();
				nouvelUtilisateur.ajouterEntree(new Entree(nouvelUtilisateur.getNom(), iud.getNom(), 0, 0));
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return false;
	}

}
