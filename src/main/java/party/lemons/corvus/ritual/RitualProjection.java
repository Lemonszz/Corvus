package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.projection.ProjectionCapability;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.init.CorvusPotions;

public class RitualProjection extends Ritual
{
	public RitualProjection(Ingredient... ingredients)
	{
		super(ingredients);
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(CorvusPotions.PROJECTION, 20 * 15, 0, false, true));
		player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20 * 15, 0, false, true));
		player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * 15, 999, false, true));
		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 15, 1, false, true));

		player.getCapability(ProjectionCapability.CAPABILITY, null).setProjecting(true, player.getPosition());
	}
}
