package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import rmi.Serveur.Coordonnees;

/**
 * Cette classe représente la grille de jeu ennemie en tant qu'objet de l'IHM.
 * @author Jorge OCHOA, Carlos MIRANDA, Victor LE MAISTRE
 *
 */
public class GrilleEnnemieIHM extends GrilleIHM {

	private static final long serialVersionUID = 1L;
	private MonMouseListener ml = new MonMouseListener();
	private Coordonnees coordonneesAAttaquer;
	private CarreauCarteVue ccvAAttaquer;
	
	/**
	 * Constructeur
	 * @param hauteur hauteur de la grille.
	 * @param longueur longueur de la grille.
	 */
	public GrilleEnnemieIHM(int hauteur, int longueur) {
		super(hauteur, longueur);
		for (Component ccv : pane2.getComponents()) {
			((CarreauCarteVue) ccv).setMouseListener(ml);
		}
		coordonneesAAttaquer = null;
		ccvAAttaquer = null;
	}

	/**
	 * Mouse Listenner pour chaque carreau de la carte
	 * @author Jorge OCHOA, Carlos MIRANDA
	 *
	 */
	private class MonMouseListener implements MouseListener { 

		@Override
		/**Permet de récupérer le carreau de la carte où l'utilisateur à clicker
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)){ //clic gauche
				CarreauCarteVue ccv = (CarreauCarteVue) e.getSource();
				if (!ccv.getEstAttaque()) {
					if (ccvAAttaquer != null) {
						ccvAAttaquer.setOpaque(false);
						ccvAAttaquer.setBackground(new JLabel("").getBackground());
					}
					ccvAAttaquer = ccv;
					ccvAAttaquer.setOpaque(true);
					coordonneesAAttaquer = ccv.getCoordonnees();
				}
			}
		}
	
		@Override
		public void mousePressed(MouseEvent e) {}
	
		@Override
		public void mouseReleased(MouseEvent e) {}
	
		@Override
		public void mouseEntered(MouseEvent e) {}
	
		@Override
		public void mouseExited(MouseEvent e) {}
	}

	/**
	 * Getter
	 * @return le cordonnées du carreau de la carte à attaquer.
	 */
	public Coordonnees getCoordonneesAAttaquer() { return coordonneesAAttaquer; }
	
	/**
	 * Permet de changer l'affichae de la carte lors des attaques.
	 * @param resultatAttaque true si l'attaque a réussi ie. on a touché une partie de bateau
	 */
	public void colorierAttaque(boolean resultatAttaque) {
		if (resultatAttaque) { // On a touché une partie de bateau
			/*Chargement de l'image*/
			URL url = ClassLoader.getSystemResource("explosion.png");
			ImageIcon explosionImg = FenetreJeu.resize(new ImageIcon(url), 50, 50);
			/*Fin du chargement de l'image*/
			
			ccvAAttaquer.setIcon(explosionImg);
			ccvAAttaquer.setBackground(Color.RED);
			ccvAAttaquer.setEtat(3);
		} else { // on a tiré sur un carreau de la carte vide
			ccvAAttaquer.setBackground(Color.BLUE);
			ccvAAttaquer.setEtat(1);
		}
		ccvAAttaquer = null;
	}
}
