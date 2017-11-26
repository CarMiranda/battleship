package modele;

public enum TypeBateau {
	
	PORTEAVIONS(5, "Porte-avions"),
	CROISEUR(4, "Croiseur"),
	CONTRETORPILLEUR(3, "Contre-torpilleur"),
	SOUSMARIN(3, "Sous-marin"),
	TORPILLEUR(2, "Torpilleur"),
	CUIRASSE(4, "Cuirasse");
	
	private int taille;
	private String nom;
	
	TypeBateau(int t, String n){
		this.taille = t;
		this.nom = n;
	}
	
	public int getTaille(){
		return this.taille;
	}
	
	public String getNom(){
		return this.nom;
	}

}
