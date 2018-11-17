package party.lemons.corvus.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.ritual.Ritual;
import party.lemons.corvus.ritual.RitualRegistry;
import party.lemons.lemonlib.item.IItemModel;

import java.util.Random;

public class BlockCandle extends Block implements IItemModel
{
	private static final double OFFSET = 0.375D;
	private static final AxisAlignedBB CANDLE_AABB = new AxisAlignedBB(OFFSET, 0, OFFSET, 1D - OFFSET, 0.5D, 1 - OFFSET);
	public static final PropertyBool ON = PropertyBool.create("on");

	public BlockCandle()
	{
		super(Material.CIRCUITS);
		this.setSoundType(SoundType.CLOTH);

		this.setDefaultState(this.blockState.getBaseState().withProperty(ON, false));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(!heldItem.isEmpty() && (heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE))
		{
			if(state.getValue(ON))
				return true;

			if(!worldIn.isRemote)
			{
				boolean didRitual = false;
				for(Ritual ritual : RitualRegistry.REGISTRY.getValuesCollection())
				{
					if(ritual.doRitual(worldIn, pos, playerIn))
					{
						didRitual = true;
						break;
					}
				}

				if(!didRitual)
					worldIn.setBlockState(pos, state.withProperty(ON, true));
				else
				{
					((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 100, 2F, 0, 2F, 0);
					((WorldServer)worldIn).spawnParticle(EnumParticleTypes.CRIT, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 100, 2F, 1.5F, 2F, 0);
				}

				if(!playerIn.isCreative())
				{
					if(heldItem.isItemStackDamageable())
					{
						heldItem.damageItem(1, playerIn);
					}else
					{
						heldItem.shrink(1);
					}
				}
			}
			spawnParticles(worldIn, pos);
		}
		else
		{
			if(state.getValue(ON))
			{
				worldIn.setBlockState(pos, state.withProperty(ON, false));
			}
		}
		return true;
	}


	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(!stateIn.getValue(ON))
			return;

		spawnParticles(worldIn, pos);
	}

	protected void spawnParticles(World world, BlockPos pos)
	{
		double pX = (double)pos.getX() + 0.5D;
		double pY = (double)pos.getY() + 0.6D;
		double pZ = (double)pos.getZ() + 0.5D;

		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
		for(int i = 0; i < 2; i++)
			world.spawnParticle(EnumParticleTypes.FLAME, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
	}

	@Deprecated
	public int getLightValue(IBlockState state)
	{
		return state.getValue(ON) ? 15 : 0;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CANDLE_AABB;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ON, meta == 1);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ON) ? 1 : 0;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ON);
	}
}
