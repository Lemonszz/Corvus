package party.lemons.corvus.capability.projection;

import net.minecraft.util.math.BlockPos;

public interface IProjection
{
	boolean isProjecting();
	void setProjecting(boolean projecting);
	void setProjecting(boolean projecting, BlockPos recallPos);
	BlockPos getRecallPosition();
}
