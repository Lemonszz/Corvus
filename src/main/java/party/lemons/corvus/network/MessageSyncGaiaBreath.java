package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.gaiabreath.GaiaBreathUtil;
import party.lemons.corvus.capability.gaiabreath.IGaiaBreath;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritCapability;

public class MessageSyncGaiaBreath implements IMessage
{
	public int amt;

	public MessageSyncGaiaBreath()
	{

	}

	public MessageSyncGaiaBreath(int amt)
	{
		this.amt = amt;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		amt = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(amt);
	}

	public static class Handler implements IMessageHandler<MessageSyncGaiaBreath, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageSyncGaiaBreath message, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					IGaiaBreath breath = GaiaBreathUtil.getGaiaBreath(Minecraft.getMinecraft().player);
					if(breath != null)
					{
						breath.setBreath(message.amt);
					}
				}
			});

			return null;
		}
	}

}
