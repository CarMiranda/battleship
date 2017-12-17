package rmi.Serveur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bateau implements Iterable<PartieBateau> {

	private final int taille;
	private final String nom;
	private final TypesBateau type;
	private boolean estCoule;
	private List<PartieBateau> bateau;

	public Bateau(TypesBateau type) {
		this.type = type;
		taille = type.getTaille();
		nom = type.getNom();
		estCoule = false;
		bateau = new ArrayList<PartieBateau>();
	};
	
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
	
	public boolean estCoule() {
		if (estCoule) { return true; }
		if (bateau.size() == 0) return false; // Cas où le bateau n'a pas encore été placé
		for (PartieBateau pb : bateau) {
			if (!pb.estTouchee()) return false;
		}
		estCoule = true;
		return true;
	}
	
	public int getTaille() { return this.taille; }
	
	public TypesBateau getType() { return this.type; }
	
	public List<PartieBateau> getBateau() { return this.bateau; }
	
	public String getNom() { return this.nom; }
	
	public Iterator<PartieBateau> iterator() { return this.bateau.iterator(); }
}