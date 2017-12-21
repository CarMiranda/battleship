package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rmi.Serveur.Bateau;
import rmi.Serveur.JoueurDistant;
import utilities.Coordonnees;
import utilities.Difficulte;
import utilities.CarreauUtiliseException;
import utilities.MauvaiseTailleException;

public class PlacementBateau {
	
	private JoueurDistant j;
	private Bateau b;

	@Before
	public void setUp() {
		j = new JoueurDistant("Test", Difficulte.DIFFICILE);
		b = j.getFlotte().getBateauSuivant();
	}
	
	/**
	 * Placement correct du bateau
	 */
	@Test
	public void test1() {
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < b.getTaille(); i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
		assertTrue(true);
	}
	
	/**
	 * Placement du bateau sur un carreau déjà utilisé par un autre bateau
	 */
	@Test(expected = CarreauUtiliseException.class)
	public void test2() {
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < b.getTaille(); i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
		lc.clear();
		
		b = j.getBateauAPlacer();
		for (int i = 0; i < b.getTaille(); i += 1) {
			lc.add(new Coordonnees(0, i));
		}
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
	}
	
	/**
	 * Moins de coordonnées que prévu
	 */
	@Test(expected = MauvaiseTailleException.class)
	public void test3() {
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < b.getTaille() - 1; i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
	}
	
	/**
	 * Coordonnées non alignées
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test4() {
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < b.getTaille() - 1; i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
		lc.add(new Coordonnees(b.getTaille(), 1));
		lc = j.getCarte().toCoordonneesServeur(lc);
		j.placerBateau(b.getNom(), lc);
	}
}
