package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

/**
 * Cette classe représente un bateau (liste de parties de bateau et leurs états).
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos - Refactoring

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Bateau implements IBateau {
	
	private final int taille;
	private final String nom;
	private boolean estCoule;
	private List<PartieBateau> bateau;

	public Bateau(TypesBateau type) {
		taille = type.getTaille();
		nom = type.getNom();
		estCoule = false;
		bateau = new ArrayList<PartieBateau>(this.taille);
	};
	
	public void placer(List<CarreauCarte> lcc) throws CarreauUtiliseException {
		if (!CarreauCarte.aligne(lcc)) throw new IllegalArgumentException();
		for (CarreauCarte cc: lcc) {
			if (cc.contientBateau()) throw new CarreauUtiliseException();
		}
		for (CarreauCarte cc: lcc) {
			this.bateau.add(new PartieBateau(cc));
		}
	}
	
	public boolean estCoule() {
		if (this.estCoule) return true;
		for (PartieBateau pb : bateau) {
			if (!pb.estTouchee()) return false;
		}
		this.estCoule = true;
		return true;
	}
	
	public int getTaille() { return this.taille; }
	
	public List<PartieBateau> getBateau() { return this.bateau;	}
	
	public String getNom() { return this.nom; }
	
	public Iterator<PartieBateau> iterator() { return this.bateau.iterator(); }
}