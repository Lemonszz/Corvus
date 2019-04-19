package party.lemons.corvus.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import party.lemons.corvus.Corvus;
import party.lemons.lemonlib.event.InitEvent;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusGeneral
{
	@SubscribeEvent
	public static void onInit(InitEvent.Init event)
	{
		OreDictionary.registerOre("flower", new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("flower", Blocks.YELLOW_FLOWER);
		OreDictionary.registerOre("flower", new ItemStack(Blocks.DOUBLE_PLANT, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("tulip", new ItemStack(Blocks.RED_FLOWER, 1, 4));
		OreDictionary.registerOre("tulip", new ItemStack(Blocks.RED_FLOWER, 1, 5));
		OreDictionary.registerOre("tulip", new ItemStack(Blocks.RED_FLOWER, 1, 6));
		OreDictionary.registerOre("tulip", new ItemStack(Blocks.RED_FLOWER, 1, 7));

	}
}
