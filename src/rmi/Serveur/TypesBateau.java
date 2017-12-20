package rmi.Serveur;

/**
 * Cette énumération représente les différents types de bateaux du jeu ainsi que leurs propriétés.
 * 
 * @author Carlos MIRANDA, Cyril ANTOUN
 */
public enum TypesBateau {
	
	PORTEAVIONS(5, "Porte-avions"),
	CROISEUR(4, "Croiseur"),
	CONTRETORPILLEUR(3, "Contre-torpilleur"),
	SOUSMARIN(3, "Sous-marin"),
	TORPILLEUR(2, "Torpilleur"),
	CUIRASSE(4, "Cuirasse");
	
	private int taille;
	private String nom;
	
	TypesBateau(int taille, String nom){
		this.taille = taille;
		this.nom = nom;
	}
	
	public int getTaille(){
		return taille;
	}
	
	public String getNom(){
		return nom;
	}
}
