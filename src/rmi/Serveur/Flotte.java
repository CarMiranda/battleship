package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;

public class Flotte extends UnicastRemoteObject implements IFlotte {

	private static final long serialVersionUID = 1953553838135245884L;
	private final HashSet<IBateau> flotte = new HashSet<>(TypesBateau.values().length);
	private boolean estDetruite;
	
	public Flotte() throws RemoteException {
		for (TypesBateau s : TypesBateau.values()) {
			flotte.add(new Bateau(s));
		}
	}
	
	@Override
	public boolean estDetruite() throws RemoteException {
		if (estDetruite) return estDetruite;
		for (IBateau b : flotte) {
			if (!b.estCoule()) return false;
		}
		estDetruite = true;
		return estDetruite;
	}

	@Override
	public Iterator<IBateau> iterator() throws RemoteException {
		return flotte.iterator();
	}

	@Override
	public int getNbBateauxNonCoules() throws RemoteException {
		int counter = 0;
		for (IBateau b : flotte) {
			if (!b.estCoule())
				counter++;
		}
		return counter;
	}
	
	@Override
	public int getNbBateauxNonCoules(TypesBateau ship) throws RemoteException {
		int counter = 0;
		for (IBateau b : flotte){
			if (!b.estCoule() && b.getType() == ship)
				counter++;
		}
		return counter;
	}
	
	@Override
	public int getNbBateaux(TypesBateau ship) throws RemoteException {
		int counter = 0;
		for (IBateau b : flotte){
			if (b.getType() == ship)
				counter++;
		}
		return counter;
	}
}

