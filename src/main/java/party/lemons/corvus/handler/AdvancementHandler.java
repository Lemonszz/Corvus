package party.lemons.corvus.handler;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.command.AdvancementCommand;
import net.minecraft.command.CommandException;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.init.CorvusProgression;
import party.lemons.corvus.init.CorvusSpells;
import party.lemons.corvus.network.MessageSyncAwakened;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class AdvancementHandler
{
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event)
	{
		if(!event.getEntity().world.isRemote && event.getEntity() instanceof EntityWolf)
		{
			if(((EntityWolf) event.getEntity()).isChild() && ((EntityWolf) event.getEntity()).getOwnerId() != null)
			{
				if(event.getSource().getTrueSource() instanceof EntityPlayer)
				{
					EntityPlayerMP playerMP = (EntityPlayerMP) event.getSource().getTrueSource();

					if(!ProgressionUtil.hasProgression(playerMP, CorvusProgression.AWAKEN))
						return;

					ProgressionUtil.tryUnlockProgression(playerMP, CorvusProgression.KILL_WOLF);
					MinecraftServer server = event.getEntity().getServer();

					try
					{
						Advancement advancement = AdvancementCommand.findAdvancement(server, Corvus.MODID + ":corvus/kill_baby_wolf");
						AdvancementProgress advancementprogress = ((EntityPlayerMP)event.getSource().getTrueSource()).getAdvancements().getProgress(advancement);

						SpiritUtil.unlockSpell(playerMP, CorvusSpells.CANIS_FAMILIAR);
						if(advancementprogress.isDone())
							return;

						for (String s : advancementprogress.getRemaningCriteria())
						{
							playerMP.getAdvancements().grantCriterion(advancement, s);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onAdvancementUnlock(AdvancementEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();

		if(event.getAdvancement().getId().toString().equals("corvus:corvus/spirit"))
		{
			SpiritUtil.unlockSpell(player, CorvusSpells.CONCEAL);
		}

		if(!event.getEntityPlayer().world.isRemote && event.getAdvancement().getId().toString().equals("corvus:corvus/awaken"))
		{
				if(player instanceof EntityPlayerMP)
				{
					ISpirit spirit = SpiritUtil.getSpirit(player);
					spirit.setAwakened(true);
					CorvusNetwork.INSTANCE.sendTo(new MessageSyncAwakened(true), (EntityPlayerMP) player);
				}
		}
	}

	public static void unlockAdvancement(EntityPlayer player, ResourceLocation location)
	{
		if(player.world.isRemote)
			return;

		MinecraftServer server = player.getServer();

		try
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			Advancement advancement = AdvancementCommand.findAdvancement(server, location.toString());
			AdvancementProgress advancementprogress = playerMP.getAdvancements().getProgress(advancement);

			if(advancementprogress.isDone())
				return;

			for (String s : advancementprogress.getRemaningCriteria())
			{
				playerMP.getAdvancements().grantCriterion(advancement, s);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean hasAdvancement(EntityPlayerMP playerMP, ResourceLocation location)
	{
		Advancement advancement = null;
		try
		{
			advancement = AdvancementCommand.findAdvancement(playerMP.getServer(), location.toString());
			AdvancementProgress advancementprogress = playerMP.getAdvancements().getProgress(advancement);
			if(advancementprogress.isDone())
			{
				return true;
			}

		}catch(CommandException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
