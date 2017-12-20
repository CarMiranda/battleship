package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 /**
  * Cette classe représente un bateau dans le jeu.
  * @author Jorge OCHOA, Carlos MIRANDA
  *
  */
public class Bateau implements Iterable<PartieBateau> {

	private final int taille;
	private final String nom;
	private final TypesBateau type;
	private boolean estCoule;
	private List<PartieBateau> bateau;
	
	/**
	 * Constructeur
	 * @param type : le type de bateau.
	 */
	public Bateau(TypesBateau type) {
		this.type = type;
		taille = type.getTaille();
		nom = type.getNom();
		estCoule = false;
		bateau = new ArrayList<PartieBateau>();
	};
	
	/**
	 * Cette méthode permet de placer un bateau dans la grille.
	 * @param lcc liste des coordonnées correspondant aux carreaux de la carte où on veut placer le bateau.
	 */
	public void placer(List<Coordonnees> lcc) {
		if (taille != lcc.size()) throw new MauvaiseTailleException();
		System.out.println("Taille correcte");
		if (!Coordonnees.aligne(lcc)) throw new IllegalArgumentException();
		System.out.println("Coordonnées correctes");
		for (Coordonnees cc: lcc) {
			if (cc.getCarreauCarte().contientBateau()) throw new CarreauUtiliseException();
		}
		for (Coordonnees cc: lcc) {
			this.bateau.add(new PartieBateau(cc.getCarreauCarte()));
		}
		System.out.println("Bateau " + nom + " placé avec succès");
	}
	
	/**
	 * Méthode qui permet de savoir si un bateau a été coulé ou pas.
	 * @return true si le bateau es coulé et false dans les autres cas.
	 */
	public boolean estCoule() {
		if (estCoule) { return true; }
		if (bateau.size() == 0) return false; // Cas où le bateau n'a pas encore été placé
		for (PartieBateau pb : bateau) {
			if (!pb.estTouchee()) return false;
		}
		estCoule = true;
		return true;
	}
	
	/**
	 * Getter
	 * @return la taille du bateau
	 */
	public int getTaille() { return this.taille; }
	
	/**
	 * Getter
	 * @return type du bateau
	 */
	public TypesBateau getType() { return this.type; }
	
	/**
	 * Getter
	 * @return liste des différentes parties qui composent le bateau.
	 */
	public List<PartieBateau> getBateau() { return this.bateau; }
	
	/**
	 * Getter
	 * @return nom du bateau.
	 */
	public String getNom() { return this.nom; }
	
	/**
	 * Iterator sur les parties constituent le bateau.
	 */
	public Iterator<PartieBateau> iterator() { return this.bateau.iterator(); }
}