package party.lemons.corvus.capability.gaiabreath;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;

public class GaiaBreathCapability
{
	@CapabilityInject(IGaiaBreath.class)
	public static final Capability<IGaiaBreath> CAPABILITY = null;

	public static class CapabilityGaiaBreath implements Capability.IStorage<IGaiaBreath>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IGaiaBreath> capability, IGaiaBreath instance, EnumFacing side)
		{
			NBTTagCompound tags = new NBTTagCompound();
			tags.setInteger("breath", instance.getBreath());

			return tags;
		}

		@Override
		public void readNBT(Capability<IGaiaBreath> capability, IGaiaBreath container, EnumFacing side, NBTBase nbts)
		{
			NBTTagCompound nbt = (NBTTagCompound) nbts;
			container.setBreath(nbt.getInteger("breath"));
		}
	}
}
