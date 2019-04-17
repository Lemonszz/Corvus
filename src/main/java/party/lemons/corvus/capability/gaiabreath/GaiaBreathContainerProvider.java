package party.lemons.corvus.capability.gaiabreath;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import party.lemons.corvus.capability.spirit.SpiritCapability;
import party.lemons.corvus.capability.spirit.SpiritContainer;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GaiaBreathContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider
{
	private final GaiaBreathContainer container;

	public GaiaBreathContainerProvider(GaiaBreathContainer container)
	{
		this.container = container;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == GaiaBreathCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == GaiaBreathCapability.CAPABILITY)
			return (T) this.container;

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("breath", container.getBreath());

		return tags;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		container.setBreath(nbt.getInteger("breath"));
	}
}