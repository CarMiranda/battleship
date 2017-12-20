package vue;

import java.awt.event.MouseListener;
import javax.swing.JLabel;
import rmi.Serveur.Coordonnees;
/**
 * Cette classe représente un carreau de la carte affichée sur la fênetre de jeu.
 * (carreau carte de l'IHM). 
 * @author Jorge OCHOA, Carlos MIRANDA
 *
 */
public class CarreauCarteVue extends JLabel {

	private static final long serialVersionUID = 1L;
	private int etat; // 0 si vide, 1 si attaqué, 2 si partie bateau, 3 si partie bateau attaquee
	private MouseListener ml;
	private final Coordonnees coordonnees;
	
	/**
	 * Contructeur
	 * @param x abscisse
	 * @param y ordonnée
	 */
	public CarreauCarteVue(int x, int y) {
		super();
		coordonnees = new Coordonnees(x, y);
		etat = 0;
	}
	
	/**
	 * Getter
	 * @return coordonnées du carreau de la carte
	 */
	public Coordonnees getCoordonnees() { return coordonnees; }
	
	/**
	 * Associe une instance d'une class implémentant MouseListener au carreau de la carte
	 * @param ml le mouse listenner
	 */
	public void setMouseListener(MouseListener ml) { 
		this.ml = ml;
		addMouseListener(ml);
	}
	
	/**
	 * Permet de changer l'etat d'un carreau de la carte
	 * 0 si vide, 1 si attaqué, 2 si partie bateau, 3 si partie bateau attaquee
	 * @param i 0 si vide, 1 si attaqué, 2 si partie bateau, 3 si partie bateau attaquee
	 */
	public void setEtat(int i){
		this.etat = i;
	}
	
	/**
	 *
	 * @return si le carreau a déjà été attaqué
	 */
	public boolean getEstAttaque() {
		return etat == 1 || etat == 3;
	}

/*	/**
	 * Informe d'une attaque
	 */
	/*public void attaquer() {
		int etatAttaque;
		if (!jeu.estMonTour()) throw new AttendPetitConException();
		if (getEstAttaque()) throw new IllegalArgumentException();
		etatAttaque = (jeu.attaquer(coordonnees) ? 3 : 1);
		this.setOpaque(true);
		this.etat = etatAttaque;
		switch(etatAttaque) {
		case 1:
			setBackground(Color.blue);
			break;
		case 3:
			setBackground(Color.red);
			break;
		default:
		}
	}*/
	
	/**
	 * Supprime l'instance de la classe implémentant MouseListener
	 */
	public void removeMouseListener() {
		this.removeMouseListener(ml);
		ml = null;
	}
}
