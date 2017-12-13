package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import rmi.Client.CarreauUtiliseException;
import rmi.Client.IJoueur;
import rmi.Serveur.Coordonnees;
import rmi.Serveur.IBateau;
import rmi.Serveur.ICarte;
import rmi.Serveur.ICarreauCarte;
import rmi.Serveur.ICoordonnees;

public class GrilleJoueur extends GrilleIHM {

	private static final long serialVersionUID = 1L;
	private boolean placementFlotte;
	private IBateau bateauAPlacer;
	private List<ICarreauCarte> lcc;
	private IJoueur joueur;
	
	private class MaCarteControleur implements MouseListener {

		private boolean isSelecting = false;
		private List<CarreauCarteVue> lccv = new ArrayList<>();
		
		@Override
		public void mouseClicked(MouseEvent me) {}

		@Override
		public void mouseEntered(MouseEvent me) {
			if (placementFlotte && bateauAPlacer != null) {
				try {
					if (isSelecting && lcc.size() < bateauAPlacer.getTaille()) {
						CarreauCarteVue ccv = (CarreauCarteVue) me.getSource();
						if (lccv.size() >= 2 && lccv.get(lccv.size() - 2).equals(ccv)) {
							lcc.remove(lcc.size() - 1);
							lccv.remove(lccv.size() - 1).setBackground(new JLabel("").getBackground());
							return;
						} else {
							ICarreauCarte cc = ccv.getCarreauCarte();
							if (cc.aligne(lcc)) {
								lcc.add(cc);
								lccv.add(ccv);
								ccv.setBackground(Color.GRAY);
							}
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent me) {}

		@Override
		public void mousePressed(MouseEvent me) {
			if (placementFlotte && bateauAPlacer != null) {
				if (!isSelecting) {
					isSelecting = true;
					lccv.add((CarreauCarteVue) me.getSource());
					lcc.add(lccv.get(0).getCarreauCarte());
					lccv.get(0).setBackground(Color.GRAY);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			if (placementFlotte && bateauAPlacer != null) {
				if (isSelecting) {
					isSelecting = false;
					try {
						joueur.placerBateau(bateauAPlacer, lcc);
						colorier(lccv);
						this.notifyAll();
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (CarreauUtiliseException e) {
						e.printStackTrace();
					} catch (rmi.Serveur.CarreauUtiliseException e) {
						e.printStackTrace();
					}
					decolorier(lccv);
					lcc.clear();
					lccv.clear();
				}
			}			
		}
	}
	
	private void decolorier(List<CarreauCarteVue> lccv) {
		JLabel lab = new JLabel();
		for (CarreauCarteVue ccv : lccv) {
			ccv.setBackground(lab.getBackground());
		}
	}
	
	private void colorier(List<CarreauCarteVue> lccv) {
		for (CarreauCarteVue ccv : lccv) {
			ccv.setBackground(Color.BLACK);
		}
	}
	
	public GrilleJoueur(ICarte carte) throws RemoteException {
		super(carte);
		lcc = new ArrayList<ICarreauCarte>();
		placementFlotte = false;
		for (Component ccv : pane2.getComponents()) {
			((CarreauCarteVue) ccv).setMouseListener(new MaCarteControleur());
		}
	}
	
	public void setBateauAPlacer(IBateau bateauAPlacer) {
		this.bateauAPlacer = bateauAPlacer;
		try {
			System.out.println("Placement du bateau " + bateauAPlacer.getNom());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void commencerPlacementFlotte() {
		placementFlotte = true;
	}
	
	public void finirPlacementFlotte() {
		placementFlotte = false;
	}
}
