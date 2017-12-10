package rmi.Serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Entree extends UnicastRemoteObject implements IEntree {
	
	private static final long serialVersionUID = -575027575945659838L;
	private final String nom;
	private int defaites;
	private int victoires;
	
	public Entree(String joueur, String adversaire, int victoires, int defaites) throws RemoteException, AlreadyBoundException {
		this.nom = adversaire;
		this.defaites = defaites;
		this.victoires = victoires;
		Registry registry = LocateRegistry.getRegistry(Serveur.HOST, Serveur.PORT);
		registry.bind(joueur + adversaire + "Entree", this);
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
