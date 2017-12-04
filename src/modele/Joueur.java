package modele;

/**
 * Cette classe représente un joueur.
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos

import java.util.List;

public class Joueur implements IJoueur {

	private final String nom;
	private final Carte carte;
	private final Flotte flotte;
	
	public Joueur(String nom) {
		this.nom = nom;
		carte = new Carte();
		flotte = new Flotte();
	}
	
	public boolean attaquer(CarreauCarte cc) {
		return cc.attaquer();
	}
	
	public void placerBateau(Bateau bateau, List<CarreauCarte> lcc) throws CarreauUtiliseException {
		if (bateau.getTaille() != lcc.size()) throw new IllegalArgumentException();
		bateau.placer(lcc);
	}
	
	public Flotte getFlotte() { return flotte; }
	
	public Carte getCarte() { return carte; }
	
	public String getNom() { return nom; }

}

