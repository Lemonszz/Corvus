package party.lemons.corvus.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import party.lemons.lemonlib.item.IItemModel;

public class BlockModel extends Block implements IItemModel
{
	public BlockModel(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}

	public BlockModel(Material materialIn)
	{
		super(materialIn);
	}
}
