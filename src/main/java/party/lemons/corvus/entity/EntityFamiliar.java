package party.lemons.corvus.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.corvus.capability.crow.CrowCapability;

public class EntityFamiliar extends EntityCreature
{
	private int timeleft = 20 * 60;

	public EntityFamiliar(World worldIn)
	{
		super(worldIn);
		this.setSize(0.96F, 1.27500F);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.2F));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 0.8D, true));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityMob.class, 10, false, false, e->!(e instanceof EntityCreeper)));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		timeleft -= 1;
		if(timeleft <= 0 && !world.isRemote)
		{
			this.setDead();
			CrowCapability.spawnParticles((WorldServer) world, this, 75, 0.5F);
		}
	}

	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setInteger("time_left", timeleft);
	}

	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		timeleft = compound.getInteger("time_left");
	}

	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.6F, 0.5F);
	}

	protected SoundEvent getAmbientSound()
	{
		if(rand.nextBoolean())
			return SoundEvents.ENTITY_WOLF_GROWL;

		return SoundEvents.ENTITY_WOLF_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_WOLF_HURT;
	}
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_WOLF_DEATH;
	}
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	@Override
	protected float getSoundPitch()
	{
		return 0.5F;
	}

	public boolean attackEntityAsMob(Entity entityIn)
	{
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag)
		{
			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}
}

