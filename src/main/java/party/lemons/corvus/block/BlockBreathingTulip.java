package party.lemons.corvus.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.corvus.block.tilentity.TileEntityBreathingTulip;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBreathingTulip extends BlockCorvusFlower
{
	@Override
	public boolean hasModel()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof TileEntityBreathingTulip)
		{
			((TileEntityBreathingTulip) te).setDamage(stack.getItemDamage());
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots)
		{
			ItemStack stack = new ItemStack(this, 1, this.damageDropped(state));
			TileEntityBreathingTulip te = (TileEntityBreathingTulip) worldIn.getTileEntity(pos);
			System.out.println(te.getDamage());
			stack.setItemDamage(te.getDamage());
			spawnAsEntity(worldIn, pos, stack);
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{

	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityBreathingTulip();
	}
}
