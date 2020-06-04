package Model.StaticModel;

public class StaticModel{
	
	public boolean isBean() {
		return this instanceof Bean;
	}
	
	public boolean isPacGom() {
		return this instanceof PacGom;
	}
	
	public boolean isWall() {
		return this instanceof Wall;
	}
	public boolean isEmpty() {
		return this instanceof Empty;
	}
	public boolean isSpawn() {
		return this instanceof Spawn;
	}
}
