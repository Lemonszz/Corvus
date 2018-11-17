package party.lemons.corvus.capability.projection;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import party.lemons.corvus.Corvus;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class ProjectionCapability
{
	@CapabilityInject(IProjection.class)
	public static final Capability<IProjection> CAPABILITY = null;

	public static class CapabilityCrow implements Capability.IStorage<IProjection>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IProjection> capability, IProjection instance, EnumFacing side)
		{
			NBTTagCompound tags = new NBTTagCompound();
			tags.setTag("pos", NBTUtil.createPosTag(instance.getRecallPosition()));
			tags.setBoolean("projecting", instance.isProjecting());
			return tags;
		}

		@Override
		public void readNBT(Capability<IProjection> capability, IProjection instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tags = (NBTTagCompound) nbt;

			BlockPos pos = NBTUtil.getPosFromTag(tags.getCompoundTag("pos"));
			instance.setProjecting(tags.getBoolean("projecting"), pos);
		}
	}
}
