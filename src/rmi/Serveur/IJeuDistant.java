package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.Client.IJeu;
import rmi.Client.IUtilisateur;

public interface IJeuDistant extends Remote {
	
	IJoueurDistant getJoueur(String nom) throws RemoteException;
	IJoueurDistant getAdversaire(String nom) throws RemoteException;
	void setJeuLocal(IUtilisateur utilisateur, IJeu jeu) throws RemoteException;
	void jouer() throws RemoteException;

}
