package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import modele.Difficulte;

import rmi.Client.IUtilisateur;

public interface IUtilisateurDistant extends Remote {

	Map<String, IUtilisateurDistant> getUtilisateurs() throws RemoteException;
	Map<String, IEntree> getStatistiques() throws RemoteException;
	IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException;
	void deconnecter() throws RemoteException;
	boolean notifierJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu) throws RemoteException;
	String getNom() throws RemoteException;
	void setUtilisateurLocal(IUtilisateur utilisateur) throws RemoteException;
	void connecter(boolean estNouveau) throws RemoteException;
	boolean estConnecte() throws RemoteException;
	void finirJeu(String adversaire) throws RemoteException;
	void ajouterVictoire(String adversaire) throws RemoteException;
	void ajouterDefaite(String adversaire) throws RemoteException;
	int getVictoires(String adversaire) throws RemoteException;
	int getDefaites(String adversaire) throws RemoteException;
	void ajouterEntree(IEntree entree) throws RemoteException;
	int getBddId() throws RemoteException;
	IUtilisateur getUtilisateurLocal() throws RemoteException;
	void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException;
}
