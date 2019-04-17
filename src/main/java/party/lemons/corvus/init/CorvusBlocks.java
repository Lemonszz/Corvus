package party.lemons.corvus.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.block.*;
import party.lemons.corvus.block.effectcandle.BlockEffectCandle;
import party.lemons.corvus.block.effectcandle.CandleEffect;
import party.lemons.corvus.block.effectcandle.TileEntityEffectCandle;
import party.lemons.corvus.crafting.CorvusTab;
import party.lemons.corvus.gen.FeatureFrankincenseTree;
import party.lemons.lemonlib.block.BlockRegistry;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusBlocks
{
	public static final Block CRYSTAL_QUARTZ_ORE = Blocks.AIR;

	public static final Block CANDLE = Blocks.AIR;
	public static final Block CANDLE_GROWTH = Blocks.AIR;
	public static final Block CANDLE_RAGE = Blocks.AIR;
	public static final Block CANDLE_WIND = Blocks.AIR;


	public static final Block FRANKINSENCE_LOG = Blocks.AIR;
	public static final Block FRANKINSENCE_LEAVES = Blocks.AIR;
	public static final Block FRANKINSENCE_SAPLING = Blocks.AIR;

	public static final Block WORMWOOD = Blocks.AIR;
	public static final Block LAVENDER = Blocks.AIR;

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		BlockRegistry.setup(Corvus.MODID, event.getRegistry(), CorvusTab.INSTANCE);

		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockCandle(), 0.3F, 0.25F, 0F), "candle");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockEffectCandle(new CandleEffect.EffectFire()), 0.3F, 0.25F, 0F), "candle_rage");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockEffectCandle(new CandleEffect.EffectGrowth()), 0.3F, 0.25F, 0F), "candle_growth");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockEffectCandle(new CandleEffect.EffectWind()), 0.3F, 0.25F, 0F), "candle_wind");

		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockCorvusOre(1, 2, ()->CorvusItems.CRYSTAL_QUARTZ), 3F, 5F, 0F), "crystal_quartz_ore", "oreCrystalQuartz");
		BlockRegistry.registerBlock(new BlockCorvusLog(), "frankinsence_log", "logWood");
		BlockRegistry.registerBlock(new BlockCorvusLeaves(()->Item.getItemFromBlock(FRANKINSENCE_SAPLING), ()->CorvusItems.FRANKINCENSE_TEARS), "frankinsence_leaves", "treeLeaves");
		BlockRegistry.registerBlock(new BlockCorvusSapling(new FeatureFrankincenseTree(true)), "frankinsence_sapling", "treeSapling");
		BlockRegistry.registerBlock((BlockRegistry.setProperties(new BlockModel(Material.WOOD), 2F, 5F, 0F)), "frankinsence_planks", "plankWood");

		BlockRegistry.registerBlock(new BlockCorvusCrop(()->CorvusItems.LAVENDER, ()->CorvusItems.LAVENDER_SEEDS), "lavender", "flower");
		BlockRegistry.registerBlock(new BlockCorvusCrop(()->CorvusItems.WORMWOOD, ()->CorvusItems.WORMWOOD_SEEDS), "wormwood", "flower");

		BlockRegistry.registerBlock((BlockRegistry.setProperties(new BlockModel(Material.IRON), 3F, 5F, 0F)), "crystal_quartz_block", "blockCrystalQuartz");

		GameRegistry.registerTileEntity(TileEntityEffectCandle.class, new ResourceLocation(Corvus.MODID, "candle"));
	}
}
