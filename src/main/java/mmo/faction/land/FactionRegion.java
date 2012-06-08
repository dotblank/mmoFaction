package mmo.faction.land;

import java.util.HashSet;

import com.vividsolutions.jts.geom.Polygon;

import mmo.faction.Unit;

public class FactionRegion implements Region {
	private Unit owner;
	private String name;
	public HashSet<Polygon> areas = new HashSet<Polygon>();

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public Unit getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
