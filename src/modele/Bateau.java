package modele;

import java.util.*;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/


public class Bateau implements Iterable<PartieBateau>{
	
	//attributs
	
	private final int taille;
	private final String nom;
	private boolean estCoule;
	private List<PartieBateau> bateau;

	
	//constructeur
	
	public Bateau(TypeBateau type){
		
		this.taille = type.getTaille();
		this.nom = type.getNom();
		this.estCoule = false;
		this.bateau = new ArrayList<PartieBateau>(this.taille);
	};
	
	//accesseurs
	
	public int getTaille(){
		return this.taille;
	}
	
	public boolean getEstCoule(){
		return this.estCoule;
	};
	
	public List<PartieBateau> getBateau(){
		return this.bateau;		
	};
	
	public String getNom(){
		return this.nom;
	}
	
	//Methodes
	
	public Iterator<PartieBateau> iterator(){
		return this.bateau.iterator();
	}
	
	/*this method returns true if there is a ship's part at the position pos */
	public boolean estTouche(CarreauCarte pos){
		while (this.iterator().hasNext()){
			if (this.iterator().next().getCarreauCarte().equals(pos)) {
				return true;
			}
		}
		return false;
	};
	
	/*this method returns true if all the parts of the ship are touched*/
	public boolean estCoule(){
		while (this.iterator().hasNext()){
			if (! this.iterator().next().estTouche())
				return false;
		}
		this.estCoule = true;
		return true;
	}
	
	/*this procedure sets the ship's coordinates*/
	public void placer(ArrayList<CarreauCarte> l) throws CarreauUtiliseException{
		
		for(CarreauCarte sq: l){
			if(sq.contientBateau()) throw new CarreauUtiliseException();
		}
		if(!CarreauCarte.aligne(l));
		for(CarreauCarte sq: l){
			this.bateau.add(new PartieBateau(sq));
		}
	}
}