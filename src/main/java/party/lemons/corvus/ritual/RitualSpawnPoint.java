package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualSpawnPoint extends Ritual
{
	public RitualSpawnPoint(Ingredient of, Ingredient of1, Ingredient of2)
	{
		super(of,of,of);
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			if(player.getSpawnDimension() == world.provider.getDimension())
			{
				boolean hasBed = true;
				BlockPos bedLocation = player.getBedLocation();
				if(bedLocation == null || player.getBedSpawnLocation(world, bedLocation, false) == null)
					hasBed = false;

				BlockPos respawnPos;
				if(!hasBed)
				{
					BlockPos blockpos = world.provider.getRandomizedSpawnPoint();
					respawnPos = world.getTopSolidOrLiquidBlock(blockpos);
				}
				else
				{
					respawnPos = player.getBedSpawnLocation(world, bedLocation, false);
				}

				player.setPositionAndUpdate(respawnPos.getX() + 0.5F, respawnPos.getY(), respawnPos.getZ() + 0.5F);
			}
		}
	}
}
