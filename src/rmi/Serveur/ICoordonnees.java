package rmi.Serveur;

import java.io.Serializable;
/**
 * Cette interface représente une paire de coordonnées.
 * @author Cyril ANTOUN
 *
 */
public interface ICoordonnees extends Serializable, Cloneable {
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
}
