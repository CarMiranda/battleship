package rmi.Client;

/**
 * Cette classe représente une exception levée lorsqu'on essaie de 
 * placer un bateau sur une case contenant déjà un bateau.
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos

public class CarreauUtiliseException extends Exception {

	private static final long serialVersionUID = 3072572276400951592L;

	public CarreauUtiliseException(){
		super();
	}
}
