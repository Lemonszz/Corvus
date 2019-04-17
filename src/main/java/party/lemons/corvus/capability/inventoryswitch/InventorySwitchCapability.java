package party.lemons.corvus.capability.inventoryswitch;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import party.lemons.corvus.capability.gaiabreath.IGaiaBreath;

import javax.annotation.Nullable;

public class InventorySwitchCapability
{
	@CapabilityInject(IInventorySwitch.class)
	public static final Capability<IInventorySwitch> CAPABILITY = null;

	public static class CapabilityInventorySwitch implements Capability.IStorage<IInventorySwitch>
	{

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IInventorySwitch> capability, IInventorySwitch instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<IInventorySwitch> capability, IInventorySwitch instance, EnumFacing side, NBTBase nbt)
		{
			instance.deserializeNBT(new NBTTagList());
		}
	}
}
