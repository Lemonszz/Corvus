package party.lemons.corvus.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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

public class MessageSyncSpirit implements IMessage
{
	public int maxSpirit, currentSpirit, spellCount;
	public List<Spell> spells = new ArrayList<>();
	public Spell activeSpell;

	public MessageSyncSpirit()
	{

	}

	public MessageSyncSpirit(int maxSpirit, int currentSpirit, List<Spell> spells, Spell activeSpell)
	{
		this.maxSpirit = maxSpirit;
		this.currentSpirit = currentSpirit;
		this.spellCount = spells.size();
		this.spells = spells;
		this.activeSpell = activeSpell;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.maxSpirit = buf.readInt();
		this.currentSpirit = buf.readInt();

		String active = ByteBufUtils.readUTF8String(buf);
		if(!active.equals("no"))
			activeSpell = SpellRegistry.REGISTRY.getValue(new ResourceLocation(active));

		this.spellCount = buf.readInt();

		for(int i = 0; i < spellCount; i++)
		{
			spells.add(SpellRegistry.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(buf))));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(maxSpirit);
		buf.writeInt(currentSpirit);

		if(activeSpell != null)
		{
			ByteBufUtils.writeUTF8String(buf, activeSpell.getRegistryName().toString());
		}
		else
		{
			ByteBufUtils.writeUTF8String(buf, "no");
		}

		buf.writeInt(spellCount);
		for(int i = 0; i < spellCount; i++)
		{
			ByteBufUtils.writeUTF8String(buf, spells.get(i).getRegistryName().toString());
		}
	}

	public static class Handler implements IMessageHandler<MessageSyncSpirit, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageSyncSpirit message, final MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				ISpirit spirit = Minecraft.getMinecraft().player.getCapability(SpiritCapability.CAPABILITY, null);
				if(spirit != null)
				{
					spirit.setSpiritMax(message.maxSpirit);
					spirit.setSprit(message.currentSpirit);
					spirit.getUnlockedSpells().clear();

					for(Spell spell : message.spells)
					{
						spirit.unlockSpell(spell);
					}
					spirit.setActiveSpell(message.activeSpell);
				}
			});

			return null;
		}
	}

}
