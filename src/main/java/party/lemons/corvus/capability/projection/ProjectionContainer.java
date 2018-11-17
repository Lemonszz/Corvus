package party.lemons.corvus.capability.projection;

import net.minecraft.util.math.BlockPos;

public class ProjectionContainer implements IProjection
{
	private boolean projecting;
	private BlockPos pos;

	@Override
	public boolean isProjecting()
	{
		return projecting;
	}

	@Override
	public void setProjecting(boolean projecting)
	{
		this.projecting = projecting;
	}

	@Override
	public void setProjecting(boolean projecting, BlockPos recallPos)
	{
		this.projecting = projecting;
		this.pos = recallPos;
	}

	@Override
	public BlockPos getRecallPosition()
	{
		return pos;
	}
}
