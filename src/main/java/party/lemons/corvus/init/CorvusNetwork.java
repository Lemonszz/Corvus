package party.lemons.corvus.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.network.*;
import party.lemons.lemonlib.event.InitEvent;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusNetwork
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Corvus.MODID);

	@SubscribeEvent
	public static void onPreInit(InitEvent.Pre event)
	{
		INSTANCE.registerMessage(MessageSyncSpirit.Handler.class, MessageSyncSpirit.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSelectSpell.Handler.class, MessageSelectSpell.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageCastSpell.Handler.class, MessageCastSpell.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncCrow.Handler.class, MessageSyncCrow.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncAwakened.Handler.class, MessageSyncAwakened.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncGaiaBreath.Handler.class, MessageSyncGaiaBreath.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageDoEffect.Handler.class, MessageDoEffect.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncProgression.Handler.class, MessageSyncProgression.class, id++, Side.CLIENT);
	}

	private static int id = 0;
}
