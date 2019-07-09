package party.lemons.corvus.capability.progression;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import party.lemons.corvus.progression.Progression;
import party.lemons.corvus.progression.ProgressionRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PlayerProgressionProvider implements INBTSerializable<NBTTagList>, ICapabilityProvider
{
	private final PlayerProgression container;

	public PlayerProgressionProvider(PlayerProgression container)
	{
		this.container = container;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing)
	{
		return capability == PlayerProgressionCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing)
	{
		if(capability == PlayerProgressionCapability.CAPABILITY)
			return (T) this.container;

		return null;
	}

	@Override
	public NBTTagList serializeNBT()
	{
		List<Progression> progressions = container.getUnlockedProgression();
		NBTTagList tags = new NBTTagList();
		for(Progression progression : progressions)
		{
			tags.appendTag(new NBTTagString(progression.getRegistryName().toString()));
		}
		return tags;
	}

	@Override
	public void deserializeNBT(NBTTagList tags)
	{
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Progression progression = ProgressionRegistry.REGISTRY.getValue(new ResourceLocation(tags.getStringTagAt(i)));
			container.unlockProgression(progression);
		}
	}
}
