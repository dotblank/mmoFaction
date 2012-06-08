package mmo.faction.land;

import com.vividsolutions.jts.geom.Polygon;

import mmo.faction.Unit;

public class FactionRegion {
	private Unit owner;
	private Polygon pgon;
	private String name;

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public Unit getOwner() {
		return owner;
	}

	public void setPgon(Polygon pgon) {
		this.pgon = pgon;
	}

	public Polygon getPgon() {
		return pgon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
