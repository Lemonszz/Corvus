package party.lemons.corvus.init;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.crafting.CorvusTab;
import party.lemons.corvus.item.*;
import party.lemons.lemonlib.item.ItemRegistry;
import party.lemons.corvus.Corvus;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusItems
{
	public static final Item CRYSTAL_QUARTZ = Items.AIR;
	public static final Item GRIMOIRE = Items.AIR;
	public static final Item FRANKINCENSE_TEARS = Items.AIR;
	public static final Item LAVENDER = Items.AIR;
	public static final Item WORMWOOD = Items.AIR;
	public static final Item LAVENDER_SEEDS = Items.AIR;
	public static final Item WORMWOOD_SEEDS = Items.AIR;
	public static final Item OIL_GROWTH = Items.AIR;
	public static final Item OIL_PROTECTIVE = Items.AIR;
	public static final Item OIL_ATTUNED = Items.AIR;
	public static final Item CURSED_BONE = Items.AIR;
	public static final Item SPECIAL_STICK = Items.AIR;
	public static final Item WISER_GEM = Items.AIR;

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		ItemRegistry.setup(Corvus.MODID, event.getRegistry(), CorvusTab.INSTANCE);

		ItemRegistry.registerItem(new ItemGrimoire(), "grimoire", "book");

		ItemRegistry.registerItem(new ItemOil(()-> new PotionEffect[]{
				new PotionEffect(MobEffects.REGENERATION, 2400, 1)
		}), "oil_growth", "oil");

		ItemRegistry.registerItem(new ItemOil(()-> new PotionEffect[]{
				new PotionEffect(MobEffects.STRENGTH, 2400, 1),
				new PotionEffect(CorvusPotions.BURNING_RAGE, 2400, 0)
		}), "oil_enraged", "oil");

		ItemRegistry.registerItem(new ItemOil(()-> new PotionEffect[]{
				new PotionEffect(MobEffects.SPEED, 2400, 1),
				new PotionEffect(CorvusPotions.SLOW_FALL, 2400, 1)
		}), "oil_protective", "oil");

		ItemRegistry.registerItem(new ItemOil(()-> new PotionEffect[]{
				new PotionEffect(MobEffects.NIGHT_VISION, 2400, 0),
				new PotionEffect(MobEffects.SATURATION, 2400, 5),
				new PotionEffect(CorvusPotions.ATTUNED, 2400, 0)
		}), "oil_attuned", "oil");

		ItemRegistry.registerItem(new ItemModel(), "crystal_quartz", "gemCrystalQuartz");
		ItemRegistry.registerItem(new ItemModel(), "wiser_gem", "gemWiser");

		ItemRegistry.registerItem(new ItemSpiritFood(2, 0.25F, 10, false), "frankincense_tears");
		ItemRegistry.registerItem(new ItemSpiritFood(2, 0.25F, 10, false), "wormwood", "cropWormwood");
		ItemRegistry.registerItem(new ItemSpiritFood(2, 0.25F, 10, false), "lavender", "cropLavender");

		ItemRegistry.registerItem(new ItemCorvusSeed(()->CorvusBlocks.LAVENDER.getDefaultState(), EnumPlantType.Crop), "lavender_seeds");
		ItemRegistry.registerItem(new ItemCorvusSeed(()->CorvusBlocks.WORMWOOD.getDefaultState(), EnumPlantType.Crop), "wormwood_seeds");

		ItemRegistry.registerItem(new ItemCursedBone(), "cursed_bone", "bone");
		ItemRegistry.registerItem(new ItemSpecialStick(), "special_stick");

		ItemRegistry.registerItem(new ItemBreathingTulip(CorvusBlocks.BREATHING_TULIP), "breathing_tulip", "flower", "tulip");
	}
}
