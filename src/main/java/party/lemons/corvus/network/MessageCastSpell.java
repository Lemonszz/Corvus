package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

public class MessageCastSpell implements IMessage
{
	public MessageCastSpell()
	{

	}


	@Override
	public void fromBytes(ByteBuf buf)
	{
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
	}

	public static class Handler implements IMessageHandler<MessageCastSpell, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageCastSpell message, final MessageContext ctx)
		{
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			WorldServer world = serverPlayer.getServerWorld();
			world.addScheduledTask(()->SpiritUtil.castSpell(serverPlayer));

			return null;
		}
	}

}
