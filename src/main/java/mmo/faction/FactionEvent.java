package mmo.faction;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import mmo.Core.GroupAPI.Group;
import mmo.Core.GroupAPI.MMOGroupEvent;
import mmo.Core.GroupAPI.MMOGroupType;

public class FactionEvent extends Event implements MMOGroupEvent {
	private static final long serialVersionUID = 3692448881631623482L;
	private boolean cancelled;
	private Group blamer;
	private Group group;
	private MMOGroupType type;
	public FactionEvent(MMOGroupType t, Group g, Group blame)
	{
		super("mmoGroupEvent");
		type = t;
		blamer = blame;
		group = g;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public Group getBlamer() {
		return blamer;
	}

	@Override
	public Group getGroup() {
		return group;
	}
	
	@Override
	public MMOGroupType getAction() {
		return type;
	}

}
