package com.gamebuster19901.halo.common.item.abstracts;

import java.util.Random;

import com.gamebuster19901.halo.common.item.capability.Shootable;

import net.minecraft.nbt.NBTTagCompound;

public final class HandImpl implements Shootable{

	public static final HandImpl INSTANCE = new HandImpl();
	
	private Random random = new Random();
	
	private HandImpl() {}

	@Override
	public float getBloom() {
		return 0;
	}

	@Override
	public float getMaxBloom() {
		return 0;
	}

	@Override
	public float getBloomIncreasePerShot() {
		return 0;
	}

	@Override
	public float getBloomDecreasePerTick() {
		return 0;
	}

	@Override
	public float getMuzzleVelocity() {
		return 0;
	}

	@Override
	public float getMinVerticalRecoil() {
		return 0;
	}

	@Override
	public float getMaxVerticalRecoil() {
		return 0;
	}

	@Override
	public float getMinHorizontalRecoil() {
		return 0;
	}

	@Override
	public float getMaxHorizontalRecoil() {
		return 0;
	}

	@Override
	public NBTTagCompound getProjectile() {
		return null;
	}

	@Override
	public void update() {
	}

	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	public void setProjectile(NBTTagCompound projectile) {}

	@Override
	public void setMaxBloom(float maxBloom) {
	}

	@Override
	public void setBloomIncreasePerShot(float bloomIncrease) {
	}

	@Override
	public void setBloomDecreasePerTick(float bloomDecrease) {
	}

	@Override
	public void setMuzzleVelocity(float velocity) {
	}

	@Override
	public void setMinVerticalRecoil(float minVertical) {
	}

	@Override
	public void setMaxVerticalRecoil(float maxVertical) {
	}

	@Override
	public void setMinHorizontalRecoil(float minHorizontal) {
	}

	@Override
	public void setMaxHorizontalRecoil(float maxHorizontal) {
	};
}
