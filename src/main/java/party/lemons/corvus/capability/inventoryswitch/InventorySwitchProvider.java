package party.lemons.corvus.capability.inventoryswitch;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventorySwitchProvider implements INBTSerializable<NBTTagList>, ICapabilityProvider
{
	private final InventorySwitchContainer container;

	public InventorySwitchProvider(InventorySwitchContainer container)
	{
		this.container = container;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == InventorySwitchCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == InventorySwitchCapability.CAPABILITY)
			return (T)this.container;

		return null;
	}

	@Override
	public NBTTagList serializeNBT()
	{
		return this.container.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagList nbt)
	{
		this.container.deserializeNBT(nbt);
	}
}
