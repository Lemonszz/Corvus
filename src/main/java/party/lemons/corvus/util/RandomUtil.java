package party.lemons.corvus.util;

import java.util.Random;

public class RandomUtil
{
	public static Object randomFromArray(Random random, Object[] arr)
	{
		return arr[random.nextInt(arr.length)];
	}

	public static int randomRange(Random random, int min, int max)
	{
		return random.nextInt(max + 1 - min) + min;
	}
}
