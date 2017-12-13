package vue;

import java.awt.event.MouseListener;

import javax.swing.JLabel;

import rmi.Serveur.ICarreauCarte;

public class CarreauCarteVue extends JLabel {

	private static final long serialVersionUID = 1L;
	public final ICarreauCarte carreauCarte;
	private boolean estAttaque;
	
	public CarreauCarteVue(ICarreauCarte carreauCarte){
		super();
		this.carreauCarte = carreauCarte;
	}
	
	public ICarreauCarte getCarreauCarte() { return this.carreauCarte; }
	public void setMouseListener(MouseListener ml) { addMouseListener(ml); }
	
}
