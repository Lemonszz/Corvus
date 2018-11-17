package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritCapability;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import java.util.ArrayList;
import java.util.List;

public class MessageSelectSpell implements IMessage
{
	public Spell spell;

	public MessageSelectSpell()
	{

	}

	public MessageSelectSpell(Spell spell)
	{
		this.spell = spell;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		spell = SpellRegistry.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(buf)));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, spell.getRegistryName().toString());
	}

	public static class Handler implements IMessageHandler<MessageSelectSpell, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageSelectSpell message, final MessageContext ctx)
		{
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			WorldServer world = serverPlayer.getServerWorld();
			world.addScheduledTask(()->SpiritUtil.setActiveSpell(serverPlayer, message.spell));

			return null;
		}
	}

}
