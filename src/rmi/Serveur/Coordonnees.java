package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Coordonnees extends UnicastRemoteObject implements ICoordonnees {

	private static final long serialVersionUID = 6113101180175615701L;
	private final int x;
	private final int y;
	private ICarreauCarte cc;
	
	public Coordonnees(int x, int y) throws RemoteException {
		this.x = x;
		this.y = y;
		cc = null;
	}
	
	private static boolean aligne(ICoordonnees c1, ICoordonnees c2, ICoordonnees c3) {
		int dirX1, dirX2, dirY1, dirY2;
		try {
			dirX1 = c2.getX() - c1.getX();
			dirX2 = c3.getX() - c1.getX();
			if (dirX1 == dirX2) {
				dirY1 = c2.getY() - c1.getY();
				dirY2 = c3.getY() - c1.getY();
				return (dirX1 * dirY2 - dirX2 * dirY1) == 0;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static final boolean aligne(List<ICoordonnees> lc) {
		int len = lc.size() - 1, i = 0;
		boolean isAligned = true;
		while (isAligned && i < len) {
			isAligned = aligne(lc.get(i), lc.get(i+1), lc.get(i+2));
		}
		return isAligned;
	}
	
	@Override
	public int getX() throws RemoteException { return this.x; }
	
	@Override
	public int getY() { return this.y; }
	
	public ICarreauCarte getCarreauCarte() { return this.cc; }
	
	@Override
	public void setCarreauCarte(ICarreauCarte cc)  throws RemoteException { this.cc = cc; }
	
	@Override
	public boolean equals(Object o) {
		try {
			if (o instanceof ICoordonnees) return ((ICoordonnees) o).getX() == x && ((Coordonnees) o).getY() == y;
			if (o instanceof ICarreauCarte) return ((ICarreauCarte) o).getCoordonnees().getX() == x && ((CarreauCarte) o).getCoordonnees().getY() == y;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
