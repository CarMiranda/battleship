package modele;

import java.rmi.Remote;
/**
 * @author Carlos MIRANDA
 * @version 1.0.0
 * Dec 5, 2017
 */

/**
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public interface IJeuDistant extends Remote {
	
	IJoueur getJoueur(String nom);

}
