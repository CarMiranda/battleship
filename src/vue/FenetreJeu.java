package vue;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import rmi.Client.Jeu;
import rmi.Serveur.Coordonnees;
import rmi.Serveur.TypesBateau;

public class FenetreJeu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/*Cette methode permet de redimensionner une image*/
	private static ImageIcon resize(ImageIcon imageIcon){
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(70, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return  (new ImageIcon(newimg));  // transform it back
	}
	
	private final static Font POLICE_TITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);

	private Jeu leJeu;
	private GrilleJoueur grilleJoueur;
	private GrilleEnnemieIHM grilleEnnemie;
	
	public FenetreJeu(Jeu jeu) {
		super("Bataille Navale - Jeu contre " + jeu.getNomAdversaire());
		this.leJeu = jeu;
		int nbTypesDeBateaux = 6; 
		
		this.setSize(getMaximumSize());
		JPanel tableauxPanel = new JPanel();
		tableauxPanel.setLayout(new FlowLayout());
		
		//Coté gauche de l'écran qui représente le côté du Joueur. Seulement le Nord, Center et Sud sont utilisés
		//On met un texte dans le Nord
		  
		JPanel cote_Joueur = new JPanel();
		cote_Joueur.setLayout(new BorderLayout());
		cote_Joueur.setBorder(new MatteBorder(5,5,5,5,Color.GREEN.darker()));
		cote_Joueur.setSize(50,50);
		JLabel label1 = new JLabel("Votre Flotte", SwingConstants.CENTER);
		label1.setFont(POLICE_TITRE);
		label1.setVerticalAlignment(JLabel.CENTER);
		cote_Joueur.add(label1,BorderLayout.NORTH);
		
		//On créé un nouveau panel afin de pouvoir mettre un texte ainsi qu'un bouton dans le Sud
		JPanel sud = new JPanel();
		sud.setLayout(new FlowLayout());
		sud.add(new JLabel("Il vous reste " + leJeu.getNbBateaux(true) + " bateaux"));
		JButton abandonner = new JButton("Abandonner");
		abandonner.setActionCommand("abandonner");
		abandonner.addActionListener(this);
		sud.add(abandonner);
		cote_Joueur.add(sud, BorderLayout.SOUTH);
		
		
		//Chargement des images
		URL url = ClassLoader.getSystemResource("carrier.png");
		ImageIcon pAvionsImag = resize(new ImageIcon(url));
		
		url = ClassLoader.getSystemResource("submarine.gif");
		ImageIcon sousMarinImag = resize(new ImageIcon(url));
		
		url = ClassLoader.getSystemResource("battleship.jpg");
		ImageIcon croiseurImag = resize(new ImageIcon(url));
		
		url = ClassLoader.getSystemResource("destroyer.png");
		ImageIcon torpilleurImag = resize(new ImageIcon(url));
		
		url = ClassLoader.getSystemResource("battleship2.jpg");
		ImageIcon contreTorpImag = resize(new ImageIcon(url));
		
		url = ClassLoader.getSystemResource("cruiser.png");
		ImageIcon cuirasseImag = resize(new ImageIcon(url));
		
		//Creation d'un pannel pour affiché la flotte du jouer
		JPanel flotte = new JPanel();
		flotte.setLayout(new GridLayout(nbTypesDeBateaux,2));
		flotte.add(new JLabel(pAvionsImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.PORTEAVIONS.getNom(), false)));
		flotte.add(new JLabel(sousMarinImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.SOUSMARIN.getNom(), false)));
		flotte.add(new JLabel(croiseurImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.CROISEUR.getNom(), false)));
		flotte.add(new JLabel(torpilleurImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.TORPILLEUR.getNom(), false)));
		flotte.add(new JLabel(contreTorpImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.CONTRETORPILLEUR.getNom(), false)));
		flotte.add(new JLabel(cuirasseImag));
		flotte.add(new JLabel("x" + leJeu.getNbBateaux(TypesBateau.CUIRASSE.getNom(), false)));
		
		//Creation d'un pannel pour afficher la flotte ennemie
		JPanel flotteEnnemie = new JPanel();
		flotteEnnemie.setLayout(new GridLayout(nbTypesDeBateaux, 2));
		flotteEnnemie.add(new JLabel(pAvionsImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.PORTEAVIONS.getNom(), false)));
		flotteEnnemie.add(new JLabel(sousMarinImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.SOUSMARIN.getNom(), false)));
		flotteEnnemie.add(new JLabel(croiseurImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.CROISEUR.getNom(), false)));
		flotteEnnemie.add(new JLabel(torpilleurImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.TORPILLEUR.getNom(), false)));
		flotteEnnemie.add(new JLabel(contreTorpImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.CONTRETORPILLEUR.getNom(), false)));
		flotteEnnemie.add(new JLabel(cuirasseImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getNbBateauxAdversaire(TypesBateau.CUIRASSE.getNom(), false)));
		
		//Création de la grille qui aura pour but de représenter les cases de la bataille navale
		grilleJoueur = new GrilleJoueur(leJeu.getHauteurCarte(), leJeu.getLargeurCarte(), leJeu);
		cote_Joueur.add(grilleJoueur, BorderLayout.CENTER);

		//Côté droit de l'écran qui représente le côté où il peut voir les information qu'il a sur son ennemi. Très similaire au côté gauche.
		JPanel cote_Ennemie = new JPanel();
		cote_Ennemie.setLayout(new BorderLayout());
		cote_Ennemie.setBorder(new MatteBorder(5, 5, 5, 5, Color.GREEN.darker()));
		cote_Ennemie.setSize(50, 50);
		JLabel label2 = new JLabel("Flotte Ennemie", SwingConstants.CENTER);
		label2.setFont(POLICE_TITRE);
		label2.setVerticalAlignment(JLabel.CENTER);
		cote_Ennemie.add(label2, BorderLayout.NORTH);
		
		/*Création d'un panel south pour pouvoir mettre le nombre de bateaux qu'il reste à couler 
		 * ainsi qu'un bouton afin de valider la commande d'un tir */
		JPanel South = new JPanel();
		South.setLayout(new FlowLayout());
		South.add(new JLabel("Il vous reste " + leJeu.getNbBateauxAdversaire(true) + " bateaux ennemis à couler"));
		JButton tirer = new JButton("TIRER");
		tirer.setActionCommand("tirer");
		tirer.addActionListener(this);
		South.add(tirer);
		cote_Ennemie.add(South,BorderLayout.SOUTH);
		
		//De la meme manière que pour le joueur nous allons mettre en place la carte de l'ennemie
		grilleEnnemie = new GrilleEnnemieIHM(leJeu.getHauteurCarte(), leJeu.getLargeurCarte());
		cote_Ennemie.add(grilleEnnemie,BorderLayout.CENTER);
		
		//On rajoutte tous les panels dasn le pannel principal
		tableauxPanel.add(flotte);
		tableauxPanel.add(cote_Joueur);
		tableauxPanel.add(cote_Ennemie);
		tableauxPanel.add(flotteEnnemie);
				
		this.setAlwaysOnTop(true);
		this.add(tableauxPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setVisible(true);
	}
	
	public void commencerPlacementFlotte() {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "A vous de placer votre flotte!");
			}
			
		});	
		grilleJoueur.commencerPlacementFlotte();
	}

	public void setBateauAPlacer(int taille, String nomBateau) {
		grilleJoueur.setBateauAPlacer(taille, nomBateau);
	}
	
	public void finirPlacementFlotte() {
		grilleJoueur.finirPlacementFlotte();
	}
	
	boolean placementFlotte;
	
	public boolean placementFlotte() {
		return placementFlotte;
	}

	public void informerAttaque(Coordonnees coordonneesAttaquees) {
		grilleJoueur.attaquer(coordonneesAttaquees);
	}

	public void informerDefaite(final boolean parForfait) {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "Défaite!" + (parForfait ? " Vous avez forfaité." : ""));
			}
			
		});		
	}

	public void informerVictoire(final boolean parForfait) {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "Victoire!" + (parForfait ? " Votre adversaire a forfaité." : ""));
			}
			
		});
		
	}

	public void informerTour() {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "A votre tour!");
			}
			
		});
	}

	public void informerDebutJeu(final String nomJoueur) {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "Le jeu commence! Tour à " + nomJoueur);
			}
			
		});		
	}

	public void informerPlacementFlotte() {
		final JFrame fj = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(fj, "Votre adversaire place sa flotte.");
			}
			
		});	
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("abandonner")) {
			System.out.println("Abandon...");
		} else if (ae.getActionCommand().equals("tirer")) {
			System.out.println("Attaque d'un carreau en cours.");
			grilleEnnemie.colorierAttaque(leJeu.attaquer(grilleEnnemie.getCoordonneesAAttaquer()));
			
		}
	}
		
}

	

