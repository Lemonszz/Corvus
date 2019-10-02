package party.lemons.corvus.entity;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.init.CorvusItems;
import party.lemons.corvus.init.CorvusProgression;
import party.lemons.corvus.init.CorvusSounds;
import party.lemons.corvus.init.CorvusSpells;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class EntityCrow extends EntityTameable
{
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(CorvusItems.FRANKINCENSE_TEARS, Items.RABBIT_FOOT, CorvusItems.LAVENDER, CorvusItems.WORMWOOD);

	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;

	public EntityCrow(World worldIn)
	{
		super(worldIn);
		this.setSize(0.5F, 0.45F);
		this.moveHelper = new EntityFlyHelper(this);
	}

	protected void initEntityAI()
	{
		this.aiSit = new EntityAISit(this);
		this.tasks.addTask(0, new EntityAIPanic(this, 1.25D));
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(2, new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
		this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
	}

	protected PathNavigate createNavigator(World worldIn)
	{
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
		pathnavigateflying.setCanOpenDoors(false);
		pathnavigateflying.setCanFloat(true);
		pathnavigateflying.setCanEnterDoors(true);
		return pathnavigateflying;
	}

	public float getEyeHeight()
	{
		return this.height * 0.6F;
	}

	public void onLivingUpdate()
	{
		playWarningSound(this.world, this);
		
		super.onLivingUpdate();
		this.calculateFlapping();
	}

	private boolean playWarningSound(World world, Entity entity)
	{
		if (!entity.isSilent() && world.rand.nextInt(50) == 0)
		{
			List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, entity.getEntityBoundingBox().grow(15), e->e instanceof EntityWendigo);

			if (!list.isEmpty())
			{
				EntityLiving entityliving = list.get(world.rand.nextInt(list.size()));

				if (!entityliving.isSilent())
				{
					world.playSound(null, entity.posX, entity.posY, entity.posZ, CorvusSounds.CROW_YELL, entity.getSoundCategory(), 0.7F, 0.8F + (rand.nextFloat() / 20F));
					return true;
				}
			}

			return false;
		}
		else
		{
			return false;
		}
	}

	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem()))
		{
			if (!player.capabilities.isCreativeMode)
			{
				itemstack.shrink(1);
			}

			if (!this.isSilent())
			{
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			}

			if (!this.world.isRemote)
			{
				if (this.rand.nextInt(5) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
				{
					if(!ProgressionUtil.hasProgression(player, CorvusProgression.AWAKEN))
						return true;

					ProgressionUtil.tryUnlockProgression(player, CorvusProgression.TAME_CROW);

					this.setTamedBy(player);
					this.playTameEffect(true);

					if(!world.isRemote)
						SpiritUtil.unlockSpell(player, CorvusSpells.LEAP);

					this.world.setEntityState(this, (byte)7);
				}
				else
				{
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte)6);
				}
			}

			return true;
		}
		else
		{
			if (!this.world.isRemote && !this.isFlying() && this.isTamed() && this.isOwner(player))
			{
				this.aiSit.setSitting(!this.isSitting());
			}

			return super.processInteract(player, hand);
		}
	}

	private void calculateFlapping()
	{
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3D);
		this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);

		if (!this.onGround && this.flapping < 1.0F)
		{
			this.flapping = 1.0F;
		}

		this.flapping = (float)((double)this.flapping * 0.9D);

		if (!this.onGround && this.motionY < 0.0D)
		{
			this.motionY *= 0.6D;
		}

		this.flap += this.flapping * 2.0F;
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return false;
	}

	public boolean getCanSpawnHere()
	{
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog || block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	public void fall(float distance, float damageMultiplier)
	{
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{
	}

	public boolean canBePushed()
	{
		return true;
	}
	public boolean isFlying()
	{
		return !this.onGround;
	}

	protected void collideWithEntity(Entity entityIn)
	{
		if (!(entityIn instanceof EntityPlayer))
		{
			super.collideWithEntity(entityIn);
		}
	}

	public SoundCategory getSoundCategory()
	{
		return SoundCategory.NEUTRAL;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_PARROT_HURT;
	}

	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_PARROT_DEATH;
	}

	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
	}

	@Nullable
	public SoundEvent getAmbientSound()
	{
		return CorvusSounds.CROW_CAW;
	}

	protected float getSoundVolume()
	{
		return 3.0F;
	}

	protected float playFlySound(float p_191954_1_)
	{
		this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
		return p_191954_1_ + this.flapSpeed / 2.0F;
	}

	protected boolean makeFlySound()
	{
		return true;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}
}
