/**
 * 
 */
package vue;

/**
 * @author Jorge OCHOA
 *
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import rmi.Serveur.IEntree;
import rmi.Serveur.UtilisateurDistant;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("serial")
public class FenetreAccueil extends JFrame {
	private final static Font POLICE_SOUSTITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);
	
	private TableModelStats tableStats;
	private UtilisateurDistant user;
	private JList<String> usersList;
	
	public FenetreAccueil(UtilisateurDistant u) throws RemoteException{
		super("Battaille Navale");
		this.user = u;
		this.tableStats = new TableModelStats(u);
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		/*Creation du panel affichage des statistiques de jeu */
		JLabel label = new JLabel("Statistiques de Jeu", SwingConstants.CENTER);
		label.setFont(POLICE_SOUSTITRE);
		label.setVerticalAlignment(JLabel.CENTER);
		JPanel statsPanel = new JPanel(new BorderLayout());
		statsPanel.add(label, BorderLayout.NORTH);
		statsPanel.add(new JTable(this.tableStats),BorderLayout.WEST);
		
		/*Creation du panel affichage la liste d'utilisateurs connectes */
		JLabel label2 = new JLabel("Liste des Utilisateurs");
		label2.setFont(POLICE_SOUSTITRE);
		label2.setVerticalAlignment(JLabel.CENTER);
		JPanel usersPanel = new JPanel(new BorderLayout());
		usersPanel.add(label, BorderLayout.NORTH);

		this.usersList = new JList<String>();
		Object[] tmp = this.user.getUtilisateurs().toArray();
		String[] userNames = new String[tmp.length];
		for (int i = 0; i<tmp.length; i++){
			userNames[i] = ((UtilisateurDistant)tmp[i]).getNom();
		}
		this.usersList.setListData(userNames);
		usersPanel.add(this.usersList, BorderLayout.CENTER);
		
		/*Creation titre pour la fenetre d'accueil*/
		JLabel titre = new JLabel("BATTLESHIP GM4-17");
		titre.setFont(POLICE_SOUSTITRE);
		titre.setVerticalAlignment(JLabel.CENTER);
		
		/*On ajoute les differentes composantes sur le panel principal*/
		mainPanel.add(statsPanel, BorderLayout.WEST);
		mainPanel.add(usersPanel, BorderLayout.EAST);
		mainPanel.add(titre,BorderLayout.NORTH);
		
		/*On active les proprietes de la fenetre*/
		this.setAlwaysOnTop(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		this.setVisible(true);
	}
		
		

		@SuppressWarnings("serial")
		private class TableModelStats extends AbstractTableModel{
			//attribtut
			
			private Object[] stats;
			private String[] columnNames = { "Adversaire", "Total de parties", "Parties gagnées",
			        "Parties perdues"};

			//constructeur
			public TableModelStats(UtilisateurDistant u) throws RemoteException{
				
				this.stats = u.getStatistiques().values().toArray();
			}

			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return this.columnNames.length;
			}

			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return this.stats.length;
			}

			@Override
			public String getValueAt(int ligne, int colonne) {
				// TODO Auto-generated method stub
				switch (colonne) {
				case 0:
					try {
						return ((IEntree) stats[ligne]).getNom();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 1 :
					try {
						return String.valueOf(((IEntree) stats[ligne]).getDefaites() + ((IEntree) stats[ligne]).getVictoires());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 2 :
					try {
						return String.valueOf(((IEntree) stats[ligne]).getVictoires());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 3 :
					try {
						return String.valueOf(((IEntree) stats[ligne]).getDefaites());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					default:
						return "###";
				}
			}
		}
}

