package party.lemons.corvus.init;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityCrow;
import party.lemons.corvus.entity.EntityFamiliar;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusEntities
{
	private static int ID = 0;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityEntry> event)
	{
		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityCrow.class).spawn(EnumCreatureType.CREATURE, 5, 2, 6, BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS)).egg(0x3d3d3d, 0x4c4c4c).name("Crow").id(new ResourceLocation(Corvus.MODID, "crow"), ID++).tracker(80, 3, true).build());
		event.getRegistry().registerAll(EntityEntryBuilder.create().entity(EntityFamiliar.class).name("Familiar").id(new ResourceLocation(Corvus.MODID, "familiar"), ID++).tracker(80, 3, true).build());
	}


}
