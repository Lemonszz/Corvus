package party.lemons.corvus.ritual;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.handler.EffectHandler;
import party.lemons.corvus.init.CorvusSounds;

public class RitualItemSummon extends Ritual
{
	private final ItemStack SUMMON_STACK;

	public RitualItemSummon(ItemStack summonStack, Ingredient... ingredients)
	{
		super(ingredients);

		SUMMON_STACK = summonStack;
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			float pitchOffset = (world.rand.nextFloat() / 5) * (world.rand.nextBoolean() ? 1 : -1);
			world.playSound(null, pos, CorvusSounds.ITEM_SUMMON, SoundCategory.BLOCKS, 1F, 1F + pitchOffset);

			EffectHandler.performEffect(EffectHandler.STUNNING_DAHLIA, pos, world);
			world.setBlockToAir(pos);
			EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SUMMON_STACK.copy());
			entityitem.motionX = 0;
			entityitem.motionY = 0.25F;
			entityitem.motionZ = 0;
			entityitem.velocityChanged = true;
			entityitem.setDefaultPickupDelay();
			world.spawnEntity(entityitem);
		}
	}
}
