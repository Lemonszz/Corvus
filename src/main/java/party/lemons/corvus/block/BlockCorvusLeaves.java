package party.lemons.corvus.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.lemonlib.item.IItemModel;


import java.util.Random;
import java.util.function.Supplier;
public class BlockCorvusLeaves extends BlockLeaves implements IItemModel, IShearable
{
	private final Supplier<Item> saplingItem, specialDrop;

	public BlockCorvusLeaves(Supplier<Item> saplingItem, Supplier<Item> specialDrop)
	{
		super();
		this.setTickRandomly(true);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);

		this.saplingItem = saplingItem;
		this.specialDrop = specialDrop;
		this.setDefaultState(this.getDefaultState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return saplingItem.get();
	}

	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
	{
		if (worldIn.rand.nextInt(chance) < 20)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(specialDrop.get()));
		}
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this, 1, 0);
	}

	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		items.add(new ItemStack(this, 1, 0));
	}

	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(DECAYABLE, (meta & 1) == 0)
				.withProperty(CHECK_DECAY, (meta & 2) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (!state.getValue(DECAYABLE)) {
			meta |= 1;
		}
		if (state.getValue(CHECK_DECAY)) {
			meta |= 2;
		}
		return meta;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return Blocks.LEAVES.getDefaultState().isOpaqueCube();
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return Blocks.LEAVES.getRenderLayer();
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta)
	{
		return null;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
			return !(blockAccess.getBlockState(pos.offset(side)).getBlock() instanceof BlockLeaves);
		}
		return true;
	}

	@Override
	public NonNullList<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
	{
		return NonNullList.withSize(1, new ItemStack(this, 1, 1));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
	}

}
