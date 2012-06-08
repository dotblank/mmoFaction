package mmo.faction.land;

import mmo.faction.Unit;

public interface Region {
	public void setOwner(Unit owner);

	public Unit getOwner() ;
	
	public void setName(String name) ;

	public String getName() ;
}
