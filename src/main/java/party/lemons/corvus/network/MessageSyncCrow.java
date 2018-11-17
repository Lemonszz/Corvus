package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.capability.crow.ICrow;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritCapability;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import java.util.ArrayList;
import java.util.List;

public class MessageSyncCrow implements IMessage
{
	private boolean crow;
	private int entityID;

	public MessageSyncCrow()
	{

	}

	public MessageSyncCrow(EntityPlayer player, boolean isCrow)
	{
		this.crow = isCrow;
		this.entityID = player.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.crow = buf.readBoolean();
		this.entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(crow);
		buf.writeInt(entityID);
	}

	public static class Handler implements IMessageHandler<MessageSyncCrow, IMessage>
	{

		@Override
		public IMessage onMessage(final MessageSyncCrow message, final MessageContext ctx)
		{

			Minecraft.getMinecraft().addScheduledTask(() -> {
				Entity selectedEntity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
				if(selectedEntity == null)
					return;

				ICrow crow = selectedEntity.getCapability(CrowCapability.CAPABILITY, null);
				if(crow != null)
				{
					crow.setCrow((EntityPlayer) selectedEntity, message.crow);
				}
			});

			return null;
		}
	}

}
