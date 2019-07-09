package party.lemons.corvus.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMud extends BlockModel
{
	protected static final AxisAlignedBB MUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.95D, 1.0D);

	public BlockMud()
	{
		super(Material.GROUND, MapColor.DIRT);
		this.setSoundType(SoundType.SLIME);
		this.setHardness(0.5F);
		this.setResistance(0.25F);
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return MUD_AABB;
	}

	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		entityIn.motionX *= 0.4D;
		entityIn.motionZ *= 0.4D;
	}

	@Override
	public boolean isStickyBlock(IBlockState state)
	{
		return true;
	}
}
