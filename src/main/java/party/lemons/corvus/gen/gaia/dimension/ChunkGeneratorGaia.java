package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorGaia implements IChunkGenerator
{
	private static final IBlockState STONE = Blocks.STONE.getDefaultState();

	private final Random rand;
	private NoiseGeneratorOctaves lperlinNoise1;
	private NoiseGeneratorOctaves lperlinNoise2;
	private NoiseGeneratorOctaves perlinNoise1;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorPerlin surfaceNoise;
	private final World world;
	private final BlockPos spawnPoint;
	private MapGenBase caveGenerator = new MapGenGaiaCaves();

	public ChunkGeneratorGaia(World world, long seed, BlockPos spawn)
	{
		this.rand = new Random(seed);
		this.spawnPoint = spawn;
		this.world = world;

		this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

	}
	private int chunkX = 0;
	private int chunkZ = 0;
	private double[] buffer;
	private Biome[] jungleA = new Biome[]{Biomes.JUNGLE};

	@Override
	public Chunk generateChunk(int x, int z)
	{
		this.chunkX = x; this.chunkZ = z;
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);

		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.setBlocksInChunk(x, z, chunkprimer);
		this.buildSurfaces(chunkprimer);
		this.caveGenerator.generate(this.world, x, z, chunkprimer);


		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		byte jungleID = (byte) Biome.getIdForBiome(Biomes.JUNGLE);
		for (int i = 0; i < abyte.length; ++i)
		{
			abyte[i] = jungleID;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	private double[] depthBuffer = new double[256];
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
	{
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
		double d0 = 0.03125D;
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				Biome biome = Biomes.JUNGLE;
				biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
			}
		}
	}

	private void setBlocksInChunk(int x, int z, ChunkPrimer primer)
	{
		int _yy = 100;
		int _si = 8;

		this.buffer = this.getHeights(this.buffer, x * 2, 0, z * 2, _si, _yy, _si);

		for (int i1 = 0; i1 < 2; ++i1)
		{
			for (int j1 = 0; j1 < 2; ++j1)
			{
				for (int k1 = 0; k1 < 32; ++k1)
				{
					double d0 = 0.25D;
					double d1 = this.buffer[((i1 + 0) * _si + j1 + 0) * _yy + k1 + 0];
					double d2 = this.buffer[((i1 + 0) * _si + j1 + 1) * _yy + k1 + 0];
					double d3 = this.buffer[((i1 + 1) * _si + j1 + 0) * _yy + k1 + 0];
					double d4 = this.buffer[((i1 + 1) * _si + j1 + 1) * _yy + k1 + 0];
					double d5 = (this.buffer[((i1 + 0) * _si + j1 + 0) * _yy + k1 + 1] - d1) * 0.25D;
					double d6 = (this.buffer[((i1 + 0) * _si + j1 + 1) * _yy + k1 + 1] - d2) * 0.25D;
					double d7 = (this.buffer[((i1 + 1) * _si + j1 + 0) * _yy + k1 + 1] - d3) * 0.25D;
					double d8 = (this.buffer[((i1 + 1) * _si + j1 + 1) * _yy + k1 + 1] - d4) * 0.25D;

					for (int l1 = 0; l1 < 4; ++l1)
					{
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * 0.125D;
						double d13 = (d4 - d2) * 0.125D;

						for (int i2 = 0; i2 < 8; ++i2)
						{
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * 0.125D;

							for (int j2 = 0; j2 < 8; ++j2)
							{
								IBlockState iblockstate = Blocks.AIR.getDefaultState();

								if (d15 > 0.0D)
								{
									iblockstate = STONE;
								}

								int k2 = i2 + i1 * 8;
								int l2 = l1 + k1 * 4;
								int i3 = j2 + j1 * 8;
								primer.setBlockState(k2, l2, i3, iblockstate);
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	double[] pnr;
	double[] ar;
	double[] br;
	private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_)
	{
		if (p_185963_1_ == null)
		{
			p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
		}

		double d0 = 684.412D;
		double d1 = 684.412D;
		d0 = d0 * 2.0D;
		this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
		this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
		this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
		int i = p_185963_2_ / 2;
		int j = p_185963_4_ / 2;
		int k = 0;

		for (int l = 0; l < p_185963_5_; ++l)
		{
			for (int i1 = 0; i1 < p_185963_7_; ++i1)
			{
				float f = this.getIslandHeightValue(i, j, l, i1);

				for (int j1 = 0; j1 < p_185963_6_; ++j1)
				{
					double d2 = this.ar[k] / 512.0D;
					double d3 = this.br[k] / 512.0D;
					double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
					double d4;

					if (d5 < 0.0D)
					{
						d4 = d2;
					}
					else if (d5 > 1.0D)
					{
						d4 = d3;
					}
					else
					{
						d4 = d2 + (d3 - d2) * d5;
					}

					d4 = d4 - 8.0D;
					d4 = d4 + (double)f;
					int k1 = 2;

					if (j1 > p_185963_6_ / 2 - k1)
					{
						double d6 = (double)((float)(j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
						d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
						d4 = d4 * (1.0D - d6) + -3000.0D * d6;
					}

					k1 = 8;

					if (j1 < k1)
					{
						double d7 = (double)((float)(k1 - j1) / ((float)k1 - 1.0F));
						d4 = d4 * (1.0D - d7) + -30.0D * d7;
					}

					p_185963_1_[k] = d4;
					++k;
				}
			}
		}

		return p_185963_1_;
	}

	private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_)
	{
		float f = (float)(p_185960_1_ * 2 + p_185960_3_);
		float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
		float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;

		if (f2 > 80.0F)
		{
			f2 = 80.0F;
		}

		if (f2 < -100.0F)
		{
			f2 = -100.0F;
		}

		return f2;
	}

	public void buildSurfaces(ChunkPrimer primer)
	{
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, this.chunkX, this.chunkZ, primer, this.world)) return;
		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				int k = 1;
				int l = -1;
				IBlockState iblockstate = Blocks.GRASS.getDefaultState();
				IBlockState iblockstate1 = Blocks.DIRT.getDefaultState();

				for (int i1 = 127; i1 >= 0; --i1)
				{
					IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

					if (iblockstate2.getMaterial() == Material.AIR)
					{
						l = -1;
					}
					else if (iblockstate2.getBlock() == Blocks.STONE)
					{
						if (l == -1)
						{
							l = 1;

							if (i1 >= 0)
							{
								primer.setBlockState(i, i1, j, iblockstate);
							}
							else
							{
								primer.setBlockState(i, i1, j, iblockstate1);
							}
						}
						else if (l > 0)
						{
							--l;
							primer.setBlockState(i, i1, j, iblockstate1);
						}
					}
				}
			}
		}
	}

	public void populate(int x, int z)
	{
		BlockFalling.fallInstantly = true;
		int i = x * 16;
		int j = z * 16;
		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
		this.rand.setSeed(this.world.getSeed());
		long k = this.rand.nextLong() / 2L * 2L + 1L;
		long l = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
		boolean flag = false;

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE))
		{
			int i1 = this.rand.nextInt(16) + 8;
			int j1 = this.rand.nextInt(256);
			int k1 = this.rand.nextInt(16) + 8;
			(new WorldGenLakes(Blocks.WATER)).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
		}

		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.LAVA))
		{
			int i1 = this.rand.nextInt(16) + 8;
			int j1 = this.rand.nextInt(100);
			int k1 = this.rand.nextInt(16) + 8;
			(new WorldGenLakes(Blocks.LAVA)).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
		}


		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON))
		{
			for (int j2 = 0; j2 < 8; ++j2)
			{
				int i3 = this.rand.nextInt(16) + 8;
				int l3 = this.rand.nextInt(256);
				int l1 = this.rand.nextInt(16) + 8;
				(new WorldGenDungeons()).generate(this.world, this.rand, blockpos.add(i3, l3, l1));
			}
		}

		biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
			WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);

		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		return this.world.getBiome(pos).getSpawnableList(creatureType);
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z)
	{

	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
	{
		return false;
	}
}
