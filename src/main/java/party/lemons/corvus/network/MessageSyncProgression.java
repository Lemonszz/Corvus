package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.gaiabreath.GaiaBreathUtil;
import party.lemons.corvus.capability.gaiabreath.IGaiaBreath;
import party.lemons.corvus.capability.progression.IPlayerProgression;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.progression.Progression;
import party.lemons.corvus.progression.ProgressionRegistry;

import java.util.ArrayList;
import java.util.List;

public class MessageSyncProgression implements IMessage
{
	public List<Progression> progression;

	public MessageSyncProgression()
	{

	}

	public MessageSyncProgression(List<Progression> progression)
	{
		this.progression = progression;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		progression = new ArrayList<>();
		int size = buf.readInt();
		for(int i = 0; i < size; i++)
		{
			ResourceLocation location = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
			Progression prog = ProgressionRegistry.REGISTRY.getValue(location);
			progression.add(prog);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(progression.size());
		for(Progression prog : progression)
		{
			String name = prog.getRegistryName().toString();
			ByteBufUtils.writeUTF8String(buf, name);
		}
	}

	public static class Handler implements IMessageHandler<MessageSyncProgression, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageSyncProgression message, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					IPlayerProgression prog = ProgressionUtil.getProgression(Minecraft.getMinecraft().player);
					if(prog == null)
						return;

					for(int i = 0; i < message.progression.size(); i++)
					{
						Progression progression = message.progression.get(i);
						prog.unlockProgression(progression);
					}
				}
			});

			return null;
		}
	}

}
