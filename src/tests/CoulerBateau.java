package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rmi.Serveur.Bateau;
import rmi.Serveur.JoueurDistant;
import rmi.Serveur.TypesBateau;
import utilities.Coordonnees;
import utilities.Difficulte;

public class CoulerBateau {

	private JoueurDistant j;
	private Bateau b;
	private List<Coordonnees> lc;
	
	@Before
	public void setUpClass() {
		j = new JoueurDistant("Test", Difficulte.DIFFICILE);
		b = j.getFlotte().getBateauSuivant();
		lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < b.getTaille(); i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
	}
	
	/**
	 * Avant placement
	 */
	@Test
	public void test1() {
		Bateau b = new Bateau(TypesBateau.CROISEUR);
		assertFalse(b.estCoule());
	}
	
	/**
	 * Après placement
	 */
	@Test
	public void test2() {
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
		assertFalse(b.estCoule());
	}
	
	/**
	 * Après attaque de quelques parties
	 */
	@Test
	public void test3() {
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
		for (int i = 0; i < b.getTaille() - 2; i += 1) {
			lc.get(i).getCarreauCarte().attaquer();
		}
		assertFalse(b.estCoule());
	}
	
	/**
	 * Après attaque de toutes les parties du bateau
	 */
	@Test
	public void test4() {
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
		for (Coordonnees c : lc) {
			c.getCarreauCarte().attaquer();
		}
		assertTrue(b.estCoule());
	}

}
