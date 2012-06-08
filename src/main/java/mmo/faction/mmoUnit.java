package mmo.faction;

import org.bukkit.entity.Player;

public abstract class mmoUnit{
	public enum mmoUnitType {
		Player,
		Group,
		NPC,
		Property,
		ANY
	}
	/**
	 * Nest a unit
	 * @param mmoUnit The unit to add
	 * @return If successful
	 */
	public abstract boolean add(mmoUnit unit);
	
	/**
	 * make this unit a member of the specified unit
	 * @param mmoUnit The unit to become a member of
	 * @return If successful
	 */
	public abstract boolean addParent(mmoUnit unit);
	
	/**
	 * Remove this member or parent
	 * @param mmoUnit The unit to remove
	 * @return If successful
	 */
	public abstract boolean remove(mmoUnit unit);
	
	/**
	 * Retrieve the specific mmoUnit
	 * @param mmoUnit The unit to find
	 * @return Returns the found unit's path, otherwise return null
	 */
	public abstract String find(mmoUnit unit);
	
	/**
	 * Retrieve the specific mmoUnit
	 * @param mmoUnit The unit to find
	 * @return Returns the found unit's path, otherwise return null
	 */
	public abstract mmoUnit find(String path, mmoUnitType type);
	
	public abstract String getId();
	
	public abstract mmoUnitType getType();
	
	public int hashCode()
	{
		return this.getId().hashCode();
	}
}
