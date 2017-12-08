package modele;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
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
public class Serveur {
	
	public static final int PORT = 5000;
	private static Connection connexionSQL;
	private static Properties proprietesConnexion;
	static {
		connexionSQL = null;
		
		proprietesConnexion = new Properties();
		proprietesConnexion.put("user", "project");
		proprietesConnexion.put("password", "project");
		proprietesConnexion.put("useSSL", "false");
	}
	
	public static Connection initConnexionSQL() {
		try {
			connexionSQL = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/battleship",
					proprietesConnexion
			);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
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
				System.out.println("Une tentative de fermeture de connexion SQL a échoué.");
				System.exit(1);
			} else {
				e.printStackTrace();
			}
		}
	}
	
	public void main(String args[]) {
		// Initialisation de toutes les ressources
		try {
			LocateRegistry.createRegistry(PORT);
			initConnexionSQL();
			UtilisateurDistant.initUtilisateurs();
			Authentification.initAuthentification();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fermerConnexionSQL();
		}
	}
}
