package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import modele.Difficulte;

import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
import rmi.Serveur.IUtilisateurDistant;

public interface IUtilisateur extends Remote {

	// Méthodes à distance
	IJeu rejoindreJeu(String nom) throws RemoteException;

	// Méthodes locales
	IUtilisateurDistant authentification(String motDePasse) throws RemoteException;
	IUtilisateurDistant inscription(String motDePasse) throws RemoteException;
	void commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException;
	String getNom() throws RemoteException;
	void finirUtilisation() throws RemoteException;
	Map<String, IUtilisateurDistant> getUtilisateurs() throws RemoteException;
	Map<String, IEntree> getStatistiques() throws RemoteException;
	void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException;
}
