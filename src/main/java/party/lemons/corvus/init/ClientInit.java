package party.lemons.corvus.init;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.block.effectcandle.BlockEffectCandle;
import party.lemons.corvus.entity.EntityCrow;
import party.lemons.corvus.entity.EntityFamiliar;
import party.lemons.corvus.entity.EntityNagual;
import party.lemons.corvus.entity.EntityWendigo;
import party.lemons.corvus.entity.render.RenderCrow;
import party.lemons.corvus.entity.render.RenderFamiliar;
import party.lemons.corvus.entity.render.RenderNagual;
import party.lemons.corvus.entity.render.RenderWendigo;
import party.lemons.corvus.item.ItemOrbOfImprisonment;
import party.lemons.lemonlib.event.InitEvent;

@Mod.EventBusSubscriber(modid = Corvus.MODID, value = Side.CLIENT)
public class ClientInit
{
	public static final KeyBinding KEY_SPELL = new KeyBinding("key.corvus.use", 46, "key.categories.corvus");

	@SubscribeEvent
	public static void onPreInit(InitEvent.Pre event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityCrow.class, RenderCrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFamiliar.class, RenderFamiliar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityWendigo.class, RenderWendigo::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityNagual.class, RenderNagual::new);

		ClientRegistry.registerKeyBinding(KEY_SPELL);
	}

	@SubscribeEvent
	public static void onItemModelRegister(ModelRegistryEvent event)
	{
		ModelResourceLocation orb_off = new ModelResourceLocation(CorvusItems.ORB_OF_IMPRISONMENT.getRegistryName(), "inventory");
		ModelResourceLocation orb_on = new ModelResourceLocation(CorvusItems.ORB_OF_IMPRISONMENT.getRegistryName() + "_on", "inventory");
		ModelBakery.registerItemVariants(CorvusItems.ORB_OF_IMPRISONMENT, orb_off, orb_on);

		ModelLoader.setCustomMeshDefinition(CorvusItems.ORB_OF_IMPRISONMENT, stack->
		{
			if(ItemOrbOfImprisonment.hasEntity(stack))
				return orb_on;

			return orb_off;
		});
	}

	@SubscribeEvent
	public static void onRegisterColorBlock(ColorHandlerEvent.Block event)
	{
		event.getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex)->0x88d88c, CorvusBlocks.FRANKINSENCE_LEAVES);

		event.getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex)->{
				if(tintIndex == 0)
				{
					return ((BlockEffectCandle)state.getBlock()).getEffect().getColour();
				}
				return 0xFFFFFF;
		}, CorvusBlocks.CANDLE_GROWTH, CorvusBlocks.CANDLE_RAGE, CorvusBlocks.CANDLE_WIND);
	}

	@SubscribeEvent
	public static void onRegisterColorItem(ColorHandlerEvent.Item event)
	{
		event.getItemColors().registerItemColorHandler((stack, tintIndex)->0x88d88c, CorvusBlocks.FRANKINSENCE_LEAVES);
		event.getItemColors().registerItemColorHandler((stack, tintIndex)->{
			if(tintIndex == 0)
			{
				return ((BlockEffectCandle) ((ItemBlock) stack.getItem()).getBlock()).getEffect().getColour();
			}
			return 0xFFFFFF;
		}, CorvusBlocks.CANDLE_GROWTH, CorvusBlocks.CANDLE_RAGE, CorvusBlocks.CANDLE_WIND);
	}
}
