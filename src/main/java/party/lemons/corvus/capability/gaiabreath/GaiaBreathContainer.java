package party.lemons.corvus.capability.gaiabreath;

public class GaiaBreathContainer implements IGaiaBreath
{
	private int breath = 300;

	@Override
	public void setBreath(int newValue)
	{
		breath = Math.min(newValue, 300);
	}

	@Override
	public int getBreath()
	{
		return breath;
	}
}
