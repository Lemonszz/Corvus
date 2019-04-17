package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleporterGaia extends Teleporter
{
	private final boolean sendHome;

	public TeleporterGaia(WorldServer worldIn)
	{
		this(worldIn, false);
	}

	public TeleporterGaia(WorldServer worldIn, boolean sendHome)
	{
		super(worldIn);
		this.sendHome = sendHome;
	}

	@Override
	public void placeEntity(World world, Entity entityIn, float yaw)
	{
		if(world.isRemote)
			return;

		if(!sendHome)
		{
			BlockPos spawnPos = world.getTopSolidOrLiquidBlock(new BlockPos(0, 0, 0));
			entityIn.setLocationAndAngles(spawnPos.getX() + 0.5F, spawnPos.getY(), spawnPos.getZ() + 0.5F, entityIn.rotationYaw, 0.0F);
			entityIn.motionX = 0.0D;
			entityIn.motionY = 0.0D;
			entityIn.motionZ = 0.0D;
		}
		else if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			BlockPos respawnPos;
			boolean hasBed = true;

			BlockPos bedLocation = player.getBedLocation();
			if(bedLocation == null || player.getBedSpawnLocation(world, bedLocation, false) == null)
				hasBed = false;

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

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
	{
		return false;
	}
}
