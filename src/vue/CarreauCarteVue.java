package vue;

import java.awt.event.MouseListener;
import java.awt.Color;
import java.rmi.RemoteException;
import javax.swing.JLabel;
import rmi.Serveur.ICarreauCarte;

public class CarreauCarteVue extends JLabel {

	private static final long serialVersionUID = 1L;
	private final ICarreauCarte carreauCarte;
	private boolean estAttaque = false;
	
	public CarreauCarteVue(ICarreauCarte carreauCarte){
		super();
		this.carreauCarte = carreauCarte;
	}
	
	public ICarreauCarte getCarreauCarte() { return this.carreauCarte; }
	public void setMouseListener(MouseListener ml) { addMouseListener(ml); }
	
	public boolean getEstAttaque(){
		return this.estAttaque;
	}

	public boolean attaquer() throws Exception{
		boolean attaqueReussi;
		
		this.estAttaque = true;
		attaqueReussi = this.carreauCarte.attaquer();
		this.setOpaque(true);
		if (attaqueReussi){
			this.setBackground(Color.red);
		} else {
			this.setBackground(Color.blue);
		}
		return attaqueReussi;
	}
}
