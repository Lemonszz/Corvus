package party.lemons.corvus.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.corvus.gen.gaia.dimension.GaiaDimension;
import party.lemons.corvus.gen.gaia.dimension.TeleporterGaia;
import party.lemons.corvus.init.CorvusPotions;

import java.util.List;

public class RitualToGaiasLand extends Ritual
{
	public RitualToGaiasLand(Ingredient... ingredients)
	{
		super(ingredients);
	}

	@Override
	public boolean doRitual(World world, BlockPos pos, EntityPlayer player)
	{
		return super.doRitual(world, pos, player) && world.provider.getDimension() != GaiaDimension.GAIA_ID;
	}

	@Override
	public void perform(World world, BlockPos pos, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(5, 5, 5));
			players.forEach(p ->
			{
				p.addPotionEffect(new PotionEffect(CorvusPotions.BREATH_OF_GAIA, 20 * 5, 0, false, false));
				p.changeDimension(GaiaDimension.GAIA_ID, new TeleporterGaia((WorldServer) world));
			});
		}
	}
}
