package modele;

/**
 * @author Carlos MIRANDA
 */

import java.util.List;

public interface IJoueur {
	
	boolean attaquer(CarreauCarte cc);
	void placerBateau(Bateau b, List<CarreauCarte> lcc) throws CarreauUtiliseException;
	Flotte getFlotte();
	Carte getCarte();
	String getNom();

}
