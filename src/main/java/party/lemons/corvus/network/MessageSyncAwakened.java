package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritCapability;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import java.util.ArrayList;
import java.util.List;

public class MessageSyncAwakened implements IMessage
{
	public boolean awake;

	public MessageSyncAwakened()
	{

	}

	public MessageSyncAwakened(boolean awake)
	{
		this.awake = awake;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		awake = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(awake);
	}

	public static class Handler implements IMessageHandler<MessageSyncAwakened, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageSyncAwakened message, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				ISpirit spirit = Minecraft.getMinecraft().player.getCapability(SpiritCapability.CAPABILITY, null);
				if(spirit != null)
				{
					spirit.setAwakened(message.awake);
				}
			});

			return null;
		}
	}

}
