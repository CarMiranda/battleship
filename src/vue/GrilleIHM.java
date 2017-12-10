package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import rmi.Serveur.ICarte;

public class GrilleIHM extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	private final ICarte map;
	private JPanel pane1 = new JPanel(new BorderLayout());	
	private Icon icon = new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/sea.gif");
	private JLabel label = new JLabel(icon);
	
	public GrilleIHM(ICarte carte) throws RemoteException{
		super();
		this.map = carte;
		int rows = map.getDifficulte().HAUTEUR;
		int cols = map.getDifficulte().LARGEUR;
		
		this.setPreferredSize(new Dimension(512, 512));
		JPanel pane2 = new JPanel(new GridLayout(rows, cols));
		pane1.add(label);
		pane1.setOpaque(true);
		pane1.setBounds(0, 0, 512, 512);
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				final CarreauCarteVue seaSquareView = new CarreauCarteVue(map.getCarreauCarte(j, i)); //x = cols et y = rows
				seaSquareView.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				pane2.add(seaSquareView);
			}
		}
		pane2.setOpaque(false);
		pane2.setBackground(new Color(Color.TRANSLUCENT));
		pane2.setBounds(0, 0, 512, 512);


		add(pane1);
		add(pane2, new Integer(100));
	}
}