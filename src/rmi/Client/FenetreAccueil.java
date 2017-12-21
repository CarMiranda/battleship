package rmi.Client;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;


import rmi.Serveur.IUtilisateurDistant;
import rmi.Serveur.IEntree;
import utilities.Difficulte;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
/**
 * Cette classe représente la fenêtre d'accueil du jeu.
 * @author Jorge OCHOA, Carlos MIRANDA, Victor LE LEMAISTRE
 *
 */
public class FenetreAccueil extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static Font POLICE_SOUSTITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);

	private TableModelStats tableStats;
	private JTable stats;
	private MyListModel usersListModel;
	private JList<IUtilisateurDistant> usersList;
	private IUtilisateur user;

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

		public void add(IUtilisateurDistant utilisateur) {
			if (data.contains(utilisateur)) {
				data.remove(utilisateur);
				data.add(utilisateur);
			}
			fireContentsChanged(this, 0, getSize());
		}

		/*public void addAll(IUtilisateurDistant[] utilisateurs) {
			Collection<IUtilisateurDistant> c = Arrays.asList(utilisateurs);
			data.addAll(c);
			fireContentsChanged(this, 0, getSize());
		}*/

	}

	/**
	 * Cette classe représente le tableau des statistiques pour l'IHM.
	 * @author Jorge OCHOA
	 *
	 */
	private class TableModelStats extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		private List<IEntree> stats = null;
		private String[] columnNames;

		/**
		 * Constructeur
		 * @param u l'utilsiteur
		 * @param columnNames
		 * @throws RemoteException
		 */
		public TableModelStats(IUtilisateur u, String[] columnNames) throws RemoteException{
			super(columnNames, 0);
			stats = new ArrayList<IEntree>(u.getStatistiques().values());
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
			if (stats != null)
				return this.stats.size();
			return 0;
		}

		@Override
		public String getValueAt(int ligne, int colonne) {
			try {
				switch (colonne) {
				case 0:
						return stats.get(ligne).getNom();
				case 1 :
						return String.valueOf(stats.get(ligne).getDefaites() + stats.get(ligne).getVictoires());
				case 2 :
						return String.valueOf(stats.get(ligne).getVictoires());
				case 3 :
						return String.valueOf(stats.get(ligne).getDefaites());
					default:
						return "###";
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void addRow(IEntree row) {
			stats.add(row);
			Vector<Object> rowVector = new Vector<>();
			try {
				rowVector.add(row.getNom());
				rowVector.add(String.valueOf(row.getVictoires() + row.getDefaites()));
				rowVector.add(String.valueOf(row.getVictoires()));
				rowVector.add(String.valueOf(row.getDefaites()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			super.addRow(rowVector);
		}
	}

	/**
	 * Permet d'actualiser la liste d'utlisateurs.
	 */
	public void actualiserUtilisateurs(IUtilisateurDistant iud) {
		usersListModel.add(iud);
		usersList.repaint();
	}

	public void ajouterStats(String nomAdversaire) {
		try {
			this.tableStats.addRow((IEntree) LocateRegistry.getRegistry(Client.REMOTEHOST).lookup(user.getNom() + nomAdversaire + "Entree"));
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		this.stats.repaint();
	}

	/**
	 * Constructeur
	 * @param client le client
	 * @throws RemoteException
	 */
	public FenetreAccueil(final Client client) throws RemoteException {
		super("Bataille Navale - Accueil de " + client.getUtilisateur().getNom());

		user = client.getUtilisateur();
		String[] columnNames = { "Adversaire", "Total de parties", "Parties gagnées", "Parties perdues"};
		this.tableStats = new TableModelStats(user, columnNames);
		JPanel mainPanel = new JPanel(new BorderLayout());

		/*Creation du panel affichage des statistiques de jeu */
		JLabel sousTitreStats = new JLabel("Statistiques de Jeu", SwingConstants.CENTER);
		sousTitreStats.setFont(POLICE_SOUSTITRE);
		sousTitreStats.setVerticalAlignment(JLabel.CENTER);
		JPanel statsPanel = new JPanel(new BorderLayout());
		statsPanel.add(sousTitreStats, BorderLayout.NORTH);
		stats = new JTable(this.tableStats);
		statsPanel.add(new JScrollPane(stats), BorderLayout.CENTER);

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
					final IUtilisateurDistant iud = (IUtilisateurDistant) theList.getModel().getElementAt(index);

						final JComboBox<String> choixDifficulte = new JComboBox<String>(new String[] {"FACILE", "MOYEN", "DIFFICILE"});
						final JDialog choixDifficulteDialog = new JDialog(FenetreAccueil.this, "Choix de difficulté");
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								choixDifficulteDialog.setResizable(false);
								choixDifficulteDialog.addWindowListener(new WindowAdapter() {
									public void windowClosing(WindowEvent evt) {
										choixDifficulteDialog.dispose();
							        }
							    });
								JPanel panel = new JPanel(new BorderLayout());
								panel.setPreferredSize(new Dimension(150, 75));
								JButton commencer = new JButton("Commencer");
								commencer.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent ae) {
										try {
											client.getUtilisateur().commencerJeu(iud, (String) choixDifficulte.getSelectedItem());
											choixDifficulteDialog.dispose();
										} catch (RemoteException e) {
											e.printStackTrace();
										}
									}
								});
								panel.add(choixDifficulte, BorderLayout.CENTER);
								panel.add(commencer, BorderLayout.SOUTH);
								choixDifficulteDialog.add(panel);
								choixDifficulteDialog.pack();
								choixDifficulteDialog.setAlwaysOnTop(true);
								choixDifficulteDialog.setVisible(true);
							}

						});
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

