package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.block.BlockBreathingTulip;
import party.lemons.corvus.block.tilentity.TileEntityBreathingTulip;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.handler.EffectHandler;
import party.lemons.corvus.init.CorvusSounds;

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

		if(!world.isRemote)
		{
			for(int _xx = -2; _xx < 2; _xx++)
			{
				for(int _zz = -2; _zz < 2; _zz++)
				{
					BlockPos checkPos = pos.add(_xx, 0, _zz);
					if(world.getBlockState(checkPos).getBlock() instanceof BlockBreathingTulip)
					{
						TileEntityBreathingTulip tulip = (TileEntityBreathingTulip) world.getTileEntity(checkPos);
						tulip.setDamage(0);
						float pitchOffset = (world.rand.nextFloat() / 5) * (world.rand.nextBoolean() ? 1 : -1);
						world.playSound(null, pos, CorvusSounds.ITEM_SUMMON, SoundCategory.BLOCKS, 1F, 1F + pitchOffset);

						EffectHandler.performEffect(EffectHandler.STUNNING_DAHLIA, checkPos, world);
					}

				}
			}
		}
	}
}
