package party.lemons.corvus.capability.inventoryswitch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.init.CorvusItems;

import java.util.HashMap;
import java.util.Map;

public class InventorySwitchContainer implements IInventorySwitch
{
	private NBTTagList tags;

	public InventorySwitchContainer()
	{
		tags = new NBTTagList();
	}

	@Override
	public void swapInventory(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		Map<Integer, ItemStack> exceptionItemsMain = new HashMap<>();
		Map<Integer, ItemStack> exceptionItemsArmor = new HashMap<>();
		Map<Integer, ItemStack> exceptionItemsOffhand = new HashMap<>();
		InventoryPlayer currentInv = player.inventory;

		for(int i = 0; i < currentInv.mainInventory.size(); i++)
		{
			ItemStack stack = currentInv.mainInventory.get(i);
			if(isExceptionItem(stack))
			{
				exceptionItemsMain.put(i, stack);
				currentInv.mainInventory.set(i, ItemStack.EMPTY);
			}
		}

		for(int i = 0; i < currentInv.armorInventory.size(); i++)
		{
			ItemStack stack = currentInv.armorInventory.get(i);
			if(isExceptionItem(stack))
			{
				exceptionItemsArmor.put(i, stack);
				currentInv.armorInventory.set(i, ItemStack.EMPTY);
			}
		}

		for(int i = 0; i < currentInv.offHandInventory.size(); i++)
		{
			ItemStack stack = currentInv.offHandInventory.get(i);
			if(isExceptionItem(stack))
			{
				exceptionItemsOffhand.put(i, stack);
				currentInv.offHandInventory.set(i, ItemStack.EMPTY);
			}
		}

		NBTTagList currentTags = currentInv.writeToNBT(new NBTTagList());

		player.inventory.clear();
		player.inventory.readFromNBT(tags);

		this.tags = currentTags;

		for(Map.Entry<Integer, ItemStack> stackEntry : exceptionItemsMain.entrySet())
		{
			int slot = stackEntry.getKey();
			ItemStack stack = stackEntry.getValue();

			if(player.inventory.mainInventory.get(slot).isEmpty())
			{
				player.inventory.mainInventory.set(slot, stack);
			}
			else
			{
				player.dropItem(stack, false);
			}
		}

		for(Map.Entry<Integer, ItemStack> stackEntry : exceptionItemsArmor.entrySet())
		{
			int slot = stackEntry.getKey();
			ItemStack stack = stackEntry.getValue();

			if(player.inventory.armorInventory.get(slot).isEmpty())
			{
				player.inventory.armorInventory.set(slot, stack);
			}
			else
			{
				player.dropItem(stack, false);
			}
		}

		for(Map.Entry<Integer, ItemStack> stackEntry : exceptionItemsOffhand.entrySet())
		{
			int slot = stackEntry.getKey();
			ItemStack stack = stackEntry.getValue();

			if(player.inventory.offHandInventory.get(slot).isEmpty())
			{
				player.inventory.offHandInventory.set(slot, stack);
			}
			else
			{
				player.dropItem(stack, false);
			}
		}

		player.inventory.markDirty();
	}

	public static boolean isExceptionItem(ItemStack stack)
	{
		return !stack.isEmpty() && ((stack.getItem() == CorvusItems.GRIMOIRE) || stack.getItem() == Item.getItemFromBlock(CorvusBlocks.BREATHING_TULIP));
	}

	public NBTTagList serializeNBT()
	{
		return tags;
	}

	public void deserializeNBT(NBTTagList nbt)
	{
		this.tags = nbt;
	}
}
