package vue;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import rmi.Client.IJeu;
import rmi.Serveur.TypesBateau;

public class FenetreJeu extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	/*Cette methode permet de redimensionner une image*/
	private static ImageIcon resize(ImageIcon imageIcon){
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(70, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return  (new ImageIcon(newimg));  // transform it back
	}
	
	private final static Font POLICE_TITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);

	IJeu leJeu;	
	
	public FenetreJeu(IJeu jeu) throws RemoteException {
		super("Bataille Navale");
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
		sud.add(new JLabel("Il vous reste " + leJeu.getJoueur().getFlotte().getNbBateauxNonCoules() + " bateaux"));
		sud.add(new JButton("Abandonner"));
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
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.PORTEAVIONS)));
		flotte.add(new JLabel(sousMarinImag));
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.SOUSMARIN)));
		flotte.add(new JLabel(croiseurImag));
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.CROISEUR)));
		flotte.add(new JLabel(torpilleurImag));
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.TORPILLEUR)));
		flotte.add(new JLabel(contreTorpImag));
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.CONTRETORPILLEUR)));
		flotte.add(new JLabel(cuirasseImag));
		flotte.add(new JLabel("x" + leJeu.getJoueur().getFlotte().getNbBateaux(TypesBateau.CUIRASSE)));
		
		//Creation d'un pannel pour afficher la flotte ennemie
		JPanel flotteEnnemie = new JPanel();
		flotteEnnemie.setLayout(new GridLayout(nbTypesDeBateaux,2));
		flotteEnnemie.add(new JLabel(pAvionsImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.PORTEAVIONS)));
		flotteEnnemie.add(new JLabel(sousMarinImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.SOUSMARIN)));
		flotteEnnemie.add(new JLabel(croiseurImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.CROISEUR)));
		flotteEnnemie.add(new JLabel(torpilleurImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.TORPILLEUR)));
		flotteEnnemie.add(new JLabel(contreTorpImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.CONTRETORPILLEUR)));
		flotteEnnemie.add(new JLabel(cuirasseImag));
		flotteEnnemie.add(new JLabel("x"+leJeu.getAdversaire().getFlotte().getNbBateaux(TypesBateau.CUIRASSE)));
		
		//Création de la grille qui aura pour but de représenter les cases de la bataille navale
		JLayeredPane laGrille = new GrilleIHM(leJeu.getJoueur().getCarte());
		cote_Joueur.add(laGrille,BorderLayout.CENTER);

		//Côté droit de l'écran qui représente le côté où il peut voir les information qu'il a sur son ennemi. Très similaire au côté gauche.
		JPanel cote_Ennemie = new JPanel();
		cote_Ennemie.setLayout(new BorderLayout());
		cote_Ennemie.setBorder(new MatteBorder(5,5,5,5,Color.GREEN.darker()));
		cote_Ennemie.setSize(50,50);
		JLabel label2 = new JLabel("Flotte Ennemie", SwingConstants.CENTER);
		label2.setFont(POLICE_TITRE);
		label2.setVerticalAlignment(JLabel.CENTER);
		cote_Ennemie.add(label2,BorderLayout.NORTH);
		
		/*Création d'un panel south pour pouvoir mettre le nombre de bateaux qu'il reste à couler 
		 * ainsi qu'un bouton afin de valider la commande d'un tir */
		JPanel South = new JPanel();
		South.setLayout(new FlowLayout());
		South.add(new JLabel("Il vous reste " + leJeu.getAdversaire().getFlotte().getNbBateauxNonCoules() + " bateaux ennemie à couler"));
		South.add(new JButton("TIRER"));
		cote_Ennemie.add(South,BorderLayout.SOUTH);
		
		//De la meme manière que pour le joueur nous allons mettre en place la carte de l'ennemie
		//JLayeredPane grilleEnnemie = new GrilleIHM(leJeu.getAdversaire().getCarte());
		JLayeredPane grilleEnnemie = new GrilleEnnemieIHM(leJeu.getAdversaire().getCarte());
		cote_Ennemie.add(grilleEnnemie,BorderLayout.CENTER);
		
		//On rahoutte tous les panels dasn le pannel principal
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

		
}

	

