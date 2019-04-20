package party.lemons.corvus.handler;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.gaiabreath.GaiaBreathUtil;
import party.lemons.corvus.capability.gaiabreath.IGaiaBreath;
import party.lemons.corvus.gen.gaia.dimension.GaiaDimension;
import party.lemons.corvus.init.CorvusPotions;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class GaiaBreathHandler
{
	public static boolean canBreatheInGaia(EntityPlayer player)
	{
		PotionEffect breath_potion = player.getActivePotionEffect(CorvusPotions.BREATH_OF_GAIA);
		boolean has_potion = breath_potion != null && breath_potion.getDuration() > 0;


		return  has_potion || player.isSpectator() || player.isCreative();
	}

	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.END)
			return;
		if(event.player.dimension != GaiaDimension.GAIA_ID)
			return;

		EntityPlayer player = event.player;
		boolean canBreathe = canBreatheInGaia(player);

		if(!canBreathe)
		{
			if(player.ticksExisted % 4 == 0)
			{
				IGaiaBreath breath = GaiaBreathUtil.getGaiaBreath(player);
				int air = breath.getBreath();

				int i = EnchantmentHelper.getRespirationModifier(player);
				int change = i > 0 && player.getRNG().nextInt(i + 1) > 0 ? air : air - 1;
				breath.setBreath(change);

				if(player.isInsideOfMaterial(Material.WATER))
				{
					player.setAir(breath.getBreath());
				}

				if(!player.world.isRemote && event.player.ticksExisted % 5 == 0) GaiaBreathUtil.syncGaiaBreath(player);

				if(!player.world.isRemote && breath.getBreath() <= 0)
				{
					if(player.ticksExisted % 20 == 0)
					{
						player.attackEntityFrom(DamageSource.DROWN, 1);
					}
				}
			}
		}
		else
		{
			IGaiaBreath breath = GaiaBreathUtil.getGaiaBreath(player);
			boolean changed = breath.getBreath() == 300;

			if(!changed && !player.world.isRemote)
			{
				breath.setBreath(300);
				GaiaBreathUtil.syncGaiaBreath(player);
			}
		}
	}

	@Mod.EventBusSubscriber(modid = Corvus.MODID, value = Side.CLIENT)
	public static class GaiaBreathHandlerClient
	{
		@SubscribeEvent
		public static void onRenderGui(RenderGameOverlayEvent.Post event)
		{
			Minecraft mc = Minecraft.getMinecraft();
			if(mc.world.provider.getDimension() != GaiaDimension.GAIA_ID)
			{
				GuiIngameForge.renderAir = true;
				return;
			}

			if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
			{
				GuiIngameForge.renderAir = true;
				EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
				GlStateManager.enableBlend();

				int left = event.getResolution().getScaledWidth() / 2 + 91;
				int top = event.getResolution().getScaledHeight() - GuiIngameForge.right_height;

				if (!player.isInsideOfMaterial(Material.WATER))
				{
					IGaiaBreath breath = GaiaBreathUtil.getGaiaBreath(player);
					int air = breath.getBreath();
					int full = MathHelper.ceil((double)(air - 2) * 10.0D / 300.0D);
					int partial = MathHelper.ceil((double)air * 10.0D / 300.0D) - full;

					for (int i = 0; i < full + partial; ++i)
					{
						GuiUtils.drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9, 0);
					}
					GuiIngameForge.renderAir = false;
				}

				GlStateManager.disableBlend();
			}
		}
	}
}
