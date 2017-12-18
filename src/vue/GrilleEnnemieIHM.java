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

public class GrilleEnnemieIHM extends GrilleIHM {

	private static final long serialVersionUID = 1L;
	private MonMouseListener ml = new MonMouseListener();
	private Coordonnees coordonneesAAttaquer;
	private CarreauCarteVue ccvAAttaquer;
	
	public GrilleEnnemieIHM(int hauteur, int longueur) {
		super(hauteur, longueur);
		for (Component ccv : pane2.getComponents()) {
			((CarreauCarteVue) ccv).setMouseListener(ml);
		}
		coordonneesAAttaquer = null;
		ccvAAttaquer = null;
	}

	private class MonMouseListener implements MouseListener { 

		@Override
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

	public Coordonnees getCoordonneesAAttaquer() { return coordonneesAAttaquer; }
	
	public void colorierAttaque(boolean resultatAttaque) {
		if (resultatAttaque) {
			/*Chargement de l'image*/
			URL url = ClassLoader.getSystemResource("explosion.png");
			ImageIcon explosionImg = FenetreJeu.resize(new ImageIcon(url), 50, 50);
			/*Fin du chargement de l'image*/
			
			ccvAAttaquer.setIcon(explosionImg);
			ccvAAttaquer.setBackground(Color.RED);
			ccvAAttaquer.setEtat(3);
		} else {
			ccvAAttaquer.setBackground(Color.BLUE);
			ccvAAttaquer.setEtat(1);
		}
		ccvAAttaquer = null;
	}
}
