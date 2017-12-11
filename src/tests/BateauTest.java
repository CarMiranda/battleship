package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import rmi.Serveur.Bateau;
import rmi.Serveur.TypesBateau;

public class BateauTest {

	@Test
	public void test() {
		try {
			Bateau b = new Bateau(TypesBateau.CROISEUR);
			assertFalse(b.estCoule());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
