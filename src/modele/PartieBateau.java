package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

public class PartieBateau {
	
	//attributs
	private boolean touche;
	private Coordonnes position;
	
	//constructeur 
	public PartieBateau(Coordonnes pos){
		this.position = pos;
		this.position.setPartieBateau(this);
		this.touche = false;
	}
	
	//accsseurs
	
	public boolean getTouche(){
		return this.touche;
	};
	
	public void setTouche(boolean t){
		this.touche = t;
	}
	
	public Coordonnes getPosition(){
		return this.position;
	}
	
}
