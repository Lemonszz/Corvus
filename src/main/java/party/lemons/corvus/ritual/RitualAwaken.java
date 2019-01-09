package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.handler.AdvancementHandler;

public class RitualAwaken extends Ritual
{
	public RitualAwaken(Ingredient... ingredients)
	{
		super(ingredients);
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		AdvancementHandler.unlockAdvancement(player, new ResourceLocation(Corvus.MODID, "corvus/awaken"));

		SpiritUtil.getSpirit(player).setAwakened(true);
		SpiritUtil.syncSpirit(player);
	}
}
