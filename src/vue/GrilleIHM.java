package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public abstract class GrilleIHM extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	protected JPanel pane1 = new JPanel(new BorderLayout());
	protected JPanel pane2;
	URL url = ClassLoader.getSystemResource("sea.gif");
	private Icon icon = new ImageIcon(url);
	protected JLabel label = new JLabel(icon);
	protected ArrayList<CarreauCarteVue> grille;
	
	protected GrilleIHM(int hauteur, int longueur) {
		super();
		int rows = hauteur;
		int cols = longueur;
		
		this.setPreferredSize(new Dimension(512, 512));
		grille = new ArrayList<CarreauCarteVue>();
		pane2 = new JPanel(new GridLayout(rows, cols));
		pane1.add(label);
		pane1.setOpaque(true);
		pane1.setBounds(0, 0, 512, 512);
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				CarreauCarteVue ccv = new CarreauCarteVue(j, i); //x = cols et y = rows
				ccv.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				pane2.add(ccv);
				grille.add(ccv);
			}
		}
		pane2.setOpaque(false);
		pane2.setBackground(new Color(Color.TRANSLUCENT));
		pane2.setBounds(0, 0, 512, 512);


		add(pane1);
		add(pane2, new Integer(100));
	}
}
