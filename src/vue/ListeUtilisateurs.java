package vue;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.TreeSet;

import rmi.Serveur.IUtilisateurDistant;
/**
 * Cette classe représente l'ensemble ordonné d'utilisateurs et leur état de connéxion.
 * @author Carlos MIRANDA
 *
 */
class ListeUtilisateurs extends TreeSet<IUtilisateurDistant> {

	private static final long serialVersionUID = 1L;

	private static class Comparateur implements Comparator<IUtilisateurDistant> {
		@Override
		public int compare(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2) {
			try {
				if (utilisateur1.estConnecte() == utilisateur2.estConnecte()) {
					// Les deux utilisateurs ont même état de connexion donc l'ordre est lexicographique
					return utilisateur1.getNom().compareTo(utilisateur2.getNom());
				} else {
					if (utilisateur1.estConnecte()) {
						// Le premier utilisateur est connecte, il sera donc prioritaire
						return -1;
					} else {
						// Le deuxieme utilisateur est connecte, il sera donc prioritaire
						return 1;
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	/**
	 * Contructeur
	 */
	public ListeUtilisateurs() {
		super(new Comparateur());
	}
	
	/**
	 * Met à jour la liste d'utilsateurs.
	 * @param utilisateurModifie
	 */
	public void update(IUtilisateurDistant utilisateurModifie) {
		if (this.contains(utilisateurModifie)) {
			this.remove(utilisateurModifie);
			this.add(utilisateurModifie);
		}
	}
}
