package party.lemons.corvus.capability.spirit;

import party.lemons.corvus.spell.Spell;

import java.util.List;

public interface ISpirit
{
	int getSpirit();
	int getMaxSprit();

	/**
	 * Sets player spirit, returns overflow amount
	 * @param newAmount
	 * @return
	 */
	int setSprit(int newAmount);

	boolean changeSprit(int amount);

	void setSpiritMax(int amount);

	void unlockSpell(Spell spell);
	boolean hasUnlockedSpell(Spell spell);
	List<Spell> getUnlockedSpells();

	Spell getActiveSpell();
	void setActiveSpell(Spell spell);
}
