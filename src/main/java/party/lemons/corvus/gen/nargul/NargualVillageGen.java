package party.lemons.corvus.gen.nargul;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NargualVillageGen extends WorldGenerator
{
	public static List<Biome> VILLAGE_SPAWN_BIOMES = new ArrayList<>();
	public static List<ResourceLocation> BUILDINGS = new ArrayList<>();
	public static int MAX_DISTANCE = 25;
	public static int MIN_DISTANCE = 20;

	@Override
	public boolean generate(World world, Random random, BlockPos blockPos)
	{
		List<BlockPos> positions = new ArrayList<>();

		BlockPos center = blockPos;
		int houses = 2 + random.nextInt(4);

		for(int i = 0; i < houses; i++)
		{
			PlacementSettings settings = new PlacementSettings()
					.setRotation((Rotation) RandomUtil.randomFromArray(random, Rotation.values()))
					.setMirror((Mirror) RandomUtil.randomFromArray(random, Mirror.values()));

			Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), BUILDINGS.get(random.nextInt(BUILDINGS.size())));
			boolean canPlace = false;
			int attempts = 0;

			BlockPos pos = center.add(RandomUtil.randomRange(random, -MIN_DISTANCE, MAX_DISTANCE), 0, RandomUtil.randomRange(random, -MIN_DISTANCE, MAX_DISTANCE));

			while(!canPlace && attempts < 50)
			{
				boolean doIt = true;
				for(BlockPos checkPos : positions)
				{
					if(checkPos.distanceSq(pos) < 150)
					{
						pos = center.add(RandomUtil.randomRange(random, -MIN_DISTANCE, MAX_DISTANCE), 0, RandomUtil.randomRange(random, -MIN_DISTANCE, MAX_DISTANCE));
						doIt = false;
						break;
					}
				}

				if(doIt)
				{
					canPlace = true;
				}
				attempts++;
			}

			if(canPlace)
			{
				positions.add(pos);
				pos = world.getTopSolidOrLiquidBlock(pos);
				IBlockState state = world.getBlockState(pos);
				if(state instanceof BlockBush)
					pos = pos.down();

				template.addBlocksToWorld(world, pos, settings);
			}
		}

		return true;
	}

	static
	{
		VILLAGE_SPAWN_BIOMES.add(Biomes.JUNGLE);
		VILLAGE_SPAWN_BIOMES.add(Biomes.JUNGLE_EDGE);
		VILLAGE_SPAWN_BIOMES.add(Biomes.JUNGLE_HILLS);
		VILLAGE_SPAWN_BIOMES.add(Biomes.MUTATED_JUNGLE);
		VILLAGE_SPAWN_BIOMES.add(Biomes.MUTATED_JUNGLE_EDGE);

		BUILDINGS.add(new ResourceLocation(Corvus.MODID, "jungle_house_1"));
		BUILDINGS.add(new ResourceLocation(Corvus.MODID, "jungle_house_2"));
	}
}
