package modele;

import java.util.*;

/*class developed by Jorge OCHOA 
(jorge.ochoa_magana@insa-rouen.fr)*/


public class Bateau {

	public static enum TypeBateau {
		PORTEAVIONS, CROISEUR, CONTRETORPILLEUR, 
		SOUSMARIN, TORPILLEUR, CUIRASSE
		}
	
	//attributs
	
	private int taille;
	private boolean estCoule;
	private List<PartieBateau> bateau;
	private TypeBateau type;
	
	//constructeur
	
	public Bateau(TypeBateau type){
		this.type = type;
		switch(type){
		case PORTEAVIONS: 
			this.taille = 5;
			break;
		case CROISEUR: 
		case CUIRASSE: 
			this.taille = 4;
			break;
		case CONTRETORPILLEUR:
		case SOUSMARIN:
			this.taille = 3;
			break;
		case TORPILLEUR:
			this.taille = 2;
			break;
		}
		
		this.estCoule = false;
		this.bateau = new ArrayList<PartieBateau>();
		for (int i = 0; i<this.taille; i++){
			this.bateau.add(new PartieBateau(new Coordonnes(0,0,null)));
		}
		
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
	
	public TypeBateau getType(){
		return this.type;
	}
	
	//Methodes
	
	/*this method returns true if there is a ship's part at the position pos */
	public boolean estTouche(Coordonnes pos){
		Iterator<PartieBateau> bateauIterator = bateau.iterator();
		while (bateauIterator.hasNext()){
			if (bateauIterator.next().getPosition().equals(pos)) {
				return true;
			}
		}
		return false;
	};
	
	/*this method returns true if all the parts of the ship are touched*/
	public boolean estCoule(){
		Iterator<PartieBateau> bateauIterator = bateau.iterator();
		while (bateauIterator.hasNext()){
			if (! bateauIterator.next().getTouche())
				return false;
		}
		this.estCoule = true;
		return true;
	}
	
	/*this procedure sets the ship's coordinates*/
	public void placer(ArrayList<Coordonnes> l){
		Iterator<Coordonnes> coordonnesIterator = l.iterator();
		Iterator<PartieBateau> bateauIterator = bateau.iterator();
		
		if (l.size() == this.taille){
			while(coordonnesIterator.hasNext() && bateauIterator.hasNext()){
				bateauIterator.next().setPosition(coordonnesIterator.next());
			}
		}
	}
}