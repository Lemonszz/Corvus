package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.init.CorvusPotions;

public class RitualSoulForge extends Ritual
{
	public RitualSoulForge(Ingredient... ingredients)
	{
		super(ingredients);
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(CorvusPotions.SOUL_FORGE, 20 * 120, 0, false, true));

	}
}
