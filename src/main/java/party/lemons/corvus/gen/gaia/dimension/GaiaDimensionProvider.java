package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GaiaDimensionProvider extends WorldProvider
{
	public GaiaDimensionProvider()
	{
		super();
	}

	protected void init()
	{
		super.init();
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkGeneratorGaia(world, getSeed(), getSpawnCoordinate());
	}

	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return 0;
	}

	@Nullable
	@Override
	public String getSaveFolder()
	{
		return "gaia";
	}

	@Override
	public DimensionType getDimensionType()
	{
		return GaiaDimension.TYPE;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	public BlockPos getSpawnCoordinate()
	{
		return new BlockPos(0, 140, 0);
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors)
	{

	}
}
