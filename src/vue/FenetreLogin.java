package vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import rmi.Client.Utilisateur;
import rmi.Serveur.IUtilisateurDistant;

public class FenetreLogin extends JFrame implements ActionListener {

	private final JLabel usernameLabel;
	private final JTextField usernameValue;
	private final JLabel passwordLabel;
	private final JPasswordField passwordValue;
	private final JLabel errorLabel;
	private final JButton inscription;
	private final JButton authentification;
	private final Client client;
	
	private static final long serialVersionUID = 1L;
	
	public FenetreLogin(Client client) {
		super("Bataille Navale");
		
		this.setPreferredSize(new Dimension(350, 125));
		
		this.client = client;
		
		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		usernameLabel = new JLabel("Entrez votre login :");
		usernameValue = new JTextField(10);
		passwordLabel = new JLabel("Entrez votre mot de passe :");
		passwordValue = new JPasswordField(10);
		errorLabel = new JLabel("");
		inscription = new JButton ("Créer un compte");
		authentification = new JButton(" OK ");
		
		inscription.addActionListener(this);
		inscription.setActionCommand("inscription");
		authentification.addActionListener(this);
		authentification.setActionCommand("authentification");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(usernameLabel, c);
		c.gridx = 1;
		mainPanel.add(usernameValue, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(passwordLabel, c);
		c.gridx = 1;
		mainPanel.add(passwordValue, c);
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(inscription, c);
		c.gridx = 1;
		mainPanel.add(authentification, c);
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(errorLabel, c);
		
		this.setAlwaysOnTop(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
	}
	
	public void promptError() {
		errorLabel.setText("Erreur d'authentification.");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		
			Utilisateur utilisateur;
			IUtilisateurDistant utilisateurDistant = null;
			try {
				utilisateur = new Utilisateur(usernameValue.getText(), client);
				client.setUtilisateur(utilisateur);
				if (action.equals("inscription")) {
					utilisateurDistant = utilisateur.inscription(new String(passwordValue.getPassword()));
				} else if (action.equals("authentification")) {
					utilisateurDistant = utilisateur.authentification(new String(passwordValue.getPassword()));
				}
				if (utilisateurDistant != null) client.accueil();
				else throw new IllegalArgumentException();
			} catch (RemoteException e) {
				e.printStackTrace();
				promptError();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				promptError();
			}
	}

}
