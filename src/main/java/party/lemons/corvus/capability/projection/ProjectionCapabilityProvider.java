package party.lemons.corvus.capability.projection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProjectionCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{
	private final ProjectionContainer container;

	public ProjectionCapabilityProvider(ProjectionContainer container)
	{
		this.container = container;
	}

	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == ProjectionCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == ProjectionCapability.CAPABILITY)
			return (T) this.container;

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		if(container.getRecallPosition() != null)
			tags.setTag("pos", NBTUtil.createPosTag(container.getRecallPosition()));
		tags.setBoolean("projecting", container.isProjecting());
		return tags;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tags)
	{
		if(tags.hasKey("pos"))
		{
			BlockPos pos = NBTUtil.getPosFromTag(tags.getCompoundTag("pos"));
			container.setProjecting(tags.getBoolean("projecting"), pos);
		}
	}
}
