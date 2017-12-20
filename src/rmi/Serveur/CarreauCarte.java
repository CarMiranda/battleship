package rmi.Serveur;

import java.util.ArrayList;
import java.util.List;
/**
 * Cette classe représente un carreau de grille de jeu.
 * @author Carlos MIRANDA, Jorge OCHOA
 *
 */
public class CarreauCarte {
	
	private final Coordonnees c;
	private PartieBateau pb;
	
	/**
	 * Constructeur.
	 * @param x abscisse
	 * @param y ordonnée
	 */
	public CarreauCarte(int x, int y) {
		pb = null;
		c = new Coordonnees(x, y);
		c.setCarreauCarte(this);
	}
	
	/**
	 * Constructeur
	 * @param c cordonnées.
	 */
	public CarreauCarte(Coordonnees c) {
		this.c = c;
		this.c.setCarreauCarte(this);
	}
	
	/**
	 * Attaque ce CarreauCarte, i.e. attaque la partie de bateau associée ou
	 * marque la case comme touchée.
	 * @return si on a touché un bateau
	 */
	public boolean attaquer() {
		if (pb == null) {
			return false;
		} else {
			pb.attaquer();
			return true;
		}
	}
	
	/**
	 * Vérifie que la liste de CarreauCarte corresponde à une séquence de CarreauCartes
	 * alignés (cf. Coordonnees.aligne)
	 * @param lcc	Liste de CarreauCarte
	 * @return si les CarreauCarte sont alignés ou pas
	 */
	public final boolean aligne(List<CarreauCarte> lcc) {
		List<Coordonnees> lc = new ArrayList<Coordonnees>(lcc.size());
		for (CarreauCarte cc : lcc) {
			lc.add(cc.getCoordonnees());
		}
		return Coordonnees.aligne(lc);
	}
	
	/**
	 * Permet d'associer à un objet de CarreauCarte une partie de bateau (represente que le carreau de la carte 
	 * contient cette partie du bateau).
	 * Lorsque le carreau ne contient pas de partie de bateau, l'atttribut pointe sur null.
	 * @param partieBateau la partie du bateau  à lier.
	 */
	public void lierPartieBateau(PartieBateau partieBateau) throws CarreauUtiliseException {
		if (this.pb == null) {
			this.pb = partieBateau;
		} else {
			throw new CarreauUtiliseException();
		}
	}
	
	/**
	 * Getter
	 * @return	Coordonnes du carreau dans la carte.
	 */
	public Coordonnees getCoordonnees() { return c; }
	
	/**
	 * Permet de savoir si un carreau contient un parti du bateau.
	 * @return true se le carreau est associé avec une partie de bateau.
	 */
	public boolean contientBateau() { return this.pb != null; }
	
	/**
	 *Redefinition de equals.
	 *Teste si le carreau est au même coordonnées qu'un autre carreau ou s'il a les mêmes
	 *coordonnées que deux coordonnée données. 
	 */
	public boolean equals(Object o) {
		if (o instanceof CarreauCarte) return c.equals(((CarreauCarte) o).getCoordonnees());
		if (o instanceof Coordonnees) return c.equals((Coordonnees) o);
		return false;
	}
}
