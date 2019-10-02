package party.lemons.corvus.init;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityCrow;
import party.lemons.corvus.entity.EntityFamiliar;
import party.lemons.corvus.entity.EntityNagual;
import party.lemons.corvus.entity.EntityWendigo;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusEntities
{
	private static int ID = 0;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityEntry> event)
	{
		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityCrow.class).spawn(EnumCreatureType.CREATURE, 5, 2, 6, BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS)).egg(0x3d3d3d, 0x4c4c4c).name("Crow").id(new ResourceLocation(Corvus.MODID, "crow"), ID++).tracker(80, 3, true).build());
		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityFamiliar.class).name("Familiar").id(new ResourceLocation(Corvus.MODID, "familiar"), ID++).tracker(80, 3, true).build());
		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityNagual.class).name("Nagual").id(new ResourceLocation(Corvus.MODID, "nagual"), ID++).tracker(80, 3, true).egg(0x4f422e, 0xd9c19c).build());

		List<Biome> wendigoBiomes = new ArrayList<>();
		wendigoBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
		wendigoBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.END));

		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityWendigo.class).name("Wendigo").spawn(EnumCreatureType.MONSTER, 10, 1, 1,  wendigoBiomes).id(new ResourceLocation(Corvus.MODID, "wendigo"), ID++).tracker(80, 1, true).egg(0x50463b, 0x503d3b).build());
	}
}
