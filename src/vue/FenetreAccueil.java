package vue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import modele.Difficulte;

import rmi.Client.IUtilisateur;
import rmi.Serveur.IUtilisateurDistant;
import rmi.Serveur.IEntree;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.SortedSet;
/**
 * Cette classe représente la fenêtre d'accueil du jeu.
 * @author Jorge OCHOA, Carlos MIRANDA, Victor LE LEMAISTRE
 *
 */
public class FenetreAccueil extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private final static Font POLICE_SOUSTITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);
	
	private TableModelStats tableStats;
	private MyListModel usersListModel;
	private JList<IUtilisateurDistant> usersList;
	
	/**
	 * 
	 * @author Carlos MIRANDA
	 *
	 */
	private static class MyCellRenderer extends JLabel implements ListCellRenderer<IUtilisateurDistant> {

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
	
	/**
	 * Cette classe représente la liste des utilisateurs pour l'IHM.
	 * @author Carlos MIRANDA
	 *
	 */
	protected class MyListModel extends DefaultListModel<IUtilisateurDistant> {
		
		private SortedSet<IUtilisateurDistant> data = null;
		private static final long serialVersionUID = 1L;
		
		/**
		 * Contructeur
		 * @param utilisateurs Ennsemble ordonné d'utilsiateurs
		 */
		public MyListModel(SortedSet<IUtilisateurDistant> utilisateurs) {
			super();
			data = utilisateurs;
		}

		@Override
		public IUtilisateurDistant getElementAt(int index) {
			return (IUtilisateurDistant) data.toArray()[index];
		}

		@Override
		public int getSize() {
			return data.size();
		}
		
		/*public void add(IUtilisateurDistant utilisateur) {
			data.add(utilisateur);
			fireContentsChanged(this, 0, getSize());
		}
		
		public void addAll(IUtilisateurDistant[] utilisateurs) {
			Collection<IUtilisateurDistant> c = Arrays.asList(utilisateurs);
			data.addAll(c);
			fireContentsChanged(this, 0, getSize());
		}*/
		
	}
	
	/**
	 * Cette classe représenten le tableau des statistiques pour l'IHM.
	 * @author Jorge OCHOA
	 *
	 */
	private class TableModelStats extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private Object[] stats;
		private String[] columnNames;
		
		/**
		 * Constructeur
		 * @param u l'utilsiteur
		 * @param columnNames 
		 * @throws RemoteException
		 */
		public TableModelStats(IUtilisateur u, String[] columnNames) throws RemoteException{
			this.stats = u.getStatistiques().values().toArray();
			this.columnNames = columnNames;
		}
		
		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public int getRowCount() {
			return this.stats.length;
		}

		@Override
		public String getValueAt(int ligne, int colonne) {
			try {
				switch (colonne) {
				case 0:
						return ((IEntree) stats[ligne]).getNom();
				case 1 :
						return String.valueOf(((IEntree) stats[ligne]).getDefaites() + ((IEntree) stats[ligne]).getVictoires());
				case 2 :
						return String.valueOf(((IEntree) stats[ligne]).getVictoires());
				case 3 :
						return String.valueOf(((IEntree) stats[ligne]).getDefaites());
					default:
						return "###";
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	 * Permet d'actualiser la liste d'utlisateurs.
	 */
	public void actualiserUtilisateurs() {
		usersList.repaint();
	}
	
	/**
	 * Constructeur
	 * @param client le client
	 * @throws RemoteException
	 */
	public FenetreAccueil(final Client client) throws RemoteException {
		super("Bataille Navale - Accueil de " + client.getUtilisateur().getNom());
		
		final IUtilisateur user = client.getUtilisateur();
		String[] columnNames = { "Adversaire", "Total de parties", "Parties gagnées", "Parties perdues"};
		this.tableStats = new TableModelStats(user, columnNames);
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		/*Creation du panel affichage des statistiques de jeu */
		JLabel sousTitreStats = new JLabel("Statistiques de Jeu", SwingConstants.CENTER);
		sousTitreStats.setFont(POLICE_SOUSTITRE);
		sousTitreStats.setVerticalAlignment(JLabel.CENTER);
		JPanel statsPanel = new JPanel(new BorderLayout());
		statsPanel.add(sousTitreStats, BorderLayout.NORTH);
		statsPanel.add(new JScrollPane(new JTable(this.tableStats)), BorderLayout.CENTER);
		
		/*Creation du panel affichage la liste d'utilisateurs connectes */
		JLabel sousTitreUtilisateurs = new JLabel("Liste des Utilisateurs");
		sousTitreUtilisateurs.setFont(POLICE_SOUSTITRE);
		sousTitreUtilisateurs.setVerticalAlignment(JLabel.CENTER);
		JPanel usersPanel = new JPanel(new BorderLayout());
		usersPanel.add(sousTitreUtilisateurs, BorderLayout.NORTH);

		usersList = new JList<IUtilisateurDistant>();
		Object[] tmp = client.getUtilisateurs().toArray();
		IUtilisateurDistant[] userNames = new IUtilisateurDistant[tmp.length];
		for (int i = 0; i < tmp.length; i += 1) {
			userNames[i] = (IUtilisateurDistant) tmp[i];
		}
		usersListModel = new MyListModel(client.getUtilisateurs());
		usersList.setCellRenderer(new MyCellRenderer());
		usersList.setModel(usersListModel);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList<IUtilisateurDistant> theList = (JList<IUtilisateurDistant>) mouseEvent.getSource();
				int index = theList.locationToIndex(mouseEvent.getPoint());
				if (index >= 0) {
					IUtilisateurDistant iud = (IUtilisateurDistant) theList.getModel().getElementAt(index);
					try {
						client.getUtilisateur().commencerJeu(iud, Difficulte.DIFFICILE);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		};
		usersList.addMouseListener(mouseListener);
		usersPanel.add(usersList, BorderLayout.CENTER);
		
		/*Creation titre pour la fenetre d'accueil*/
		JLabel titre = new JLabel("BATTLESHIP GM4-17", SwingConstants.CENTER);
		titre.setFont(POLICE_SOUSTITRE);
		titre.setVerticalAlignment(JLabel.CENTER);
		
		/*On ajoute les differentes composantes sur le panel principal*/
		mainPanel.add(statsPanel, BorderLayout.CENTER);
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
	
	/**
	 * Permet de mettre à jour les statistiques de jeu.
	 */
	public void actualiserStats(){ this.tableStats.fireTableDataChanged();}
}

