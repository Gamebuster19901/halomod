package com.gamebuster19901.halo.common.item.capability;

import com.gamebuster19901.halo.common.util.EasyLocalization;
import com.gamebuster19901.halo.common.util.Updateable;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public interface Weapon extends Updateable{
	double FPSToMPS = 0.3048d;
	double TPS = 20;
	
	public static interface Tag{}
	
	/**
	 * @return if this weapon can be used.
	 */
	public default boolean canFire(Entity shooter) {
		return this.getTimeUntilNextFire() <= 0;
	}
	
	/**
	 * Attempt to fire this weapon. Checks if the weapon can fire, and then fires if it can.
	 * 
	 * If automatic and can fire more than once per tick, fires as many times as possible per tick.
	 * 
	 * @return true if a shot was fired, false otherwise
	 */
	public default boolean attemptFire(Entity shooter) {
		boolean shot = false;
		byte shotsLeft = shotsPerTick();
		if(!isAutomatic()) {
			shotsLeft = 1;
		}
		while(canFire(shooter) && shotsLeft > 0) {
			fire(shooter);
			shot = true;
			shotsLeft--;
		}
		return shot;
	}
	
	/**
	 * Fires the weapon.
	 */
	public void fire(Entity shooter);
	
	/**
	 * How many shots this weapon can shoot per second
	 * 
	 * Any values returned over 2540 are effectively treated as 2540.
	 * 
	 * @return how many shots this weapon can fire per second
	 */
	public int getFireRate();
	
	public void setFireRate(int rate);
	
	/**
	 * @return the time in ticks until the weapon can fire if its rate of fire is >= 1 per tick, max 6.35 seconds
	 */
	public byte getTimeUntilNextFire();
	
	public void setTimeUntilNextFire(byte time);
	
	/**
	 * @return If this is an automatic weapon or not. I.E. will keep firing when the fire button is held.
	 */
	public boolean isAutomatic();
	
	public void setAutomatic(boolean isAutomatic);
	
	/**
	 * @return The maximum amount of bullets this gun can shoot per tick
	 * 
	 * Max of 127 to prevent lag
	 */
	public default byte shotsPerTick() {
		if(getFireRate() < 20) {
			return (byte) MathHelper.clamp(Math.floor(getFireRate() / TPS), 1, 127);
		}
		return 1;
	}
	
	/**
	 * Update the weapon, called every tick.
	 */
	public void update();
	
	/**
	 * Convert Feet per second to meters per tick
	 * @param fps feet per second
	 * @return meters per tick
	 */
	public static double FPSToMPT(double fps) {
		return fps * FPSToMPS / TPS;
	}
	
	public static ResourceLocation getCapabilityKey() {
		return EasyLocalization.getResourceLocation(Weapon.class);
	}

}
