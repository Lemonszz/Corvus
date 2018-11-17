package party.lemons.corvus.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.corvus.entity.EntityCrow;

public class ModelCrow extends ModelBase
{
	public ModelRenderer head;
	public ModelRenderer wingRight;
	public ModelRenderer legRight;
	public ModelRenderer tail;
	public ModelRenderer body;
	public ModelRenderer wingLeft;
	public ModelRenderer legLeft;
	public ModelRenderer shape12;
	private State state = State.STANDING;

	public ModelCrow()
	{
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.head = new ModelRenderer(this, 2, 2);
		this.head.setRotationPoint(0.0F, 19.0F, -3.5F);
		this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 2, 3, 0.0F);

		this.wingLeft = new ModelRenderer(this, 19, 8);
		this.wingLeft.setRotationPoint(1.5F, 20.0F, -2.759999990463257F);
		this.wingLeft.addBox(-0.5F, 0.0F, -1.5F, 1, 4, 3, 0.0F);
		this.setRotateAngle(wingLeft, 0.9599310755729675F, 0.0F, 0.0F);

		this.legRight = new ModelRenderer(this, 14, 18);
		this.legRight.setRotationPoint(-1.0F, 22.0F, -1.0499999523162842F);
		this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(legRight, 0.668231725692749F, 0.0F, 0.0F);

		this.shape12 = new ModelRenderer(this, 0, 0);
		this.shape12.setRotationPoint(-0.5F, -0.9F, -2.0F);
		this.shape12.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

		this.tail = new ModelRenderer(this, 22, 1);
		this.tail.setRotationPoint(0.0F, 6.1F, 1.0F);
		this.tail.addBox(-1.5F, -0.4F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(tail, 0.4553564018453205F, 0.0F, 0.0F);

		this.wingRight = new ModelRenderer(this, 19, 8);
		this.wingRight.setRotationPoint(-1.5F, 20.0F, -2.759999990463257F);
		this.wingRight.addBox(-0.5F, 0.0F, -1.5F, 1, 4, 3, 0.0F);
		this.setRotateAngle(wingRight, -0.9599310755729675F, -3.1415927410125732F, 0.0F);

		this.legLeft = new ModelRenderer(this, 14, 18);
		this.legLeft.setRotationPoint(1.0F, 22.0F, -1.0499999523162842F);
		this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(legLeft, 0.668231725692749F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 2, 8);
		this.body.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.body.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(body, 1.3089969158172607F, 0.0F, 0.0F);

		this.head.addChild(this.shape12);
		this.body.addChild(this.tail);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.head.render(f5);
		this.wingRight.render(f5);
		this.wingLeft.render(f5);
		this.body.render(f5);
		this.legLeft.render(f5);
		this.legRight.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		float f = ageInTicks * 0.3F;
		float pi180 = ((float)Math.PI / 180F);

		this.head.rotateAngleX = headPitch * pi180;
		this.head.rotateAngleY = netHeadYaw * pi180;
		this.head.rotateAngleZ = 0.0F;
		this.head.rotationPointX = 0.0F;

		this.tail.rotateAngleX = (0.6F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount) + ((-0.5F + (float)Math.sin(ageInTicks/ 4)) / 25F);
		this.tail.rotateAngleZ =  ((-0.5F + (float)Math.sin(ageInTicks/ 8)) / 25F);

		if(entityIn instanceof EntityCrow)
		{
			EntityCrow crow = (EntityCrow) entityIn;
			if(crow.isFlying())
			{
				this.wingRight.rotateAngleZ = (float) Math.sin(ageInTicks / 1.25F) * 4F;
				this.wingLeft.rotateAngleZ = -(float) Math.sin(ageInTicks / 1.25F) * 4F;
			}
			else
			{
				this.wingRight.rotateAngleZ = 0;
				this.wingLeft.rotateAngleZ = 0;
				this.wingRight.rotateAngleZ = ((-0.5F + (float)Math.sin(ageInTicks/ 8)) / 25F);
				this.wingLeft.rotateAngleZ = ((-0.5F + (float)Math.sin(ageInTicks/ 8)) / 25F);
			}

			if(crow.isSitting() && !crow.isFlying())
			{
				this.tail.rotateAngleX = (-0.1F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount) + ((-0.5F + (float)Math.sin(ageInTicks/ 4)) / 25F);
			}
		}
	}

	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{/*
		this.body.rotateAngleX = 0.4937F;
		this.wingLeft.rotateAngleX = -((float)Math.PI * 2F / 9F);
		this.wingLeft.rotateAngleY = -(float)Math.PI;
		this.wingRight.rotateAngleX = -((float)Math.PI * 2F / 9F);
		this.wingRight.rotateAngleY = -(float)Math.PI;
		this.legLeft.rotateAngleX = -0.0299F;
		this.legRight.rotateAngleX = -0.0299F;
		this.legLeft.rotationPointY = 22.0F;
		this.legRight.rotationPointY = 22.0F;

		if (entitylivingbaseIn instanceof EntityCrow)
		{
			EntityCrow entityparrot = (EntityCrow)entitylivingbaseIn;

			if (entityparrot.isSitting())
			{
				this.head.rotationPointY = 17.59F;
				this.tail.rotateAngleX = 1.5388988F;
				this.tail.rotationPointY = 22.97F;
				this.body.rotationPointY = 18.4F;
				this.wingLeft.rotateAngleZ = -0.0873F;
				this.wingLeft.rotationPointY = 18.84F;
				this.wingRight.rotateAngleZ = 0.0873F;
				this.wingRight.rotationPointY = 18.84F;
				++this.legLeft.rotationPointY;
				++this.legRight.rotationPointY;
				++this.legLeft.rotateAngleX;
				++this.legRight.rotateAngleX;
				this.state = State.SITTING;
			}
			else if (entityparrot.isFlying())
			{
				this.legLeft.rotateAngleX += ((float)Math.PI * 2F / 9F);
				this.legRight.rotateAngleX += ((float)Math.PI * 2F / 9F);
				this.state = State.FLYING;
			}
			else
			{
				this.state = State.STANDING;
			}

			this.legLeft.rotateAngleZ = 0.0F;
			this.legRight.rotateAngleZ = 0.0F;
		}*/
	}

	@SideOnly(Side.CLIENT)
	enum State
	{
		FLYING,
		STANDING,
		SITTING
	}
}
