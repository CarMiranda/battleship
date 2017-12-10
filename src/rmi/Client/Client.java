package rmi.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import modele.Difficulte;

import rmi.Serveur.IEntree;
import rmi.Serveur.IUtilisateurDistant;

public class Client {
	
	public static final int PORT = 5002;
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
			Utilisateur carlos = new Utilisateur("Carlos");
			IUtilisateurDistant carlosd = carlos.authentification("Miranda");
			
			Utilisateur jorge = new Utilisateur("Jorge");
			IUtilisateurDistant jorged = jorge.authentification("Ochoa");
			
			/*if (iud != null)
				System.out.println(iud.getNom());
			else
				System.out.println("User not found.");
			for (IEntree entree : iud.getStatistiques().values()) {
				System.out.println(entree.getNom() + " " + entree.getVictoires() + " " + entree.getDefaites());
			}*/
			carlos.commencerJeu(jorged, Difficulte.FACILE).jouer();
			/*Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			IUtilisateurDistant carlos = (IUtilisateurDistant) registry.lookup("CarlosDistant");
			System.out.println(carlos.estConnecte());*/
		} catch (RemoteException e) {
			e.printStackTrace();
		}/* catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
