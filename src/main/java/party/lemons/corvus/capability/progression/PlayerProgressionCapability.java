package party.lemons.corvus.capability.progression;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import party.lemons.corvus.progression.Progression;
import party.lemons.corvus.progression.ProgressionRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class PlayerProgressionCapability
{
	@CapabilityInject(IPlayerProgression.class)
	public static final Capability<IPlayerProgression> CAPABILITY = null;

	public static class CapabilityPlayerProgression implements Capability.IStorage<IPlayerProgression>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IPlayerProgression> capability, IPlayerProgression instance, EnumFacing enumFacing)
		{
			List<Progression> progressions = instance.getUnlockedProgression();
			NBTTagList tags = new NBTTagList();
			for(Progression progression : progressions)
			{
				tags.appendTag(new NBTTagString(progression.getRegistryName().toString()));
			}
			return tags;
		}

		@Override
		public void readNBT(Capability<IPlayerProgression> capability, IPlayerProgression instance, EnumFacing enumFacing, NBTBase nbtBase)
		{
			NBTTagList tags = (NBTTagList) nbtBase;
			for(int i = 0; i < tags.tagCount(); i++)
			{
				Progression progression = ProgressionRegistry.REGISTRY.getValue(new ResourceLocation(tags.getStringTagAt(i)));
				instance.unlockProgression(progression);
			}
		}
	}
}
