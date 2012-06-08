package mmo.faction.land;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import org.bukkit.World;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.PRTree;
import org.khelekore.prtree.SimpleMBR;

import com.vividsolutions.jts.geom.Polygon;

public class LandManager implements RegionBiomeGenerator.callback<Region>{
	 
	private class PolygonConverter implements MBRConverter<Polygon> {
		@Override
		public double getMinX(Polygon t) {
			return t.getEnvelopeInternal().getMinX();
		}

		@Override
		public double getMinY(Polygon t) {
			return t.getEnvelopeInternal().getMinY();
		}

		@Override
		public double getMaxX(Polygon t) {
			return t.getEnvelopeInternal().getMaxX();
		}

		@Override
		public double getMaxY(Polygon t) {
			return t.getEnvelopeInternal().getMaxY();
		}
		
	}
	
	private World world;
	private PRTree<Polygon> index;
	private HashMap<Polygon,Region> regions = new HashMap<Polygon,Region>();

	public LandManager(World w) {
		this.world = w;
		index = new PRTree<Polygon>(new PolygonConverter(), 10);
		
	}
	
	public void generateRegion(int x, int z) {
		for(Polygon p : index.find(new SimpleMBR(x,z,x,z))) {
			return;
		}
		RegionBiomeGenerator gen = new RegionBiomeGenerator(x,z,world,this,null);
		gen.start();
	}

	@Override
	public void onGeneration(Polygon p, Region data) {
		
	}

}
