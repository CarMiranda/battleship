package rmi.Client;

import java.rmi.RemoteException;

import rmi.Serveur.IEntree;
import rmi.Serveur.IUtilisateurDistant;

public class Client {
	
	public static final int PORT = 5001;
	public static final String HOST = "localhost";
	static {
		System.setProperty("java.rmi.server.hostname", HOST);
		System.setProperty("java.rmi.server.port", Integer.toString(PORT));
	}

	/**
	 * @since 1.0.0
	 */
	public static void main(String[] args) {		
		try {
			// Authentification
			Utilisateur user = new Utilisateur("Carlos");
			IUtilisateurDistant iud = user.authentification("Miranda");
			if (iud != null)
				System.out.println(iud.getNom());
			else
				System.out.println("User not found.");
			for (IEntree entree : iud.getStatistiques().values()) {
				System.out.println(entree.getNom() + " " + entree.getVictoires() + " " + entree.getDefaites());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
