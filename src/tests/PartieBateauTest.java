package tests;

import static org.junit.Assert.*;


import org.junit.Test;

import rmi.Serveur.CarreauCarte;
import rmi.Serveur.PartieBateau;

public class PartieBateauTest {

	@Test
	public void test() {
		PartieBateau pb = new PartieBateau(new CarreauCarte(0, 0));
		assertFalse(pb.estTouchee());
	}

}
