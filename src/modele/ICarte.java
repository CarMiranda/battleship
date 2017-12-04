package modele;

/**
 * @author Carlos MIRANDA
 */
public interface ICarte extends Iterable<CarreauCarte> {

	public CarreauCarte getCarreauCarte(int x, int y);
	
}
