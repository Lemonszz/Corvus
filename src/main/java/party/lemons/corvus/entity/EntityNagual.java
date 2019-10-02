package party.lemons.corvus.entity;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityNagual extends EntityCreature
{
	public static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityNagual.class, DataSerializers.VARINT);

	public EntityNagual(World worldIn)
	{
		super(worldIn);
		setSize(0.6F, 1.8F);
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
		this.tasks.addTask(2, new EntityAIMoveIndoors(this));
		this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
		this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
		this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 16.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityOcelot.class, 16.0F));

		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(VARIANT, 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setInteger("_type", dataManager.get(VARIANT));
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		dataManager.set(VARIANT, compound.getInteger("_type"));
		super.readEntityFromNBT(compound);
	}

	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		setHomePosAndDistance(getPosition(), 30);
		getDataManager().set(VARIANT, world.rand.nextInt(4));

		if(world.rand.nextInt(5) == 0)
		{
			final int type = world.rand.nextInt(10);
			ItemStack stack = ItemStack.EMPTY;
			switch (type)
			{
				case 0:
					stack = new ItemStack(Items.BONE);
					break;
				case 1:
					stack = new ItemStack(Items.BOOK);
					break;
				case 2:
					stack = new ItemStack(Items.BOW);
					break;
				case 3:
					stack = new ItemStack(Items.IRON_SWORD);
					break;
				case 4:
					stack = new ItemStack(Items.GOLDEN_SWORD);
					break;
			}
			setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
		}

		return super.onInitialSpawn(difficulty, livingdata);
	}
}
