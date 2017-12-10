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
	
	public static Connection initConnexionSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexionSQL = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/battleship",
					proprietesConnexion
			);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Connexion SQL établie correctement.");
		return connexionSQL;
	}
	
	public static Connection getConnexionSQL() {
		return connexionSQL;
	}
	
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
	
	public static void main(String args[]) {
		// Initialisation de toutes les ressources
		try {
			//LocateRegistry.createRegistry(PORT);
			LocateRegistry.createRegistry(1099);
			System.setSecurityManager(new SecurityManager());
			initConnexionSQL();
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
