package mmo.faction;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import mmo.Core.GroupAPI.Group;
import mmo.Core.GroupAPI.MMOGroupType;

public class BasicEntity implements Group {
	private HashSet<Group> members;
	private HashSet<Group> invited;
	private HashSet<Group> parents;
	private HashSet<Group> excluded;
	private JavaPlugin plugin;
	private String ID;
	BasicEntity(JavaPlugin p)
	{
		plugin = p;
	}

	@Override
	public boolean accept(Group group) {
		if(!canAccept(group))
			return false;
		FactionEvent event = new FactionEvent(MMOGroupType.ACCEPT,group,this);
		plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled())
		{
			invited.remove(group);
			members.add(group);
		}
		return false;
	}

	@Override
	public boolean canAccept(Group group) {
		if(invited.contains(group))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canDecline(Group group) {
		return false;
	}

	@Override
	public boolean canDemote(Group leader, Group group) {
		return false;
	}

	@Override
	public boolean canInvite(Group inviter, Group invitee) {
		if(!this.exclude().contains(invitee))
			return true;
		return false;
	}

	@Override
	public boolean canJoin(Group group) {
		if(!this.exclude().contains(group))
			return true;
		return false;
	}

	@Override
	public boolean canKick(Group leader, Group group) {
		return false;
	}

	@Override
	public boolean canLeave(Group group) {
		return false;
	}

	@Override
	public boolean canPromote(Group leader, Group group) {
		return false;
	}

	@Override
	public boolean decline(Group group) {
		return false;
	}

	@Override
	public boolean demote(Group leader, Group group) {
		return false;
	}

	@Override
	public Collection<Group> exclude() {
		HashSet<Group> Recursive = new HashSet<Group>();
		for(Group member : members)
		{
			Recursive.addAll(member.exclude());
		}
		Recursive.addAll(excluded);
		Recursive.add(this);
		return Recursive;
	}

	@Override
	public Collection<Group> find(Group group) {
		//plugin.get
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> find(Group group, Group parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> find(Group group, Group parent, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group findGroup(String id) {
		for(Group member : members)
		{
			if(member.getId().equals(id))
				return member;
			Group found = member.findGroup(member.getId() + "." + id);
			if(found != null)
				return found;
		}
		return null;
	}

	@Override
	public boolean getBoolean(Group group, String key, boolean def) {
		return def;
	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> getChildren() {
		return this.members;
	}

	@Override
	public double getDouble(Group group, String key, double def) {
		return def;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public int getInt(Group group, String key, int def) {
		// TODO Auto-generated method stub
		return def;
	}

	@Override
	public Location getLocation(Group group, String key, Location def) {
		// TODO Auto-generated method stub
		return def;
	}

	@Override
	public Collection<Group> getMembers() {
		// TODO Auto-generated method stub
		return this.members;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> getOwnMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> getParents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(Group group, String key, String def) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getStringList(Group group, String key,
			Collection<String> def) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invite(Group inviter, Group invitee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChild(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMember(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOwnMember(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParent(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean join(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean kick(Group leader, Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leave(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean promote(Group leader, Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Group> require() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBoolean(Group group, String key, boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDouble(Group group, String key, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInt(Group group, String key, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(Group group, String key, Location value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setString(Group group, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStringList(Group group, String key, Collection<String> value) {
		// TODO Auto-generated method stub
		
	}
	
}
