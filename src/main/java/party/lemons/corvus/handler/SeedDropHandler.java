package party.lemons.corvus.handler;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusItems;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class SeedDropHandler
{
	@SubscribeEvent
	public static void onHarvestBlock(BlockEvent.HarvestDropsEvent event)
	{
		if(event.getState().getBlock() instanceof BlockTallGrass && event.getDrops().size() > 0)
		{
			if(!event.isSilkTouching())
			{
				if(event.getWorld().rand.nextInt(100) <= 25)
				{
					Biome biome = event.getWorld().getBiome(event.getPos());
					if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS))
					{
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(CorvusItems.LAVENDER_SEEDS));
					}
					else if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST))
					{
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(CorvusItems.WORMWOOD_SEEDS));

					}
				}
			}
		}
	}
}
