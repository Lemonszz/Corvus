package party.lemons.corvus.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.UUID;

public class ItemBooster extends Item
{
	private final IAttribute boostAttribute;
	private final int boostAmt;
	private final int maxBoost;
	private final UUID modifierUUID = UUID.fromString("e881fcf9-0942-4234-8d87-804b093dcdb6");
	private final String modiferName;

	public ItemBooster(IAttribute boostAttribute, int boostAmt, int maxBoost)
	{
		this.boostAmt = boostAmt;
		this.maxBoost = maxBoost;

		this.boostAttribute = boostAttribute;
		modiferName = "corvus" + boostAttribute.getName().replace(".", "");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{

		//if the target entity is a player, stop
		if(target instanceof EntityPlayer)
			return false;

		//Don't apply attributes on the client
		if(playerIn.world.isRemote)
			return true;

		//Get the Attribute Modifier
		IAttributeInstance attributeInstance = target.getEntityAttribute(boostAttribute);
		AttributeModifier modifier = attributeInstance.getModifier(modifierUUID);


		//If the modifier is at the max boost level, don't continue
		if(modifier != null && modifier.getAmount() >= maxBoost)
			return false;

		//Remove old modifier & add the new one
		double oldAmt = 0;
		if(modifier != null)
		{
			oldAmt = modifier.getAmount();
			attributeInstance.removeModifier(modifier);
		}
		attributeInstance.applyModifier(new AttributeModifier(modifierUUID, modiferName, boostAmt + oldAmt, 0));

		if(boostAttribute == SharedMonsterAttributes.MAX_HEALTH)
			target.heal(boostAmt);


		if(!playerIn.isCreative())
			stack.shrink(1);

		return true;
	}
}
