package party.lemons.corvus.capability.progression;

import party.lemons.corvus.progression.Progression;

import java.util.List;

public interface IPlayerProgression
{
	boolean hasProgression(Progression progression);
	boolean unlockProgression(Progression progression);
	boolean canUnlockProgression(Progression progression);
	List<Progression> getUnlockedProgression();
}
