package rmi.Client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import utilities.Coordonnees;
/**
 * Cette classe représente la grille de jeu du joueur en tant qu'objet de l'IHM.
 * @author Carlos MIRANDA, Victor LE MAISTRE
 *
 */
public class GrilleJoueur extends GrilleIHM {

	private static final long serialVersionUID = 1L;
	private boolean placementFlotte;
	private final List<Coordonnees> bateauEnPlacement;
	private final Jeu jeu;
	private final ArrayList<CarreauCarteVue> lccv = new ArrayList<>();
	private boolean isSelecting = false;
	private int tailleBateauAPlacer;
	private String nomBateauAPlacer;
	
	/**
	 * 
	 * @author Carlos MIRANDA.
	 *
	 */
	private class MaCarteControleur implements MouseListener {
		
		public MaCarteControleur() {
			super();
		}
		
		@Override
		public void mouseClicked(MouseEvent me) {}

		@Override
		public void mouseEntered(MouseEvent me) {
			if (placementFlotte) {
				if (isSelecting && bateauEnPlacement.size() >= 1 && bateauEnPlacement.size() < tailleBateauAPlacer) {
					CarreauCarteVue ccv = (CarreauCarteVue) me.getSource();
					if (lccv.size() >= 2 && lccv.get(lccv.size() - 2).equals(ccv)) {
						bateauEnPlacement.remove(bateauEnPlacement.size() - 1);
						CarreauCarteVue ccvb = lccv.remove(lccv.size() - 1);
						ccvb.setBackground(new JLabel("").getBackground());
						ccvb.setOpaque(false);
						return;
					} else {
						bateauEnPlacement.add(ccv.getCoordonnees());
						if (Coordonnees.aligne(bateauEnPlacement)) {
							System.out.println("Ajout du carreau de coordonnées " + ccv.getCoordonnees().getX() + ":" + ccv.getCoordonnees().getY());
							lccv.add(ccv);
							System.out.println(lccv.size() + " carreaux sélectionnées.");
							ccv.setOpaque(true);
							ccv.setBackground(Color.GRAY);
						} else {
							bateauEnPlacement.remove(ccv.getCoordonnees());
						}
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent me) {}

		@Override
		public void mousePressed(MouseEvent me) {
			if (placementFlotte) {
				if (!isSelecting) {
					isSelecting = true;
					lccv.add((CarreauCarteVue) me.getSource());
					System.out.println("Selection d'un carreau");
					bateauEnPlacement.add(lccv.get(0).getCoordonnees());
					lccv.get(0).setOpaque(true);
					lccv.get(0).setBackground(Color.GRAY);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			if (placementFlotte) {
				if (isSelecting) {
					isSelecting = false;
					try {
						System.out.println("Placement du bateau " + nomBateauAPlacer);
						jeu.placerBateau(nomBateauAPlacer, bateauEnPlacement);
						colorier(lccv);
					} catch (utilities.CarreauUtiliseException e) {
						e.printStackTrace();
					} catch (utilities.MauvaiseTailleException e) {
						decolorier(lccv);
					}
					bateauEnPlacement.clear();
					lccv.clear();
				}
			}			
		}
	}
	
	/**
	 * Permet de décolorier une liste de carreaux de la carte.
	 * @param lccv liste de carreaux de la carte
	 */
	private void decolorier(List<CarreauCarteVue> lccv) {
		JLabel lab = new JLabel();
		for (CarreauCarteVue ccv : lccv) {
			ccv.setOpaque(false);
			ccv.setBackground(lab.getBackground());
		}
	}
	
	/**
	 * Permet de colorier une liste de carreaux de la carte.
	 * @param lccv liste de carreaux de la carte
	 */
	private void colorier(List<CarreauCarteVue> lccv) {
		for (CarreauCarteVue ccv : lccv) {
			ccv.setOpaque(true);
			ccv.setBackground(Color.BLACK);
		}
	}
	
	/**
	 * Attaque un carreau de la carte (change sa couleur) sur la grille du joueur.
	 * @param coordonneesAttaquees coordonnées du carreau de la carte à attaquer
	 */
	public void attaquer(Coordonnees coordonneesAttaquees) {
		CarreauCarteVue ccv = grille.get(coordonneesAttaquees.getX() + coordonneesAttaquees.getY() * jeu.getLargeurCarte());
		ccv.setOpaque(true);
		ccv.setBackground(Color.RED);
	}
	
	/**
	 * Constructeur.
	 * @param hauteur hauteur de la grille
	 * @param largeur largeur de la grille
	 * @param jeu le jeu
	 */
	public GrilleJoueur(int hauteur, int largeur, Jeu jeu) {
		super(hauteur, largeur);
		placementFlotte = false;
		this.jeu = jeu;
		bateauEnPlacement = new ArrayList<>();
		for (CarreauCarteVue ccv : grille) {
			ccv.setMouseListener(new MaCarteControleur());
		}
	}
	
	/**
	 * Defini le bateau à placer.
	 * @param taille la taille du batau
	 * @param nom nom du bateau
	 */
	public void setBateauAPlacer(int taille, String nom) {
		System.out.println("Placement du bateau " + nom + " de taille " + taille);
		tailleBateauAPlacer = taille;
		nomBateauAPlacer = nom;
	}
	
	/**
	 * Setter.
	 * Utiliser pour indiquer que le placement d'une flotte est en train de s'effectuer.
	 */
	public void commencerPlacementFlotte() {
		placementFlotte = true;
	}
	
	/**
	 * Setter. 
	 * Utiliser pour indiquer que le placement d'une flotte a fini.
	 */
	public void finirPlacementFlotte() {
		placementFlotte = false;
	}
}
