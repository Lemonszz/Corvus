package party.lemons.corvus.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemOrbOfImprisionment extends ItemModel
{
	public ItemOrbOfImprisionment()
	{
		setMaxStackSize(1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		//If the entity is a boss or the orb already has an entity or the target is a player, don't continue
		if(!target.isNonBoss() || target instanceof EntityPlayer || hasEntity(stack))
			return false;

		if(!playerIn.world.isRemote)
		{
			putEntity(stack, target);
			target.setDead();
			System.out.println(stack.hasTagCompound() + " after");

		}


		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		System.out.println(hasEntity(player.getHeldItem(hand)) + " place" );
		if(!worldIn.isRemote && hasEntity(stack))
		{
	//		EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag("entity"), worldIn);
		//	stack.setTagCompound(null);
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	private void putEntity(ItemStack stack, Entity entity)
	{
		if(entity.world.isRemote)
			return;

		NBTTagCompound tags = stack.getTagCompound();
		if(tags == null)
			tags = new NBTTagCompound();

		NBTTagCompound entityTags = new NBTTagCompound();
		entity.writeToNBTAtomically(entityTags);

		tags.setTag("entity", entityTags);
		stack.setTagCompound(tags);

		System.out.println(stack.hasTagCompound() + " input");
	}

	private boolean hasEntity(ItemStack stack)
	{
		System.out.println(stack.hasTagCompound());

		if(!stack.hasTagCompound())
			return false;

		System.out.println(stack.getTagCompound().hasKey("entity"));
		return stack.getTagCompound().hasKey("entity");
	}
}
