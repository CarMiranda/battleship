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
	
	public Entree(String joueur, String adversaire, int perdus, int gagnes) throws RemoteException, AlreadyBoundException {
		this.nom = adversaire;
		this.defaites = perdus;
		this.victoires = gagnes;
		Registry registry = LocateRegistry.getRegistry(Serveur.HOST, Serveur.PORT);
		registry.bind(joueur + adversaire + "Entree", this);
	}
	
	public void ajouterVictoire() { victoires += 1; }
	
	public void ajouterDefaite() { defaites += 1; }

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public int getDefaites() throws RemoteException { return defaites; }

	@Override
	public int getVictoires() throws RemoteException { return victoires; }

}
