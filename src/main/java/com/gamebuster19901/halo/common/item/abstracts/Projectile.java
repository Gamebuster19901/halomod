package com.gamebuster19901.halo.common.item.abstracts;

import java.lang.reflect.Field;
import java.util.Random;

import com.gamebuster19901.halo.common.entity.ProjectileEntity;
import com.gamebuster19901.halo.common.item.capability.Shootable;
import com.gamebuster19901.halo.common.util.EasyLocalization;
import com.gamebuster19901.halo.common.util.VecMath;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public final class Projectile implements IProjectile, EasyLocalization{
	private static final Field RAND = ObfuscationReflectionHelper.findField(Entity.class, "field_70146_Z");
	
	protected final NBTTagCompound projectile;
	
	protected double x;
	protected double y;
	protected double z;
	
	protected Entity shooter;
	protected Shootable gun;
	protected ProjectileEntity projectileEntity;
	
	/**
	 * @param projectile the nbt representing the projectile
	 */
	public Projectile(NBTTagCompound projectile) {
		this.projectile = projectile;
	}
	
	/**
	 * Makes the entity shoot the projectile from their eye position
	 * 
	 * @param shooter
	 * @param gun
	 * @param gunNBT
	 */
	public void shoot(Entity shooter, Shootable gun) {
		this.shooter = shooter;
		Vec3d pos = shooter.getPositionEyes(3f).add(new Vec3d(0,-0.5,0));
		Vec3d lookVec = shooter.getLookVec().add(0,0.3d,0);
		float distance = 1.5f;
		
		shoot(shooter.getEntityWorld(), VecMath.traverse(pos, lookVec, distance), shooter.getLookVec(), gun);
	}
	
	/**
	 * Makes the entity shoot the projectile from an arbitrary position
	 * @param shooter
	 * @param world
	 * @param pos
	 * @param vector
	 * @param gun
	 * @param gunNBT
	 */
	public void shoot(Entity shooter, World world, Vec3d pos, Vec3d vector, Shootable gun) {
		this.shooter = shooter;
		shoot(shooter.getEntityWorld(), new Vec3d(shooter.posX, shooter.posY, shooter.posZ), shooter.getLookVec(), gun);
	}
	
	/**
	 * Shoots the projectile from an arbitrary position
	 * 
	 * @param world the world to spawn the projectile in
	 * @param pos the position to spawn the projectile at
	 * @param vector the projectile's vector
	 * @param gun the gun which shot the projectile
	 */
	public void shoot(World world, Vec3d pos, Vec3d vector, Shootable gun) {
		if(gun == null) {
			gun = HandImpl.INSTANCE;
		}
		this.gun = gun;
		if(!world.isRemote && projectile != null) {
			projectile.setString("ownerName", shooter.getName());
			projectileEntity = (ProjectileEntity)AnvilChunkLoader.readWorldEntityPos(projectile, world, pos.x, pos.y, pos.z, false);
			if(projectileEntity != null) {
				try {
					projectileEntity.setUniqueId(MathHelper.getRandomUUID((Random)RAND.get(projectileEntity)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new AssertionError(e);
				}
				Vec3d lookVec = shooter.getLookVec();
				projectileEntity.setShooter(shooter);
				projectileEntity.setGun(gun);
				shoot(lookVec.x, lookVec.y, lookVec.z, gun.getMuzzleVelocity(), gun.getBloom()); //Shoot before spawning entity so it has momentum when spawned!
				world.spawnEntity(projectileEntity);
				return;
			}
			
			throw new IllegalStateException("Illegal projectile NBT: ( " + projectile.toString() + " )");
		}
	}
	
	
	/**
	 * See EntityArrow
	 * @deprecated Use any of the other shoot() methods in this class.
	 */
	@Override
	@Deprecated
	public void shoot(double vx, double vy, double vz, float velocity, float inaccuracy) {
		if(projectileEntity != null) {
			Random rand;
			try {
				rand = (Random) RAND.get(projectileEntity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new AssertionError(e);
			}
			
	        float f = MathHelper.sqrt(vx * vx + vy * vy + vz * vz);
	        vx = vx / (double)f;
	        vy = vy / (double)f;
	        vz = vz / (double)f;
	        vx = vx + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
	        vy = vy + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
	        vz = vz + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
	        vx = vx * (double)velocity;
	        vy = vy * (double)velocity;
	        vz = vz * (double)velocity;
	        projectileEntity.motionX = vx;
	        projectileEntity.motionY = vy;
	        projectileEntity.motionZ = vz;
	        float f1 = MathHelper.sqrt(vx * vx + vz * vz);
	        projectileEntity.rotationYaw = -(float)(MathHelper.atan2(vx, vz) * (180D / Math.PI));
	        projectileEntity.rotationPitch = -(float)(MathHelper.atan2(vy, (double)f1) * (180D / Math.PI));
	        projectileEntity.prevRotationYaw = -projectileEntity.rotationYaw;
	        projectileEntity.prevRotationPitch = -projectileEntity.rotationPitch;
		}
	}
	
	public NBTTagCompound getProjectileNBT() {
		return projectile;
	}
	
	public Entity getProjectileEntity() {
		return projectileEntity;
	}
}
