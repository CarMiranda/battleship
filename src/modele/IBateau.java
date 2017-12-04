package modele;

/**
 * @author Carlos MIRANDA
 */

import java.util.List;

public interface IBateau extends Iterable<PartieBateau> {

	void placer(List<CarreauCarte> lcc) throws CarreauUtiliseException;
	boolean estCoule();
	int getTaille();
	List<PartieBateau> getBateau();
	String getNom();
	
}
