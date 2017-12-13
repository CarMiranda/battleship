package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import rmi.Serveur.ICarte;
//import vue.GrilleJoueur.MaCarteControleur;

public class GrilleEnnemieIHM extends GrilleIHM {

	private MonMouseListener ml = new MonMouseListener();
	
	public GrilleEnnemieIHM(ICarte carte) throws RemoteException {
		super(carte);
		for (Component ccv : pane2.getComponents()) {
			((CarreauCarteVue) ccv).setMouseListener(ml);
		}
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private class MonMouseListener implements MouseListener { 

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getButton() == 1){ //clic droit
				if (! ((CarreauCarteVue) e.getSource()).getEstAttaque()){
					try {
						((CarreauCarteVue) e.getSource()).attaquer();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
