package modele;

/**
 * @author Carlos MIRANDA
 */
public interface ICarreauCarte {

	boolean attaquer();
	void lierPartieBateau(PartieBateau pb) throws CarreauUtiliseException;
	Coordonnees getCoordonnees();
	boolean contientBateau();
	
}
