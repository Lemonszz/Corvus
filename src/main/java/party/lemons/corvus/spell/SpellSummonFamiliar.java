package party.lemons.corvus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.entity.EntityFamiliar;

public class SpellSummonFamiliar extends Spell
{
	private static final ResourceLocation ICON = new ResourceLocation(Corvus.MODID, "textures/spell/wolf-howl.png");

	public SpellSummonFamiliar(int cost, int cooldown)
	{
		super(cost, cooldown);
	}

	@Override
	public void performSpell(EntityPlayer player)
	{
		if(!player.world.isRemote)
		{
			CrowCapability.spawnParticles((WorldServer) player.world, player, 75, 0.5F);
			EntityFamiliar familiar = new EntityFamiliar(player.world);
			familiar.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);

			player.world.spawnEntity(familiar);
		}
	}

	@Override
	public boolean hasUnlockedSpell(EntityPlayer player)
	{
		return false;
	}

	@Override
	public ResourceLocation getSpellIcon()
	{
		return ICON;
	}
}
