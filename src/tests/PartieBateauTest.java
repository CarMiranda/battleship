package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import rmi.Serveur.CarreauCarte;
import rmi.Serveur.CarreauUtiliseException;
import rmi.Serveur.PartieBateau;

public class PartieBateauTest {

	@Test
	public void test() {
		try {
			PartieBateau pb = new PartieBateau(new CarreauCarte(0, 0));
			assertFalse(pb.estTouchee());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CarreauUtiliseException e) {
			e.printStackTrace();
		}
	}

}
