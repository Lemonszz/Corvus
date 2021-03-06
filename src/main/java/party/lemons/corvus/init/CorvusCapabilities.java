package party.lemons.corvus.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.capability.crow.CrowContainer;
import party.lemons.corvus.capability.crow.CrowContainerProvider;
import party.lemons.corvus.capability.crow.ICrow;
import party.lemons.corvus.capability.gaiabreath.*;
import party.lemons.corvus.capability.inventoryswitch.IInventorySwitch;
import party.lemons.corvus.capability.inventoryswitch.InventorySwitchCapability;
import party.lemons.corvus.capability.inventoryswitch.InventorySwitchContainer;
import party.lemons.corvus.capability.inventoryswitch.InventorySwitchProvider;
import party.lemons.corvus.capability.progression.*;
import party.lemons.corvus.capability.projection.IProjection;
import party.lemons.corvus.capability.projection.ProjectionCapability;
import party.lemons.corvus.capability.projection.ProjectionCapabilityProvider;
import party.lemons.corvus.capability.projection.ProjectionContainer;
import party.lemons.corvus.capability.spirit.*;
import party.lemons.corvus.handler.AdvancementHandler;
import party.lemons.corvus.network.MessageSyncAwakened;
import party.lemons.lemonlib.event.InitEvent;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusCapabilities
{
	@SubscribeEvent
	public static void onPreInit(InitEvent.Pre event)
	{
		CapabilityManager.INSTANCE.register(ISpirit.class, new SpiritCapability.CapabilitySpirit(), SpiritContainer::new);
		CapabilityManager.INSTANCE.register(ICrow.class, new CrowCapability.CapabilitySpirit(), CrowContainer::new);
		CapabilityManager.INSTANCE.register(IProjection.class, new ProjectionCapability.CapabilityCrow(), ProjectionContainer::new);
		CapabilityManager.INSTANCE.register(IGaiaBreath.class, new GaiaBreathCapability.CapabilityGaiaBreath(), GaiaBreathContainer::new);
		CapabilityManager.INSTANCE.register(IInventorySwitch.class, new InventorySwitchCapability.CapabilityInventorySwitch(), InventorySwitchContainer::new);
		CapabilityManager.INSTANCE.register(IPlayerProgression.class, new PlayerProgressionCapability.CapabilityPlayerProgression(), PlayerProgression::new);
	}

	@SubscribeEvent
	public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(Corvus.MODID, "spirit"), new SpiritContainerProvider(new SpiritContainer()));
			event.addCapability(new ResourceLocation(Corvus.MODID, "crow"), new CrowContainerProvider(new CrowContainer()));
			event.addCapability(new ResourceLocation(Corvus.MODID, "projection"), new ProjectionCapabilityProvider(new ProjectionContainer()));
			event.addCapability(new ResourceLocation(Corvus.MODID, "gaia_breath"), new GaiaBreathContainerProvider(new GaiaBreathContainer()));
			event.addCapability(new ResourceLocation(Corvus.MODID, "inventory_switch"), new InventorySwitchProvider(new InventorySwitchContainer()));
			event.addCapability(new ResourceLocation(Corvus.MODID, "player_progression"), new PlayerProgressionProvider(new PlayerProgression()));
		}
	}

	@SubscribeEvent
	public static void onJoinWorldEvent(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			SpiritUtil.syncSpirit(player);
			GaiaBreathUtil.syncGaiaBreath(player);
			ProgressionUtil.syncProgression(player);

			if(player instanceof EntityPlayerMP)
				CorvusNetwork.INSTANCE.sendTo(new MessageSyncAwakened(SpiritUtil.getSpirit(player).isAwakened()), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		SpiritContainer container = (SpiritContainer) SpiritUtil.getSpirit(event.getOriginal());
		NBTTagCompound tags = (NBTTagCompound) SpiritCapability.CAPABILITY.writeNBT(container, null);
		SpiritContainer containerNew = (SpiritContainer) SpiritUtil.getSpirit(event.getEntityPlayer());
		SpiritCapability.CAPABILITY.readNBT(containerNew, null, tags);
		SpiritUtil.syncSpirit(event.getEntityPlayer());

		if(event.getEntityPlayer() instanceof EntityPlayerMP)
		{
			CorvusNetwork.INSTANCE.sendTo(new MessageSyncAwakened(
					AdvancementHandler.hasAdvancement(
							(EntityPlayerMP) event.getEntityPlayer(),
							new ResourceLocation(Corvus.MODID, "corvus/awaken"))),
					(EntityPlayerMP) event.getEntityPlayer());
		}

		IInventorySwitch inventorySwitch = event.getOriginal().getCapability(InventorySwitchCapability.CAPABILITY, null);
		NBTTagList tagList = inventorySwitch.serializeNBT();

		IInventorySwitch inventorySwitchNew = event.getEntityPlayer().getCapability(InventorySwitchCapability.CAPABILITY, null);
		inventorySwitchNew.deserializeNBT(tagList);

		IPlayerProgression oldProgress = ProgressionUtil.getProgression(event.getOriginal());
		NBTBase progressionList = PlayerProgressionCapability.CAPABILITY.writeNBT(oldProgress, null);
		IPlayerProgression newProgress = ProgressionUtil.getProgression(event.getEntityPlayer());
		PlayerProgressionCapability.CAPABILITY.readNBT(newProgress , null, progressionList);
		ProgressionUtil.syncProgression(event.getEntityPlayer());
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if(event.player instanceof EntityPlayerMP)
		{
			CorvusNetwork.INSTANCE.sendTo(new MessageSyncAwakened(
							AdvancementHandler.hasAdvancement(
									(EntityPlayerMP) event.player,
									new ResourceLocation(Corvus.MODID, "corvus/awaken"))),
					(EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		if(event.player instanceof EntityPlayerMP)
		{
			CorvusNetwork.INSTANCE.sendTo(new MessageSyncAwakened(
							AdvancementHandler.hasAdvancement(
									(EntityPlayerMP) event.player,
									new ResourceLocation(Corvus.MODID, "corvus/awaken"))),
					(EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.END || event.player.world.isRemote)
			return;

		EntityPlayer player = event.player;

		if(player.ticksExisted % 20 == 0)
		{
			World world = event.player.world;
			BlockPos pos = new BlockPos(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
			boolean canSeeSky = world.canSeeSky(pos);
			Biome biome = world.getBiome(pos);

			if(!world.isDaytime() && world.getCurrentMoonPhaseFactor() == 1F && canSeeSky)
			{
				SpiritUtil.changeSpirit(player, 5);
			}
			else if((BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) ||
					BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER) ||
					BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) || player.posY >= 100)
			{
				SpiritUtil.changeSpirit(player, 1);
			}
		}
	}
}
