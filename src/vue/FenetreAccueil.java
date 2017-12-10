package vue;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import rmi.Client.IUtilisateur;
import rmi.Serveur.IUtilisateurDistant;
import rmi.Serveur.IEntree;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


class MyCellRenderer extends JLabel implements ListCellRenderer<IUtilisateurDistant> {

	private static final long serialVersionUID = 1L;
	final static ImageIcon connectedIcon = new ImageIcon(ClassLoader.getSystemResource("connected.png"));
	final static ImageIcon notConnectedIcon = new ImageIcon(ClassLoader.getSystemResource("notConnected.png"));
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends IUtilisateurDistant> list, 
			IUtilisateurDistant value,
			int index,
			boolean isSelected,
			boolean cellHasFocus
			) {
		
		try {
			setText(value.getNom());
			setIcon(value.estConnecte() ? connectedIcon : notConnectedIcon);
			if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
			setEnabled(list.isEnabled());
	        setFont(list.getFont());
	        setOpaque(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return this;
		
	}
}

public class FenetreAccueil extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private final static Font POLICE_SOUSTITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);
	
	private TableModelStats tableStats;
	private Client client;
	private JList<IUtilisateurDistant> usersList;
	
	public void actualiserUtilisateurs() {
		usersList.repaint();
	}
	
	public FenetreAccueil(Client client) throws RemoteException {
		super("Battaille Navale");
		
		this.client = client;
		final IUtilisateur user = client.getUtilisateur();
		this.tableStats = new TableModelStats(user);
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

		usersList = new JList<IUtilisateurDistant>();
		Object[] tmp = user.getUtilisateurs().values().toArray();
		IUtilisateurDistant[] userNames = new IUtilisateurDistant[tmp.length];
		for (int i = 0; i < tmp.length; i += 1) {
			userNames[i] = (IUtilisateurDistant) tmp[i];
		}
		usersList.setCellRenderer(new MyCellRenderer());
		usersList.setModel(new MyListModel());
		usersList.setListData(userNames);
		usersPanel.add(usersList, BorderLayout.CENTER);
		
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					user.finirUtilisation();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		this.setVisible(true);
	}
	
	class MyComparator implements Comparator<IUtilisateurDistant> {
		@Override
		public int compare(IUtilisateurDistant utilisateur1, IUtilisateurDistant utilisateur2) {
			try {
				if (utilisateur1.estConnecte() == utilisateur2.estConnecte()) {
					// Les deux utilisateurs ont même état de connexion donc l'ordre est lexicographique
					return utilisateur1.getNom().compareTo(utilisateur2.getNom());
				} else {
					if (utilisateur1.estConnecte()) {
						// Le premier utilisateur est connecte, il sera donc prioritaire
						return 1;
					} else {
						// Le deuxieme utilisateur est connecte, il sera donc prioritaire
						return -1;
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
		
	class MyListModel extends AbstractListModel<IUtilisateurDistant> {
		
		private SortedSet<IUtilisateurDistant> data = null;
		private static final long serialVersionUID = 1L;
		
		public MyListModel() {
			super();
			data = new TreeSet<IUtilisateurDistant>(new MyComparator());
		}

		@Override
		public IUtilisateurDistant getElementAt(int index) {
			return (IUtilisateurDistant) data.toArray()[index];
		}

		@Override
		public int getSize() {
			return data.size();
		}
		
		public void add(IUtilisateurDistant utilisateur) {
			data.add(utilisateur);
			fireContentsChanged(this, 0, getSize() - 1);
		}
		
		public void addAll(IUtilisateurDistant[] utilisateurs) {
			Collection<IUtilisateurDistant> c = Arrays.asList(utilisateurs);
			data.addAll(c);
			fireContentsChanged(this, 0, getSize());
		}
		
	}
		

		@SuppressWarnings("serial")
		private class TableModelStats extends AbstractTableModel{
			//attribtut
			
			private Object[] stats;
			private String[] columnNames = { "Adversaire", "Total de parties", "Parties gagnées",
			        "Parties perdues"};

			//constructeur
			public TableModelStats(IUtilisateur u) throws RemoteException{
				
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

