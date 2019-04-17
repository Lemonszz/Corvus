package party.lemons.corvus.init;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.ritual.*;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusRituals
{
	public static final Ritual TO_GAIAS_LAND = null;

	@SubscribeEvent
	public static void onRegisterRitual(RegistryEvent.Register<Ritual> event)
	{
		event.getRegistry().registerAll(
				new RitualAwaken(
						of("gemEmerald"),
						of("gemDiamond"),
						of("gemCrystalQuartz"),
						of(CorvusItems.WORMWOOD),
						of(CorvusItems.OIL_GROWTH))
						.setRegistryName(Corvus.MODID, "awaken"),

				new RitualSoulForge(
						of(CorvusItems.WORMWOOD),
						of(Items.GOLDEN_APPLE)
				).setRegistryName(Corvus.MODID, "soul_forge"),

				new RitualProjection(
						of(CorvusItems.OIL_PROTECTIVE),
						of("gemCrystalQuartz")
				).setRegistryName(Corvus.MODID, "project"),

				new RitualToGaiasLand(
						of("gemCrystalQuartz"),
						of("flower"),
						of("treeSapling"),
						of("obsidian")
				).setRegistryName(Corvus.MODID, "to_gaias_land")
		);
	}

	public static Ingredient of(String oreDict)
	{
		return new OreIngredient(oreDict);
	}

	public static Ingredient of(Item item)
	{
		return of(new ItemStack(item));
	}

	public static Ingredient of(Block block)
	{
		return of(new ItemStack(block));
	}

	public static Ingredient of(ItemStack stack)
	{
		return Ingredient.fromStacks(stack);
	}
}
