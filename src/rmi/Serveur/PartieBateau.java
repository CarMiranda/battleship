package rmi.Serveur;
/**
 * Cette classe représente une composante d'un bateau.
 * @author Jorge OCHOA
 *
 */
public class PartieBateau {
	
	private boolean touchee;
	private final CarreauCarte cc;
	
	/**
	 * Constructeur.
	 * @param cc carreau de la carte où l'on veut placer la partie du bateau.
	 * @throws CarreauUtiliseException
	 */
	public PartieBateau(CarreauCarte cc) throws CarreauUtiliseException {
		touchee = false;
		this.cc = cc;
		this.cc.lierPartieBateau(this);	
	}
	
	/**
	 * Permet de savoir si cette partie de bateau a été attaqué.
	 * @return true si la partie du bateau a été attaquée.
	 */
	public boolean estTouchee() { return touchee; }
	
	/**
	 * Attaque la partie de bateau en question.
	 */
	public void attaquer() { touchee = true; }
	
	/**
	 * Getter
	 * @return le carreau de la carte qui contient cette partie de bateau.
	 */
	public CarreauCarte getCarreauCarte() { return cc; }	
	
	/**
	 * Rédifintion de equals.
	 * 
	 */
	public boolean equals(Object o) {
		if (o instanceof PartieBateau) return (PartieBateau) o == this;
		if (o instanceof CarreauCarte) return cc.equals((CarreauCarte) o);
		return false;
	}
}
