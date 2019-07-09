package party.lemons.corvus.ritual;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.gen.gaia.dimension.GaiaDimension;
import party.lemons.corvus.handler.AttunedEffectHandler;
import party.lemons.corvus.handler.EffectHandler;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.init.CorvusItems;
import party.lemons.corvus.init.CorvusSounds;

import java.util.ArrayList;
import java.util.List;

public class RitualGrowthOfBreath extends RitualItemSummon
{
	private static ItemStack STACK_OIL = new ItemStack(CorvusItems.OIL_ATTUNED);

	public RitualGrowthOfBreath(Ingredient... ingredients)
	{
		super(new ItemStack(CorvusBlocks.BREATHING_TULIP), ingredients);
	}

	@Override
	public boolean doRitual(World world, BlockPos pos, EntityPlayer player)
	{
		if(AttunedEffectHandler.isAttuned(player) && world.provider.getDimension() == GaiaDimension.GAIA_ID)
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
		str.add(I18n.format("corvus.message.requiregaia"));

		return str;
	}
}
