package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PartieBateau extends UnicastRemoteObject implements IPartieBateau {
	
	private static final long serialVersionUID = 4860153582005026777L;
	private boolean touchee;
	private final ICarreauCarte cc;
	
	public PartieBateau(ICarreauCarte cc)
			throws CarreauUtiliseException, RemoteException {
		touchee = false;
		this.cc = cc;
		this.cc.lierPartieBateau(this);	
	}
	
	public boolean estTouchee() throws RemoteException { return touchee; }
	
	public void attaquer() throws RemoteException { touchee = true; }
	
	public ICarreauCarte getCarreauCarte() throws RemoteException { return cc; }	

	@Override
	public boolean equals(Object o) {
		if (o instanceof PartieBateau) return (PartieBateau) o == this;
		if (o instanceof CarreauCarte) return cc.equals((CarreauCarte) o);
		return false;
	}
}
