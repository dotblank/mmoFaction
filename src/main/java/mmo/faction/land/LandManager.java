package mmo.faction.land;

import org.bukkit.World;

import com.vividsolutions.jts.geom.Polygon;

public class LandManager implements RegionBiomeGenerator.callback<Object>{
	private World world;

	public LandManager(World w) {
		this.world = w;
	}
	
	public void generateRegion(int x, int z) {
		RegionBiomeGenerator gen = new RegionBiomeGenerator(x,z,world,this,null);
		gen.start();
	}

	@Override
	public void onGeneration(Polygon p, Object data) {
		
	}

}
