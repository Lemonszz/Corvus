package party.lemons.corvus.capability.progression;

import party.lemons.corvus.progression.Progression;

import java.util.ArrayList;
import java.util.List;

public class PlayerProgression implements IPlayerProgression
{
	private List<Progression> unlockedProgressions;

	public PlayerProgression()
	{
		unlockedProgressions = new ArrayList<>();
	}

	@Override
	public boolean hasProgression(Progression progression)
	{
		return unlockedProgressions.contains(progression);
	}

	@Override
	public boolean unlockProgression(Progression progression)
	{
		if(!hasProgression(progression))
		{
			unlockedProgressions.add(progression);
			return true;
		}

		return false;
	}

	@Override
	public boolean canUnlockProgression(Progression progression)
	{
		Progression[] parents = progression.getParents();
		for(Progression parent : parents)
		{
			if(!hasProgression(parent))
				return false;
		}

		return true;
	}

	@Override
	public List<Progression> getUnlockedProgression()
	{
		return unlockedProgressions;
	}
}
