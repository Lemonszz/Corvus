package party.lemons.corvus.capability.spirit;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import javax.annotation.Nullable;

public class SpiritCapability
{
	@CapabilityInject(ISpirit.class)
	public static final Capability<ISpirit> CAPABILITY = null;

	public static class CapabilitySpirit implements Capability.IStorage<ISpirit>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ISpirit> capability, ISpirit instance, EnumFacing side)
		{
			NBTTagCompound tags = new NBTTagCompound();
			tags.setInteger("spirit", instance.getSpirit());
			tags.setInteger("max_spirit", instance.getMaxSprit());

			NBTTagList spells = new NBTTagList();
			for(Spell spell : instance.getUnlockedSpells())
			{
				spells.appendTag(new NBTTagString(spell.getRegistryName().toString()));
			}
			tags.setTag("spells", spells);
			if(instance.getActiveSpell() != null)
			{
				tags.setString("active_spell", instance.getActiveSpell().getRegistryName().toString());
			}

			return tags;
		}

		@Override
		public void readNBT(Capability<ISpirit> capability, ISpirit container, EnumFacing side, NBTBase nbts)
		{
			NBTTagCompound nbt = (NBTTagCompound) nbts;

			container.setSpiritMax(nbt.getInteger("max_spirit"));
			container.setSprit(nbt.getInteger("spirit"));

			NBTTagList list = nbt.getTagList("spells", Constants.NBT.TAG_STRING);
			for(int i = 0; i < list.tagCount(); i++)
			{
				container.unlockSpell(SpellRegistry.REGISTRY.getValue(new ResourceLocation(list.getStringTagAt(i))));
			}

			if(nbt.hasKey("active_spell"))
			{
				container.setActiveSpell(SpellRegistry.REGISTRY.getValue(new ResourceLocation(nbt.getString("active_spell"))));
			}
		}
	}
}
