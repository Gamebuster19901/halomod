package com.gamebuster19901.halo.capability.client.item.overlay;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class OverlayStorage implements IStorage<Overlay>{

	@Override
	public INBTBase writeNBT(Capability<Overlay> capability, Overlay instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<Overlay> capability, Overlay instance, EnumFacing side, INBTBase nbt) {}

}