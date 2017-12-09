/**
 * 
 */
package vue;

/**
 * @author Jorge OCHOA
 *
 */

import modele.*;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;




@SuppressWarnings("serial")
public class FenetreJeu extends javax.swing.JFrame {
	
	/*Cette methode permet de redimensionner une image*/
	private static ImageIcon resize(ImageIcon imageIcon){
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(70, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return  (new ImageIcon(newimg));  // transform it back
	}
	
private final static Font POLICE_TITRE = new Font("DevanagariMT-Bold",Font.ITALIC,20);

	//Jeu leJeu;
	int nmbre_bateau =3;
	int taille_Horizontale = 10;
	int taille_Verticale = 10;	
	int nbTypesDeBateaux = 6;
	
	//public FenetreJeu(Jeu jeu) {
	public FenetreJeu(){
	super("Bataille Navale");
		//this.leJeu = jeu;
		//int taille_Verticale = leJeu.DIFFICULTE.HAUTEUR;
		 //int taille_Horizontale = leJeu.DIFFICULTE.LARGEUR;
		 
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
		sud.add(new JLabel("Il vous reste " + nmbre_bateau + " bateaux"));
		sud.add(new JButton("Abandonner"));
		cote_Joueur.add(sud, BorderLayout.SOUTH);
		
		
		//Chargement des images
		ImageIcon pAvionsImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/carrier.png"));
		ImageIcon sousMarinImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/submarine.gif"));
		ImageIcon croiseurImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/battleship.jpg"));
		ImageIcon torpilleurImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/destroyer.png"));
		ImageIcon contreTorpImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/battleship2.jpg"));
		ImageIcon cuirasseImag = resize(new ImageIcon("/home/jorge/Bureau/GM4/POA/projet_java/cahier_de_charges/images_bateaux/cruiser.png"));
		
		//Creation d'un pannel pour affiché la flotte du jouer
		JPanel flotte = new JPanel();
		flotte.setLayout(new GridLayout(this.nbTypesDeBateaux,2));
		flotte.add(new JLabel(pAvionsImag));
		flotte.add(new JLabel("x1"));
		flotte.add(new JLabel(sousMarinImag));
		flotte.add(new JLabel("x2"));
		flotte.add(new JLabel(croiseurImag));
		flotte.add(new JLabel("x3"));
		flotte.add(new JLabel(torpilleurImag));
		flotte.add(new JLabel("x4"));
		flotte.add(new JLabel(contreTorpImag));
		flotte.add(new JLabel("x5"));
		flotte.add(new JLabel(cuirasseImag));
		flotte.add(new JLabel("x6"));
		
		//Creation d'un pannel pour afficher la flotte ennemie
		JPanel flotteEnnemie = new JPanel();
		flotteEnnemie.setLayout(new GridLayout(this.nbTypesDeBateaux,2));
		flotteEnnemie.add(new JLabel(pAvionsImag));
		flotteEnnemie.add(new JLabel("x1"));
		flotteEnnemie.add(new JLabel(sousMarinImag));
		flotteEnnemie.add(new JLabel("x2"));
		flotteEnnemie.add(new JLabel(croiseurImag));
		flotteEnnemie.add(new JLabel("x3"));
		flotteEnnemie.add(new JLabel(torpilleurImag));
		flotteEnnemie.add(new JLabel("x4"));
		flotteEnnemie.add(new JLabel(contreTorpImag));
		flotteEnnemie.add(new JLabel("x5"));
		flotteEnnemie.add(new JLabel(cuirasseImag));
		flotteEnnemie.add(new JLabel("x6"));
		
		//Création d'une grille de boutons qui aura pour but de représenter les cases de la bataille navale !
		JPanel carte_Joueur = new JPanel();
		carte_Joueur.setLayout(new GridLayout(taille_Verticale, taille_Horizontale));	
		for(int i = 1; i <= taille_Verticale; i++)
		{
			for(int j = 1; j <= taille_Horizontale; j++)
			{
				carte_Joueur.add(new JButton("X"));
				//Carte_Joueur.add(new JButton(water));
			}
		}
		//Finalement on rajoute ce grid au centre du panel Joueur
		cote_Joueur.add(carte_Joueur,BorderLayout.CENTER);
		

		//Côté droit de l'écran qui représente le côté où il peut voir les information qu'il a sur son ennemi. Très similaire au côté gauche.
		JPanel cote_Ennemie = new JPanel();
		cote_Ennemie.setLayout(new BorderLayout());
		cote_Ennemie.setBorder(new MatteBorder(5,5,5,5,Color.GREEN.darker()));
		cote_Ennemie.setSize(50,50);
		JLabel label2 = new JLabel("Flotte Ennemie", SwingConstants.CENTER);
		label2.setFont(POLICE_TITRE);
		label2.setVerticalAlignment(JLabel.CENTER);
		cote_Ennemie.add(label2,BorderLayout.NORTH);
		//Création d'un panel south pour pouvoir mettre le nombre de bateaux qu'il reste à couler ainsi qu'un bouton afin de valider la commande d'un tir !
		JPanel South = new JPanel();
		South.setLayout(new FlowLayout());
		South.add(new JLabel("Il vous reste " + nmbre_bateau + " bateaux ennemie à couler"));
		South.add(new JButton("TIRER"));
		cote_Ennemie.add(South,BorderLayout.SOUTH);
		//De la meme manière que pour le joueur nous allons mettre en place la carte de l'ennemie
		JPanel carte_Ennemie = new JPanel();
		carte_Ennemie.setLayout(new GridLayout(taille_Verticale, taille_Horizontale));	
		for(int i = 1; i <= taille_Verticale; i++)
		{
			for(int j = 1; j <= taille_Horizontale; j++)
			{
				carte_Ennemie.add(new JButton("X"));
				//Carte_Joueur.add(new JButton(water));
			}
		}
		//Finalement on rajoute ce grid au centre du panel Joueur
		cote_Ennemie.add(carte_Ennemie,BorderLayout.CENTER);

		//On rahoutte tous les panels dasn le pannel principal
		tableauxPanel.add(flotte);
		tableauxPanel.add(cote_Joueur);
		tableauxPanel.add(cote_Ennemie);
		tableauxPanel.add(flotteEnnemie);
		
		this.setAlwaysOnTop(true);
		this.setContentPane(tableauxPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setVisible(true);
	}

		
}

	

