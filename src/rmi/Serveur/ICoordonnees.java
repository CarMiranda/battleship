package rmi.Serveur;

import java.io.Serializable;

public interface ICoordonnees extends Serializable, Cloneable {
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
}
