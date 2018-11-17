package party.lemons.corvus.block.effectcandle;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import party.lemons.corvus.init.CorvusBlocks;

public class TileEntityEffectCandle extends TileEntity implements ITickable
{
	private CandleEffect effect;
	private int age = 0;

	public TileEntityEffectCandle()
	{
		super();
	}

	public TileEntityEffectCandle(CandleEffect effect)
	{
		super();
		this.effect = effect;
	}

	@Override
	public void update()
	{
		if(effect != null && world.getTotalWorldTime() % 5 == 0)
		{
			effect.doEffect(world, pos);
		}

		age++;

		if(age > 18000) //15 mins
		//if(age > 120) //15 mins
		{
			world.setBlockState(pos, CorvusBlocks.CANDLE.getDefaultState());
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("candle", age);
		int ind = -1;
		if(effect instanceof CandleEffect.EffectWind)
		{
			ind = 0;
		}
		else if(effect instanceof CandleEffect.EffectGrowth)
			ind = 1;
		else if(effect instanceof CandleEffect.EffectFire)
			ind = 2;

		compound.setInteger("candle_type", ind);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		age = compound.getInteger("candle");
		switch(compound.getInteger("candle_type"))
		{
			case 0:
				effect = new CandleEffect.EffectWind();
				break;
			case 1:
				effect= new CandleEffect.EffectGrowth();
				break;
			case 2:
				effect = new CandleEffect.EffectFire();
				break;
		}

		super.readFromNBT(compound);
	}
}
