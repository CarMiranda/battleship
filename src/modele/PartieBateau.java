package modele;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/

public class PartieBateau {
	
	//attributs
	private boolean touche;
	private final CarreauCarte sq;
	
	//constructeur 
	public PartieBateau(CarreauCarte sq0) throws CarreauUtiliseException{
		this.sq = sq0;
		this.touche = false;
		this.sq.lierPartieBateau(this);	
	}
	
	//accsseurs
	
	public boolean estTouche(){
		return this.touche;
	};
	
	public void attaquer(){
		this.touche = true;
	}
	
	public CarreauCarte getCarreauCarte(){
		return this.sq;
	}
	
	/*public void setCarreauCarte(CarreauCarte cc){
		this.sq = cc;
	}*/
	

}
