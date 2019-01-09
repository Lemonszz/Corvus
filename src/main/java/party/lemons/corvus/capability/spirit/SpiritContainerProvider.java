package party.lemons.corvus.capability.spirit;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpiritContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider
{
	private final SpiritContainer container;

	public SpiritContainerProvider(SpiritContainer container)
	{
		this.container = container;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == SpiritCapability.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == SpiritCapability.CAPABILITY)
			return (T) this.container;

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("spirit", container.getSpirit());
		tags.setInteger("max_spirit", container.getMaxSprit());
		tags.setBoolean("awakened", container.isAwakened());

		NBTTagList spells = new NBTTagList();
		for(Spell spell : container.getUnlockedSpells())
		{
			spells.appendTag(new NBTTagString(spell.getRegistryName().toString()));
		}
		tags.setTag("spells", spells);
		if(container.getActiveSpell() != null)
		{
			tags.setString("active_spell", container.getActiveSpell().getRegistryName().toString());
		}

		return tags;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		container.setSpiritMax(nbt.getInteger("max_spirit"));
		container.setSprit(nbt.getInteger("spirit"));
		container.setAwakened(nbt.getBoolean("awakened"));

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
