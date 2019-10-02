package party.lemons.corvus.capability.spirit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.init.CorvusItems;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.init.CorvusProgression;
import party.lemons.corvus.network.MessageSyncSpirit;
import party.lemons.corvus.spell.Spell;

import java.util.Collections;
import java.util.List;

public class SpiritUtil
{
	public static void syncSpirit(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		ISpirit spirit = getSpirit(player);
		List<Spell> sendSpells = spirit.getUnlockedSpells();
		if(!ProgressionUtil.hasProgression(player, CorvusProgression.AWAKEN))
		{
			spirit.setActiveSpell(null);
			sendSpells = Collections.EMPTY_LIST;
		}

		CorvusNetwork.INSTANCE.sendTo(new MessageSyncSpirit(spirit.getMaxSprit(), spirit.getSpirit(), sendSpells, spirit.getActiveSpell()), (EntityPlayerMP) player);
	}

	public static ISpirit getSpirit(EntityPlayer player)
	{
		return player.getCapability(SpiritCapability.CAPABILITY, null);
	}

	public static void changeSpirit(EntityPlayer player, int amount)
	{
		if(getSpirit(player).changeSprit(amount))
			syncSpirit(player);
	}

	public static boolean hasSpirit(EntityPlayer player, int amount)
	{
		return getSpirit(player).getSpirit() >= amount;
	}

	public static void unlockSpell(EntityPlayer player, Spell spell)
	{
		getSpirit(player).unlockSpell(spell);
		syncSpirit(player);
	}

	public static boolean hasSpellUnlocked(EntityPlayer player, Spell spell)
	{
		return getSpirit(player).hasUnlockedSpell(spell);
	}

	public static void setActiveSpell(EntityPlayer player, Spell spell)
	{
		if(hasSpellUnlocked(player, spell))
		{
			getSpirit(player).setActiveSpell(spell);
		}
		syncSpirit(player);
	}

	public static boolean isSpellActive(EntityPlayer player, Spell spell)
	{
		return getSpirit(player).getActiveSpell() == spell;
	}

	public static void castSpell(EntityPlayer player)
	{
		if(getSpirit(player).getActiveSpell() != null)
			getSpirit(player).getActiveSpell().castSpell(player);
	}

	public static boolean hasGrimoire(EntityPlayer player)
	{
		if(!player.world.isRemote)
		{
			if(!ProgressionUtil.hasProgression(player, CorvusProgression.AWAKEN))
			{
				return false;
			}
		}
		else
		{
			if(getSpirit(player).getUnlockedSpells().size() <= 0)
				return false;
		}

		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() == CorvusItems.GRIMOIRE)
				return true;
		}

		return false;
	}
}
