package com.gamebuster19901.halo.common.item.capability.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public interface Energy extends IEnergyStorage, INBTSerializable<NBTTagCompound>{
	
	/**
	 * sets the amount of energy in this to the specified amount, or
	 * the maximum capacity if the amount specified exceeds the capacity
	 * 
	 * ignores maxReceive and maxExtract restrictions
	 * 
	 * @param energy
	 * @return the amount of energy now stored in this
	 */
	
	int setEnergy(int energy);
	
	void setCapacity(int capacity);
	
	void setCanReceive(boolean canReceive);
	
	void setMaxReceive(int maxReceive);
	
	int getMaxReceive();
	
	void setMaxExtract(int maxExtract);
	
	int getMaxExtract();
	
	void setCanExtract(boolean canExtract);
	
	default double getPercentageRemaining() {
		return this.getMaxEnergyStored() / this.getEnergyStored();
	}
	
}
