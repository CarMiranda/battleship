package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import modele.Difficulte;

import org.junit.Test;

import rmi.Client.Joueur;
import rmi.Serveur.Bateau;
import rmi.Serveur.Carte;
import rmi.Serveur.Coordonnees;
import rmi.Serveur.Flotte;
import rmi.Serveur.JoueurDistant;
import rmi.Serveur.PartieBateau;

public class FlotteTest {

	@Test
	public void test() {
		JoueurDistant joueur = new JoueurDistant("Test", Difficulte.DIFFICILE);
		Flotte flotte = joueur.getFlotte();
		int i = 0, j = 0;
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (Bateau b : flotte) {
			for (j = 0; j < b.getTaille(); ++j) {
				lc.add(new Coordonnees(i,j));
			}
			lc = joueur.getCarte().toCoordonneesServeur(lc);
			System.out.println(lc.size());
			joueur.placerBateau(b.getNom(), lc);
			lc.clear();
			i++;
		}
		joueur.attaquer(joueur.getCarte().toCoordonneesServeur(new Coordonnees(0,0)));
		for (Bateau b : flotte) {
			for (PartieBateau pb : b) {
				System.out.println(pb.getCarreauCarte().getCoordonnees().getX() + ":" + pb.getCarreauCarte().getCoordonnees().getY());
			}
			System.out.println("Est coulé? " + b.estCoule());
		}
		assertFalse(joueur.aPerdu());
	}

}
