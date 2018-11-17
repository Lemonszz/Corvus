package party.lemons.corvus.capability.crow;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrowContainerProvider implements ICapabilityProvider
{
	private final CrowContainer container;

	public CrowContainerProvider(CrowContainer container)
	{
		this.container = container;
	}

	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CrowCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == CrowCapability.CAPABILITY)
			return (T) this.container;

		return null;
	}
}
