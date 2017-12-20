package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Cette interface représente les données des statistiques de jeu.
 * Elle représente une ligne de données.
 * @author Carlos MIRANDA.
 *
 */
public interface IEntree extends Remote {

	/**
	 * Getter
	 * @return le nom de l'adversaire.
	 * @throws RemoteException
	 */
	String getNom() throws RemoteException;
	
	/**
	 * Getter
	 * @return nombre de défaites contre un joueur donné.
	 * @throws RemoteException
	 */
	int getDefaites() throws RemoteException;
	
	/**
	 * Getter
	 * @return nombre de victoires contre un joueur donné.
	 * @throws RemoteException
	 */
	int getVictoires() throws RemoteException;
	/**
	 * Ajoute une défaite aux statistiques.
	 * @throws RemoteException
	 */
	void ajouterDefaite() throws RemoteException;
	
	/**
	 * Ajoute une victoire aux statistiques.
	 * @throws RemoteException
	 */
	void ajouterVictoire() throws RemoteException;
	
}
