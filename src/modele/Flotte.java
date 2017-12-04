package modele;

/**
 * Cette classe représente une flotte (ensemble de bateaux).
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos

import java.util.HashSet;
import java.util.Iterator;

public class Flotte implements IFlotte {
	
	private final HashSet<Bateau> flotte = new HashSet<>(TypesBateau.values().length);
	private boolean estDetruite;
	
	public Flotte() {
		for (TypesBateau s : TypesBateau.values()) {
			flotte.add(new Bateau(s));
		}
	}
	
	public boolean estDetruite() {
		if (estDetruite) return estDetruite;
		for (Bateau b : flotte) {
			if (!b.estCoule()) return false;
		}
		estDetruite = true;
		return estDetruite;
	}

	public Iterator<Bateau> iterator() {
		return flotte.iterator();
	}

}

