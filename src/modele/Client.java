package modele;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
public class Client {
	
	public static final int PORT = 5000;

	/**
	 * @since 1.0.0
	 */
	public static void main(String[] args) {
		IAuthentification auth = null;
		
		try {
			// Authentification
			auth = (IAuthentification)Naming.lookup("rmi://localhost:" + PORT + "/auth");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
