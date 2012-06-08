package mmo.faction;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import mmo.Core.MMOPlugin;
import mmo.Core.GroupAPI.Group;
import mmo.faction.land.LandManager;
import mmo.faction.land.Region;

public class FactionPlugin extends MMOPlugin implements Listener {
	public Group rootGroup;
	HashMap<World,LandManager> landmanagers = new HashMap<World,LandManager>();
	
	public void onEnable() {
		
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("gen")) {
			Player p = (Player) sender;
			Location l = p.getLocation();
			LandManager lm = this.getOrMakeLM(p.getLocation().getWorld());
			Region r = lm.getRegionAt(l.getBlockX(), l.getBlockZ());
			if(r == null) {
				p.sendMessage("generating region at " + p.getLocation());
				lm.generateRegion(l.getBlockX(),l.getBlockZ());
			} else {
				p.sendMessage("Region has already been generated for: " + r.getName());
			}
		}
		
		return false;
	}
	private LandManager getOrMakeLM(World w) {
		if(!landmanagers.containsKey(w)) {
			landmanagers.put(w, new LandManager(w));
		}
		return landmanagers.get(w);
	}
}
