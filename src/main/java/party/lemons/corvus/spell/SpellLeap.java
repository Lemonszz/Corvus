package party.lemons.corvus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
		Vec3d vec = player.getLookVec();
		double jumpX = vec.x * 5D;
		double jumpY = vec.y * 2D;
		double jumpZ = vec.z * 5D;

		player.motionX = jumpX;
		player.motionY = jumpY < 0.4 ? 0.4 : jumpY;
		player.motionZ = jumpZ;
		player.velocityChanged = true;

		if(!player.world.isRemote)
		{
			player.getCapability(CrowCapability.CAPABILITY, null).setCrow(player, true);
			CrowCapability.sync(player);
			CrowCapability.spawnParticles((WorldServer) player.world, player, 70, 0.5F);
			player.world.playSound(null, player.getPosition(), CorvusSounds.CROW_CAW, SoundCategory.PLAYERS, 2F, 1F);
			player.world.playSound(null, player.getPosition(), CorvusSounds.WIND, SoundCategory.PLAYERS, 2F, 1F);
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
