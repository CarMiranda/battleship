package modele;

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
public interface IUtilisateur {
	
	IUtilisateurDistant authentification(String motDePasse);
	IUtilisateurDistant inscription(String motDePasse);
	IJeuDistant commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte);
	void finirUtilisation();
}
