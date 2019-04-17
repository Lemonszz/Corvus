package party.lemons.corvus.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import party.lemons.lemonlib.item.IItemModel;


public class BlockCorvusFlower extends BlockBush implements IItemModel
{
	public BlockCorvusFlower()
	{
		super();
		this.setSoundType(SoundType.PLANT);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
	}

	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

}
