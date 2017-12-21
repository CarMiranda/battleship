package utilities;

/**
 * Exception levée lorsqu'on essaye d'utiliser un carreau de la carte qui est deja utilisé
 * @author Carlos MIRANDA
 *
 */
public class CarreauUtiliseException extends IllegalArgumentException {

	private static final long serialVersionUID = 3072572276400951592L;

	public CarreauUtiliseException(){
		super();
	}
}
