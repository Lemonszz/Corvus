package party.lemons.corvus.capability.crow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityCrow;
import party.lemons.corvus.init.CorvusPotions;

@Mod.EventBusSubscriber(modid = Corvus.MODID, value = Side.CLIENT)
public class CrowClientHandler
{
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event)
	{
		ICrow crow = event.getEntityPlayer().getCapability(CrowCapability.CAPABILITY, null);

		if(event.getEntityPlayer().getActivePotionEffect(CorvusPotions.CONCEALMENT) != null)
		{
			event.setCanceled(true);
		}
		else if(crow != null && crow.isCrow())
		{
			event.setCanceled(true);
			renderCrowForPlayer(event.getEntityPlayer(), event.getPartialRenderTick());
		}
	}

	private static EntityCrow crow = null;

	private static void renderCrowForPlayer(EntityPlayer player, float delta)
	{
		if(crow == null)
		{
			crow = new EntityCrow(player.world);
		}

		crow.lastTickPosX = player.lastTickPosX;
		crow.lastTickPosZ = player.lastTickPosY;
		crow.lastTickPosZ = player.lastTickPosZ;

		crow.prevPosX = player.prevPosX;
		crow.prevPosY = player.prevPosY + player.getEyeHeight();
		crow.prevPosZ = player.prevPosZ;

		crow.posX = player.posX;
		crow.posY = player.posY + player.getEyeHeight();
		crow.posZ = player.posZ;

		crow.motionX = player.motionX;
		crow.motionY = player.motionY;
		crow.motionZ = player.motionZ;

		crow.ticksExisted = player.ticksExisted;
		crow.prevRotationYaw = player.prevRotationYaw;
		crow.prevRotationPitch = player.prevRotationPitch;
		crow.rotationPitch = player.rotationPitch;
		crow.rotationYaw = player.rotationYaw;

		crow.onUpdate();
		float f = crow.prevRotationYaw + (crow.rotationYaw - crow.prevRotationYaw) * delta;

		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		Render render = rendermanager.getEntityRenderObject(crow);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(-f, 0, 1, 0);
		render.doRender(crow, 0,player.getEyeHeight() / 2, 0 , f, delta);
		GlStateManager.popMatrix();
	}
}
