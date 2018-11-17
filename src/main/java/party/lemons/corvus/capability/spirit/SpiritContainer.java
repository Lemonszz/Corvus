package party.lemons.corvus.capability.spirit;

import party.lemons.corvus.spell.Spell;

import java.util.ArrayList;
import java.util.List;

public class SpiritContainer implements ISpirit
{
	private int spirit, maxSpirit;
	private List<Spell> spellList;
	private Spell activeSpell;

	public SpiritContainer()
	{
		maxSpirit = 100;
		spirit = maxSpirit;
		spellList = new ArrayList<>();
	}

	@Override
	public int getSpirit()
	{
		return spirit;
	}

	@Override
	public int getMaxSprit()
	{
		return maxSpirit;
	}

	@Override
	public int setSprit(int newAmount)
	{
		int overflow = Math.max(0, (newAmount) - getMaxSprit());
		this.spirit = Math.min(getMaxSprit(), Math.max(0, newAmount));
		return overflow;
	}

	@Override
	public boolean changeSprit(int amount)
	{
		if(spirit == maxSpirit && amount > 0)
			return false;
		this.spirit = Math.min(getMaxSprit(), Math.max(0, spirit + amount));

		return true;
	}

	@Override
	public void setSpiritMax(int amount)
	{
		this.maxSpirit = amount;
	}

	@Override
	public void unlockSpell(Spell spell)
	{
		if(!hasUnlockedSpell(spell))
			spellList.add(spell);
	}

	@Override
	public boolean hasUnlockedSpell(Spell spell)
	{
		return spellList.contains(spell);
	}

	@Override
	public List<Spell> getUnlockedSpells()
	{
		return spellList;
	}

	@Override
	public Spell getActiveSpell()
	{
		return activeSpell;
	}

	@Override
	public void setActiveSpell(Spell spell)
	{
		if(hasUnlockedSpell(spell))
		{
			this.activeSpell = spell;
		}
	}
}
