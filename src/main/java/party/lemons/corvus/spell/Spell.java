package party.lemons.corvus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.registries.IForgeRegistryEntry;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusSounds;
import party.lemons.lemonlib.handler.cooldown.CooldownUtil;

public abstract class Spell extends IForgeRegistryEntry.Impl<Spell>
{
	private final int cost, cooldown;

	public Spell(int cost, int cooldown)
	{
		this.cost = cost;
		this.cooldown = cooldown;
	}

	public void castSpell(EntityPlayer player)
	{
		if(!canCast(player))
		{
			if(SpiritUtil.hasGrimoire(player))
				player.world.playSound(null, player.getPosition(), CorvusSounds.SPELL_FAIL, SoundCategory.PLAYERS, 1F, 1F);
			return;
		}

		SpiritUtil.changeSpirit(player, -cost);
		performSpell(player);

		setCooldown(player);
	}
	public abstract void performSpell(EntityPlayer player);

	public void setCooldown(EntityPlayer player)
	{
		CooldownUtil.setCooldown(player, getRegistryName().toString(), cooldown);
	}

	public boolean hasUnlockedSpell(EntityPlayer player)
	{
		return SpiritUtil.getSpirit(player).hasUnlockedSpell(this);
	}

	public boolean canCast(EntityPlayer player)
	{
		 return SpiritUtil.hasSpirit(player, cost) && !CooldownUtil.isOnCooldown(player, getRegistryName().toString()) && !player.isSpectator() && SpiritUtil.hasGrimoire(player);
	}

	public ResourceLocation getSpellIcon()
	{
		return null;
	}
}
