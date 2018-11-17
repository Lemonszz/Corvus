package party.lemons.corvus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusSounds;

public class SpellLeap extends Spell
{
	private static final ResourceLocation ICON = new ResourceLocation(Corvus.MODID, "textures/spell/crow-dive.png");

	public SpellLeap(int cost, int cooldown)
	{
		super(cost, cooldown);
	}

	@Override
	public void performSpell(EntityPlayer player)
	{
		double jumpX = player.posX+ 5 * player.getLookVec().x;
		double jumpZ = player.posZ+ 5 * player.getLookVec().z;

		double d0 = jumpX - player.posX;
		double d1 = jumpZ - player.posZ;
		float f = MathHelper.sqrt(d0 * d0 + d1 * d1);

		if ((double)f >= 1.0E-4D)
		{
			player.motionX += d0 / (double)f * 4D * 0.800000011920929D + player.motionX * 0.20000000298023224D;
			player.motionZ += d1 / (double)f * 4D * 0.800000011920929D + player.motionZ * 0.20000000298023224D;
		}

		player.motionY = 0.5F;
		player.velocityChanged = true;

		if(!player.world.isRemote)
		{
			player.getCapability(CrowCapability.CAPABILITY, null).setCrow(player, true);
			CrowCapability.sync(player);
			CrowCapability.spawnParticles((WorldServer) player.world, player, 70, 0.5F);
			player.world.playSound(null, player.getPosition(), CorvusSounds.CROW_CAW, SoundCategory.PLAYERS, 2F, 1F);
		}
	}

	@Override
	public boolean hasUnlockedSpell(EntityPlayer player)
	{
		return SpiritUtil.getSpirit(player).hasUnlockedSpell(this);
	}

	@Override
	public ResourceLocation getSpellIcon()
	{
		return ICON;
	}
}
