package vue;

import java.awt.Color;
import java.rmi.RemoteException;

import javax.swing.JLabel;

import rmi.Serveur.ICarreauCarte;

public class CarreauCarteVue extends JLabel{

	private static final long serialVersionUID = 1L;
	private final ICarreauCarte carreauCarte;
	private boolean estAttaque = false;
	
	public CarreauCarteVue(ICarreauCarte sq){
		super();
		this.carreauCarte = sq;
	}
	
	public boolean getEstAttaque(){
		return this.estAttaque;
	}
	public boolean attaquer() throws Exception{
		boolean attaqueReussi;
		
		this.estAttaque = true;
		attaqueReussi = this.carreauCarte.attaquer();
		if (attaqueReussi){
			this.setBackground(Color.RED);
		} else {
			this.setBackground(Color.GRAY);
		}
		return attaqueReussi;
	}
}
