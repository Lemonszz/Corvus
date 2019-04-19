package party.lemons.corvus.ritual;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.handler.AttunedEffectHandler;
import party.lemons.corvus.handler.EffectHandler;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.init.CorvusItems;

import java.util.ArrayList;
import java.util.List;

public class RitualGrowthOfBreath extends Ritual
{
	private static ItemStack STACK_OIL = new ItemStack(CorvusItems.OIL_ATTUNED);

	public RitualGrowthOfBreath(Ingredient... ingredients)
	{
		super(ingredients);
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			EffectHandler.performEffect(EffectHandler.STUNNING_DAHLIA, pos, world);
			world.setBlockToAir(pos);
			ItemStack dropStack = new ItemStack(CorvusBlocks.BREATHING_TULIP);
			EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, dropStack);
			entityitem.motionX = 0;
			entityitem.motionY = 0.25F;
			entityitem.motionZ = 0;
			entityitem.velocityChanged = true;
			entityitem.setDefaultPickupDelay();
			world.spawnEntity(entityitem);
		}
	}

	@Override
	public boolean doRitual(World world, BlockPos pos, EntityPlayer player)
	{
		if(AttunedEffectHandler.isAttuned(player))
		{
			return super.doRitual(world, pos, player);
		}
		return false;
	}

	@Override
	public boolean hasTip()
	{
		return true;
	}

	@Override
	public ItemStack getTipStack()
	{
		return STACK_OIL;
	}

	@Override
	public List<String> getTipText()
	{
		List<String> str = new ArrayList<>();
		str.add(I18n.format("corvus.message.requireattuned"));

		return str;
	}
}
