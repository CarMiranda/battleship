package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

/**
 * Cette classe représente une partie d'un bateau (état et coordonnées).
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos - Refactoring
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

	@Override
	public boolean equals(Object o) {
		if (o instanceof PartieBateau) return (PartieBateau) o == this;
		if (o instanceof CarreauCarte) return cc.equals((CarreauCarte) o);
		return false;
	}
}
