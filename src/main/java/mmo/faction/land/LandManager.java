package mmo.faction.land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.PRTree;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import de.beimax.janag.NameGenerator;

public class LandManager implements RegionBiomeGenerator.callback<FactionRegion>{
	 
	private class PolygonConverter implements MBRConverter<Polygon> {

		@Override
		public int getDimensions() {
			return 2;
		}

		@Override
		public double getMin(int axis, Polygon t) {
			switch(axis) {
			case 0:
				return t.getEnvelopeInternal().getMinX();
			case 1:
				return t.getEnvelopeInternal().getMinY();
			}
			return 0;
		}

		@Override
		public double getMax(int axis, Polygon t) {
			switch(axis) {
			case 0:
				return t.getEnvelopeInternal().getMinX();
			case 1:
				return t.getEnvelopeInternal().getMinY();
			}
			return 0;
		}
		
	}
	
	private World world;
	private GeometryFactory gfac = new GeometryFactory();
	private PRTree<Polygon> index;
	private HashMap<Polygon,Region> regions = new HashMap<Polygon,Region>();
	private NameGenerator gen = new NameGenerator("languages.txt","semantics.txt");

	public LandManager(World w) {
		this.world = w;
		index = new PRTree<Polygon>(new PolygonConverter(), 10);
		
	}
	
	public void generateRegion(int x, int z) {
		if(getRegionAt(x,z) == null) {
			return;
		}
		FactionRegion reg = new FactionRegion();
		reg.setName(gen.getRandomName("Fantasy Human", "male", 1)[0] + " " + nameBiome(world.getBiome(x, z)));
		RegionBiomeGenerator gen = new RegionBiomeGenerator(x,z,world,this,reg);
		gen.start();
	}
	public Region getRegionAt(int x, int z) {
		for(Polygon p : index.find(x,z,x,z)) {
			if(p.intersects(gfac.createPoint(new Coordinate(x,z)))) {
				return regions.get(p);
			}
		}
		return null;
	}

	@Override
	public void onGeneration(Polygon p, FactionRegion data) {
		System.out.println("Generated land: " + data.getName() + "\nsize: " + p.getArea());
		data.areas.add(p);
		regions.put(p, data);
		if(!index.isEmpty()) {
			index = new PRTree<Polygon>(new PolygonConverter(), 10);
		}
		index.load(regions.keySet());
	}
	public String nameBiome(Biome b) {
		List<String> s = new ArrayList<String>();
		switch(b) {
		case DESERT:
			s.add("desert");
			s.add("wasteland");
			break;
		case EXTREME_HILLS:
			s.add("mountains");
			s.add("range");
			s.add("peaks");
			break;
		case SEASONAL_FOREST:
			s.add("overgrowth");
			s.add("tropics");
		case FOREST:
			s.add("woods");
			s.add("forest");
			s.add("grove");
			s.add("wood");
			break;
		case HELL:
			s.add("hell");
			break;
		case ICE_DESERT:
			s.add("tundra");
			s.add("expanse");
			s.add("snow");
			s.add("sea");
			s.add("colds");
			break;
		case OCEAN:
			s.add("ocean");
			s.add("sea");
			break;
		case PLAINS:
			s.add("plains");
			s.add("field");
			s.add("grasslands");
			break;
		case RAINFOREST:
			s.add("jungle");
			s.add("morass");
			s.add("thicket");
			break;
		case RIVER:
			s.add("river");
			s.add("brook");
			s.add("run");
			s.add("rill");
			break;
		case SAVANNA:
			s.add("plains");
			s.add("field");
			s.add("pasture");
			s.add("meadow");
			break;
		case SHRUBLAND:
			s.add("bush");
			s.add("ferns");
			s.add("shruberry");
			break;
		case SKY:
			s.add("skies");
			s.add("heavens");
			break;
		case SWAMPLAND:
			s.add("bog");
			s.add("glade");
			s.add("mire");
			s.add("marsh");
			break;
		case TAIGA:
			s.add("taiga");
			break;
		case TUNDRA:
			s.add("ice desert");
			s.add("tundra");
			s.add("frost");
			break;
			default:
				s.add("land");
				s.add("hold");
				s.add("trust");
				s.add("purchase");
				s.add("reserve");
		}
		Random rand = new Random();
		if(s.size() == 0) {
			return null;
		}
		int index = rand.nextInt(s.size());
		return s.get(index);
	}
}
