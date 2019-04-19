package party.lemons.corvus.block.tilentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBreathingTulip extends TileEntity
{
	private int damage;

	public TileEntityBreathingTulip()
	{
		this.damage = 0;
	}

	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int newDamage)
	{
		this.damage = newDamage;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("damage_amt", damage);

		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		damage = compound.getInteger("damage_amt");

		super.readFromNBT(compound);
	}
}
