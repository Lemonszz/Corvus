package party.lemons.corvus.capability.crow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageSyncCrow;
import party.lemons.lemonlib.handler.cooldown.CooldownUtil;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CrowCapability
{
	@CapabilityInject(ICrow.class)
	public static final Capability<ICrow> CAPABILITY = null;

	public static void sync(EntityPlayer entity, Collection<? extends EntityPlayer> receivers)
	{
		if(entity.world.isRemote)
			return;

		MessageSyncCrow msg = new MessageSyncCrow(entity, entity.getCapability(CAPABILITY, null).isCrow());
		receivers.forEach(p -> CorvusNetwork.INSTANCE.sendTo(msg, (EntityPlayerMP) p));
	}

	@SubscribeEvent
	public static void onPlayerJoin(EntityJoinWorldEvent event)
	{
		if (event.getWorld().isRemote || !(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer target = (EntityPlayer) event.getEntity();

		sync(target, Collections.singletonList(target));
	}


	@SubscribeEvent
	public static void onStartTracking(PlayerEvent.StartTracking event)
	{
		if (event.getTarget().world.isRemote || !(event.getTarget() instanceof EntityPlayer))
		{
			return;
		}

		sync((EntityPlayer) event.getTarget(), Collections.singletonList(event.getEntityPlayer()));
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START || event.player.world.isRemote)
			return;

		boolean isCrow = event.player.getCapability(CrowCapability.CAPABILITY, null).isCrow();
		if(isCrow)
		{
			spawnParticles((WorldServer) event.player.world, event.player, 2, 0.25F);
			event.player.fallDistance = 0;
		}

		if(isCrow && (event.player.onGround || !CooldownUtil.isOnCooldown(event.player, "crow_cd")))
		{
			event.player.getCapability(CrowCapability.CAPABILITY,  null).setCrow(event.player, false);
			spawnParticles((WorldServer) event.player.world, event.player, 70, 0.5F);
		}
		sync(event.player);
	}

	public static void spawnParticles(WorldServer world, Entity player, int amt, float spread)
	{
		double xPos = player.posX - (player.width / 4F);
		double yPos = player.posY + (player.height / 2F);
		double zPos = player.posZ - (player.width / 4F);

		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, xPos, yPos, zPos, amt, spread, spread, spread, 0);
	}

	public static void sync(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		WorldServer worldServer = (WorldServer) player.world;

		sync(player, worldServer.getEntityTracker().getTrackingPlayers(player));
		sync(player, Collections.singletonList(player));
	}

	public static class CapabilitySpirit implements Capability.IStorage<ICrow>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ICrow> capability, ICrow instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ICrow> capability, ICrow instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}
