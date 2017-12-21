package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import utilities.Coordonnees;

import org.junit.Test;

public class CoordonneesTest {

	@Test
	public void testAligne() {
		List<Coordonnees> lc = new ArrayList<Coordonnees>();
		for (int i = 0; i < 10; i += 1) {
			lc.add(new Coordonnees(i, 0));
		}
		assertTrue(Coordonnees.aligne(lc));
	}

}
