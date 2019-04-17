package party.lemons.corvus.capability.inventoryswitch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;

public interface IInventorySwitch
{
	void swapInventory(EntityPlayer player);
	NBTTagList serializeNBT();
	void deserializeNBT(NBTTagList nbt);
}
