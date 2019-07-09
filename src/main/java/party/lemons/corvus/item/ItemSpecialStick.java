package party.lemons.corvus.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.corvus.gen.gaia.dimension.GaiaDimension;
import party.lemons.corvus.gen.gaia.dimension.TeleporterGaia;
import party.lemons.corvus.item.ItemModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSpecialStick extends ItemModel
{
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote && player.isSneaking())
		{
			if(player.world.provider.getDimension() == GaiaDimension.GAIA_ID)
				player.changeDimension(0, new TeleporterGaia((WorldServer) worldIn, true));
			else
				player.changeDimension(GaiaDimension.GAIA_ID, new TeleporterGaia((WorldServer) worldIn));

			player.getCooldownTracker().setCooldown(this, 20);
			return EnumActionResult.SUCCESS;
		}

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format("corvus.message.creativeonly"));
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
