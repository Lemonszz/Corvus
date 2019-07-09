package party.lemons.corvus.gen.biome;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.chunk.ChunkPrimer;
import party.lemons.corvus.gen.WorldGenRandomRockBlob;
import party.lemons.corvus.init.CorvusBlocks;

import java.util.Random;

public class BiomeLightJungle extends BiomeJungle
{
	private static final WorldGenRandomRockBlob FOREST_ROCK_GENERATOR = new WorldGenRandomRockBlob(1, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);

	public BiomeLightJungle()
	{
		super(false, new BiomeProperties("Light Jungle").setBaseHeight(0.125F).setHeightVariation(0.07F).setTemperature(0.95F).setRainfall(0.9F));

		this.decorator.treesPerChunk = 7;
		this.decorator.clayPerChunk = 3;
		this.decorator.flowersPerChunk = 5;

		this.flowers.clear();
		this.addFlower(CorvusBlocks.TWILIGHT_GARGET.getDefaultState(), 15);
		this.addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID), 5);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos)
	{
		int rockAmt = rand.nextInt(3);

		for(int i = 0; i < rockAmt; ++i) {
			int xx = rand.nextInt(16) + 8;
			int yy = rand.nextInt(16) + 8;
			BlockPos blockpos = worldIn.getHeight(pos.add(xx, 0, yy));
			FOREST_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
		}

		addDoublePlants(worldIn, rand, pos, rand.nextInt(7));
		addFlowers(worldIn, rand, pos);
		super.decorate(worldIn, rand, pos);
	}

	public void addFlowers(World worldIn, Random random, BlockPos pos)
	{
		for(int i = 0; i < 5; ++i)
		{
			int xx = random.nextInt(16) + 8;
			int zz = random.nextInt(16) + 8;
			int yy = worldIn.getHeight(pos.add(xx, 0, zz)).getY() + 32;
			if (yy > 0)
			{
				int placeY = random.nextInt(yy);
				BlockPos placePos = pos.add(xx, placeY, zz);
				plantFlower(worldIn, random, placePos);
			}
		}
	}

	public void addDoublePlants(World world, Random random, BlockPos pos, int amount)
	{
		for(int i = 0; i < amount; ++i)
		{
			int type = random.nextInt(3);
			if(type == 0) DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
			else if(type == 1) DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
			else if(type == 2) DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);

			for(int k = 0; k < 5; ++k)
			{
				int xx = random.nextInt(16) + 8;
				int yy = random.nextInt(16) + 8;
				int zz = random.nextInt(world.getHeight(pos.add(xx, 0, yy)).getY() + 32);
				if(DOUBLE_PLANT_GENERATOR.generate(world, random, new BlockPos(pos.getX() + xx, zz, pos.getZ() + yy)))
				{
					break;
				}
			}
		}
	}

	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
	{
		double weight = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
		BlockFlower.EnumFlowerType type = BlockFlower.EnumFlowerType.values()[(int)(weight * (double) BlockFlower.EnumFlowerType.values().length)];
		return type;
	}

	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
	{
		this.topBlock = Blocks.GRASS.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		if (noiseVal > 1.0D) {
			this.topBlock = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
		} else if (noiseVal > -0.125D) {
			this.topBlock = CorvusBlocks.MUD.getDefaultState();
		}

		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}
}
