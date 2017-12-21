package rmi.Serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Cette classe est l'implémentation de l'interface IEntree.
 * @author Carlos MIRANDA
 *
 */
public class Entree extends UnicastRemoteObject implements IEntree {
	
	private static final long serialVersionUID = -575027575945659838L;
	private final String nom;
	private int defaites;
	private int victoires;
	
	/**
	 * Constructeur
	 * @param joueur nom du joueur.
	 * @param adversaire nom de l'adversaire.
	 * @param victoires nombre de victoires.
	 * @param defaites nombre de défaites.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public Entree(String joueur, String adversaire, int victoires, int defaites)
			throws RemoteException {
		this.nom = adversaire;
		this.defaites = defaites;
		this.victoires = victoires;
		Registry registry = LocateRegistry.getRegistry(/*Serveur.HOST, Serveur.PORT*/);
		try {
			registry.bind(joueur + adversaire + "Entree", this);
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void ajouterVictoire() throws RemoteException { victoires += 1; }
	
	@Override
	public void ajouterDefaite() throws RemoteException { defaites += 1; }

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public int getDefaites() throws RemoteException { return defaites; }

	@Override
	public int getVictoires() throws RemoteException { return victoires; }

}
