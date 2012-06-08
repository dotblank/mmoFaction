package mmo.faction.land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import mmo.faction.land.RegionGenerationException.Reason;

import org.bukkit.World;
import org.bukkit.block.Biome;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

public class RegionBiomeGenerator extends Thread {
	public interface callback<K> {
		public void onGeneration(Polygon p, K data);
	}
	private enum status {
		scanned,
		pending
	}
	private int centerx;
	private int centerz;
	private World world;
	private callback callbk;
	private Object data;;
	public RegionBiomeGenerator (int x, int z, World w, callback c, Object data) {
		callbk = c;
		this.data = data;
		centerx = x;
		centerz = z;
		world = w;
	}
	@SuppressWarnings("unchecked")
	public void run() {
		Polygon p = null;
		try {
			p = generateRegion(world,centerx,centerz);

			if(callbk != null) {
				callbk.onGeneration(p, data);
			}
		} catch (RegionGenerationException e) {
			e.printStackTrace();
		}
	}
	public static Polygon generateRegion(World w, int wx, int wz) throws RegionGenerationException {
		GeometryFactory gFactory = new GeometryFactory();
		
		HashMap<Coordinate, status> scanner = new HashMap<Coordinate,status>();
		Biome biome = null;
		
		scanner.put(new Coordinate(wx,wz), status.pending);
		
		HashMap<Coordinate,List<Coordinate>> borderpoints = new HashMap<Coordinate,List<Coordinate>>();
		
		while(scanner.containsValue(status.pending)) {
			HashMap<Coordinate, status> newlist = new HashMap<Coordinate,status>();
			for(Entry<Coordinate, status> entry : scanner.entrySet()) {
				if(entry.getValue() == status.pending) {
					int x = (int) entry.getKey().x;
					int y = (int) entry.getKey().y;
					if(biome == null) {
						biome = w.getBiome(x, y);
						if(biome == Biome.OCEAN) {
							break;
						}
					}
					if(biome == w.getBiome(x, y)) {
						//scan up
						double scan_x;
						double scan_y;
						
						for(scan_x = x-1; scan_x-x < 2; scan_x++)
						for(scan_y = y-1; scan_y-y < 2; scan_y++) {
							if(scan_x != x && scan_y != y) {
								continue;
							}
							if(w.getBiome((int)scan_x, (int)scan_y) == biome){
								if(scanner.get(new Coordinate(scan_x,scan_y)) == null) {
									newlist.put(new Coordinate(scan_x,scan_y), status.pending);
								}
							} else {
								Coordinate point1 = null;
								Coordinate point2 = null;
								if(scan_x-x == 0 && scan_y-y > 0) {
									point1 = new Coordinate(x+0.5,y+.5);
									point2 = new Coordinate(x-0.5,y+.5);
								}
								if(scan_x-x == 0 && scan_y-y < 0) {
									point1 = new Coordinate(x+0.5,y-.5);
									point2 = new Coordinate(x-0.5,y-.5);
								}
								if(scan_x-x > 0 && scan_y-y == 0) {
									point1 = new Coordinate(x+0.5,y+.5);
									point2 = new Coordinate(x+0.5,y-.5);
								}
								if(scan_x-x < 0 && scan_y-y == 0) {
									point1 = new Coordinate(x-0.5,y+.5);
									point2 = new Coordinate(x-0.5,y-.5);
								}
								List<Coordinate> line1 = borderpoints.get(point1);
								List<Coordinate> line2 = borderpoints.get(point2);
								
								if(line1 == null && line2 == null) {
									ArrayList<Coordinate> line = new ArrayList<Coordinate>();
									line.add(point1);
									line.add(point2);
									borderpoints.put(point1, line);
									borderpoints.put(point2, line);
								}
								if(line1 != null && line2 == null) {
									if(line1.indexOf(point1) == line1.size() -1) //Prepend the point
									{
										line1.add(point2);
									} else {
										line1.add(0,point2);
									}
									//Update reference
									borderpoints.put(point2, line1);
								}
								if(line2 != null && line1 == null) {
									if(line2.indexOf(point2) == line2.size() -1) //Prepend the point
									{
										line2.add(point1);
									} else {
										line2.add(0,point1);
									}
									//Update reference
									borderpoints.put(point1, line2);
								}
								if(line2 != null && line1 != null) {
									if(line1 == line2) {
										//line1.add(line1.get(0));
										continue;
									}
									if(line2.indexOf(point2) == 0 && line1.indexOf(point1) == line1.size() -1 ) {
										for(int i = 0; i < line2.size(); i++) {
											line1.add(line2.get(i));
										}
										for(Coordinate l : line2) {
											borderpoints.put(l, line1);
										}
										line2.clear();
									} else if(line1.indexOf(point1) == 0 && line2.indexOf(point2) == line2.size() -1 ) {
										for(int i = 0; i < line1.size(); i++) {
											line2.add(line1.get(i));
										}
										for(Coordinate l : line1) {
											borderpoints.put(l, line2);
										}
										line1.clear();
									} else {
										if(line1.indexOf(point1) == line1.size() -1) {
											for(int i = line2.size()-1; i >= 0; i--){
												line1.add(line2.get(i));
												borderpoints.put(line2.get(i),line1);
											}
											line2.clear();
										} else {
											for(int i = 0; i < line2.size(); i ++) {
												line1.add(0, line2.get(i));
												borderpoints.put(line2.get(i),line1);
											}
											line2.clear();
										}
									}
								} 
						    }
						}
					}
					entry.setValue(status.scanned);
				}
			}
			scanner.putAll(newlist);
		}
		HashSet<List<Coordinate>> f = new HashSet<List<Coordinate>>();
		for(List<Coordinate> c : borderpoints.values()){
			
			if(f.contains(c)) {
				continue;
			}
			f.add(c);
			
			for(List<Coordinate> cs : borderpoints.values()) {
				if(cs == c) {
					continue;
				}
				for(Coordinate p : c) {
					if(cs.contains(p)) {
						throw new RegionGenerationException(Reason.FAILURE);
					}
				}
			}
		}
		
		LinearRing largest = null;
		LinearRing[] rings = new LinearRing[borderpoints.values().size() -1];
		int ringcount = 0;
		try {
			for(List<Coordinate> current: borderpoints.values()) {
				current.add(current.get(0));
				LinearRing ring = gFactory.createLinearRing(current.toArray(new Coordinate[0]));
				ring = (LinearRing) TopologyPreservingSimplifier.simplify(ring, 0);
				if(largest == null || ring.getArea() > largest.getArea()) {
					if(largest != null) {
						rings[ringcount] = largest;
						ringcount++;
					}
					largest = ring;
				} else {
					rings[ringcount] = ring;
					ringcount++;
				}
			}

			return gFactory.createPolygon(largest,rings);
		} catch (Exception e) {
			throw new RegionGenerationException(Reason.INVALID);
		}
	}
}

