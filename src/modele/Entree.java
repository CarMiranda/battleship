package modele;

import java.rmi.RemoteException;
/**
 * @author Carlos MIRANDA
 * @version 1.0.0
 * Dec 5, 2017
 */

/**
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public class Entree implements IEntree {
	
	private final String nom;
	private int perdus;
	private int gagnes;
	
	public Entree(String nom, int perdus, int gagnes) {
		this.nom = nom;
		this.perdus = perdus;
		this.gagnes = gagnes;
	}
	
	public void incrementerGagnes() { perdus += 1; }
	
	public void incrementerPerdus() { gagnes += 1; }

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public int getPerdus() throws RemoteException { return perdus; }

	@Override
	public int getGagnes() throws RemoteException { return gagnes; }

}
