package party.lemons.corvus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusPotions;
import party.lemons.corvus.init.CorvusSounds;

public class SpellConceal extends Spell
{
	private static final ResourceLocation ICON = new ResourceLocation(Corvus.MODID, "textures/spell/cultist.png");

	public SpellConceal(int cost, int cooldown)
	{
		super(cost, cooldown);
	}

	@Override
	public void performSpell(EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(CorvusPotions.CONCEALMENT, 30 * 20, 0, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 30 * 20, 0, true, false));
		player.world.playSound(null, player.getPosition(), CorvusSounds.CONCEAL, SoundCategory.PLAYERS, 0.4F, 1F);
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
