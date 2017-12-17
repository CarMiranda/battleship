package rmi.Serveur;

public class PartieBateau {
	
	private boolean touchee;
	private final CarreauCarte cc;
	
	public PartieBateau(CarreauCarte cc) throws CarreauUtiliseException {
		touchee = false;
		this.cc = cc;
		this.cc.lierPartieBateau(this);	
	}
	
	public boolean estTouchee() { return touchee; }
	
	public void attaquer() { touchee = true; }
	
	public CarreauCarte getCarreauCarte() { return cc; }	

	public boolean equals(Object o) {
		if (o instanceof PartieBateau) return (PartieBateau) o == this;
		if (o instanceof CarreauCarte) return cc.equals((CarreauCarte) o);
		return false;
	}
}
