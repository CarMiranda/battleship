package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import modele.Difficulte;

import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;

public interface IUtilisateur extends Remote {
	
	// Méthodes à distance
	boolean rejoindreJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu) throws RemoteException;
	
	// Méthodes locales
	IUtilisateurDistant authentification(String motDePasse) throws RemoteException;
	IUtilisateurDistant inscription(String motDePasse) throws RemoteException;
	IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException;
	String getNom() throws RemoteException;
	void finirUtilisation() throws RemoteException;
	Set<IUtilisateurDistant> getUtilisateurs() throws RemoteException;
	Map<String, IEntree> getStatistiques() throws RemoteException;
}
