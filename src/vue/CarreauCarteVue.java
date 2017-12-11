package vue;

import javax.swing.JLabel;

import rmi.Serveur.ICarreauCarte;

public class CarreauCarteVue extends JLabel{

	private static final long serialVersionUID = 1L;
	public final ICarreauCarte carreauCarte;
	
	public CarreauCarteVue(ICarreauCarte sq){
		super();
		this.carreauCarte = sq;
	}
}
