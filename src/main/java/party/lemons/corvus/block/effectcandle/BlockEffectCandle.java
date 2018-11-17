package party.lemons.corvus.block.effectcandle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.corvus.block.BlockCandle;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockEffectCandle extends BlockCandle
{
	private final CandleEffect effect;

	public BlockEffectCandle(CandleEffect effect)
	{
		super();

		this.effect = effect;
		this.setDefaultState(this.getDefaultState().withProperty(ON, true));
	}

	public CandleEffect getEffect()
	{
		return effect;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		spawnParticles(worldIn, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return false;
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
		return new TileEntityEffectCandle(effect);
	}
}
