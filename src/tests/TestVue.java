/**
 * 
 */
package tests;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.Client.Client;
import rmi.Client.FenetreJeu;
import rmi.Client.FenetreLogin;
import rmi.Client.Jeu;
import rmi.Client.Utilisateur;
import rmi.Serveur.IUtilisateurDistant;
import rmi.Serveur.JeuDistant;
import rmi.Serveur.UtilisateurDistant;
import utilities.Difficulte;

/**
 * @author Jorge OCHOA
 *
 */
public class TestVue {
	
	private static FenetreJeu IHMMarche;
	
	
	public static void main(String[] args) {
		Client client = new Client(args[0]);
		Utilisateur user = null;
		try {
			user = new Utilisateur("Jorge",client);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UtilisateurDistant userD = null;
		try {
			 userD = new UtilisateurDistant(15151,"Jorge");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AlreadyBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JeuDistant jeuD = null;
		try {
			jeuD = new JeuDistant(userD, userD, Difficulte.FACILE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Jeu leJeu = null;
		try {
			leJeu = new Jeu(user, user,jeuD);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			IHMMarche = new FenetreJeu(leJeu);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
