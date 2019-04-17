package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.handler.EffectHandler;

public class MessageDoEffect implements IMessage
{
	public BlockPos pos;
	public int effect;

	public MessageDoEffect()
	{

	}

	public MessageDoEffect(int effect, BlockPos blockPos)
	{
		this.pos = blockPos;
		this.effect = effect;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.effect = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(effect);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler implements IMessageHandler<MessageDoEffect, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageDoEffect message, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				EffectHandler.doEffect(message.effect, message.pos, Minecraft.getMinecraft().world);
			});

			return null;
		}
	}

}
