package party.lemons.corvus.handler;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.ClientInit;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageCastSpell;

@Mod.EventBusSubscriber(modid = Corvus.MODID, value = Side.CLIENT)
public class ClientKeybindHandler
{
	@SubscribeEvent
	public static void onClientTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			if(ClientInit.KEY_SPELL.isPressed())
			{
				CorvusNetwork.INSTANCE.sendToServer(new MessageCastSpell());
			}
		}
	}

	@SubscribeEvent
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CorvusBlocks.BREATHING_TULIP), 0, new ModelResourceLocation(CorvusBlocks.BREATHING_TULIP.getRegistryName(), "inventory"));
	}
}
