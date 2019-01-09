package party.lemons.corvus.entity;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.init.CorvusSounds;

import javax.annotation.Nullable;


public class EntityWendigo extends EntityMob
{
	public EntityWendigo(World world)
	{
		super(world);

		this.setSize(1, 3);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 50.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityAnimal.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityVillager.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI()
	{
		EntityAINearestAttackableTarget playerAttackAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, e->
		{
			if(e instanceof EntityPlayerMP)
			{
				if(AdvancementHandler.hasAdvancement((EntityPlayerMP) e, new ResourceLocation(Corvus.MODID, "corvus/awaken")))
					return true;
			}
			return false;
		});

		this.targetTasks.addTask(2, playerAttackAI);

		Predicate<Entity> targetPred = e ->!playerAttackAI.shouldExecute();

		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, 10,true, false, targetPred));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 10,true, false, targetPred));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(!world.isRemote && !this.isBurning() && amount > 0)
		{
			amount /= 2;
		}

		return super.attackEntityFrom(source, amount);
	}

	public boolean attackEntityAsMob(Entity entityIn)
	{
		if(rand.nextBoolean())
			this.playSound(CorvusSounds.WENDIGO_ATTACK, 1.0F, 1.0F);

		return super.attackEntityAsMob(entityIn);
	}

	public void onLivingUpdate()
	{
		/* Burn during day*/
		if (this.world.isDaytime() && !this.world.isRemote)
		{
			float f = this.getBrightness();

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
			{
				//TODO: Uncomment this
				//this.setFire(8);
			}
		}

		if (this.world.isRemote)
		{
			for (int i = 0; i < 1; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) / 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) / 2.0D);
			}
		}

		/* Heal over time */
		if(!isBurning() && ticksExisted % 20 == 0)
		{
			heal(1);
		}

		double d0 = this.moveHelper.getX() - posX;
		double d1 = this.moveHelper.getZ() - posZ;
		float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
		rotationYaw = limitAngle(rotationYaw, f9, 1);
		super.onLivingUpdate();
	}

	protected float limitAngle(float sourceAngle, float targetAngle, float maximumChange)
	{
		float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);

		if (f > maximumChange)
		{
			f = maximumChange;
		}

		if (f < -maximumChange)
		{
			f = -maximumChange;
		}

		float f1 = sourceAngle + f;

		if (f1 < 0.0F)
		{
			f1 += 360.0F;
		}
		else if (f1 > 360.0F)
		{
			f1 -= 360.0F;
		}

		return f1;
	}

	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(CorvusSounds.WENDIGO_STEP, 1.0F, 1.0F);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound()
	{
		return CorvusSounds.WENDIGO;
	}

	@Override
	protected void playHurtSound(DamageSource source)
	{
		this.playSound(CorvusSounds.WENDIGO_HURT, 1.0F, 1.0F);
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LootTableList.ENTITIES_ZOMBIE;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
}
