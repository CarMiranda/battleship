package rmi.Serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

/**
 * Cette classe permet d'initialiser le serveur RMI.
 * @author Carlos MIRANDA
 *
 */
public class Serveur {

	/*public static final String HOST;
	public static final int PORT;*/
	private static Connection connexionSQL;
	private static Properties proprietesConnexion;
	static {
		/*HOST = "localhost";
		PORT = 5002;
		System.setProperty("java.rmi.server.hostname", HOST);
		System.setProperty("java.rmi.server.port", Integer.toString(PORT));*/
		connexionSQL = null;
		proprietesConnexion = new Properties();
		proprietesConnexion.put("user", "project");
		proprietesConnexion.put("password", "project");
		proprietesConnexion.put("useSSL", "false");
	}
	
	/**
	 * Permet de se connecter à la base de données.
	 * @return la connexion SQL.
	 */
	public static Connection initConnexionSQL() {
		return initConnexionSQL("localhost");
	}

	public static Connection initConnexionSQL(String host) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexionSQL = (Connection) DriverManager.getConnection(
					"jdbc:mysql://" + host + ":3306/battleship",
					proprietesConnexion
			);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Connexion SQL établie correctement.");
		return connexionSQL;
	}
	
	/**
	 * Getter
	 * @return la connexion SQL.
	 */
	public static Connection getConnexionSQL() {
		return connexionSQL;
	}
	
	/**
	 * Permet de fermer ma connexion SQL.
	 */
	public static void fermerConnexionSQL() {
		try {
			connexionSQL.close();
		} catch (SQLException e) {
			if (connexionSQL == null) {
				System.out.println("Tentative de fermeture de connexion SQL échouée.");
				System.exit(1);
			} else {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Main. Initialise le serveur RMI.
	 * @param args arguments du programme.
	 */
	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(1099);
			System.setSecurityManager(new SecurityManager());
			if (args.length >= 1) {
				initConnexionSQL(args[0]);
			} else {
				initConnexionSQL();
			}
			UtilisateurDistant.initUtilisateurs();
			Authentification.initAuthentification();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} finally {
			//fermerConnexionSQL();
		}
	}
}
