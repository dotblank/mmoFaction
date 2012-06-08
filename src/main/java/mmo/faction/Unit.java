package mmo.faction;

import java.util.EnumSet;

public interface Unit {
	public static enum Capabilities {
		Parent,   //Able to be a parent
		Child,    //Able to be a child
		Searchable, //Can be searched or queried
		ExclusiveOwner //Sets member child's parents
	}
	public EnumSet<Capabilities> getCapabilities();
	public Unit[] getChildren();
	public void addChild(Unit... unit);
	public void removeChild(Unit... unit);
	public void removeChildren();
	public boolean containsUnit(Unit u, int depth);
	public Unit getParent();
	public void setParent(Unit u);
	public <T extends Unit> T[] findUnits(Class<T> u, int depth);
	public String getName();
}
