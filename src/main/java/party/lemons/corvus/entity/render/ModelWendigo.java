package party.lemons.corvus.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Wendigo - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelWendigo extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer neck;
	public ModelRenderer arm_right;
	public ModelRenderer arm_left;
	public ModelRenderer leg_left;
	public ModelRenderer leg_right;
	public ModelRenderer head_base;
	public ModelRenderer head_nose;
	public ModelRenderer antler_1;
	public ModelRenderer antler_7;
	public ModelRenderer antler_2;
	public ModelRenderer anter_5;
	public ModelRenderer anter_6;
	public ModelRenderer antler_3;
	public ModelRenderer antler_4;
	public ModelRenderer antler_8;
	public ModelRenderer anter_11;
	public ModelRenderer anter_12;
	public ModelRenderer antler_9;
	public ModelRenderer antler_10;
	public ModelRenderer arm_right_forearm;
	public ModelRenderer right_claw_1;
	public ModelRenderer right_claw_2;
	public ModelRenderer arm_left_forearm;
	public ModelRenderer left_claw_1;
	public ModelRenderer left_claw_2;
	public ModelRenderer leg_left_2;
	public ModelRenderer left_foot;
	public ModelRenderer leg_right_2;
	public ModelRenderer right_foot;

	public ModelWendigo() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.anter_6 = new ModelRenderer(this, 0, 0);
		this.anter_6.setRotationPoint(0.6F, 1.9F, 1.0F);
		this.anter_6.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(anter_6, 2.6862362517444724F, 1.5025539530419183F, 1.2747884856566583F);
		this.left_foot = new ModelRenderer(this, 44, 50);
		this.left_foot.setRotationPoint(0.0F, 13.1F, 0.0F);
		this.left_foot.addBox(0.0F, 0.0F, 0.0F, 5, 6, 5, 0.0F);
		this.setRotateAngle(left_foot, -0.4553564018453205F, 0.0F, 0.0F);
		this.body = new ModelRenderer(this, 38, 14);
		this.body.setRotationPoint(-3.0F, -18.9F, -1.4F);
		this.body.addBox(0.0F, 0.0F, 0.0F, 8, 16, 5, 0.0F);
		this.setRotateAngle(body, 0.27314402793711257F, 0.0F, 0.0F);
		this.head_nose = new ModelRenderer(this, 10, 0);
		this.head_nose.setRotationPoint(0.0F, 2.5F, -4.0F);
		this.head_nose.addBox(0.0F, 0.2F, 1.0F, 5, 4, 4, 0.0F);
		this.setRotateAngle(head_nose, 0.18203784098300857F, 0.0F, 0.0F);
		this.arm_left = new ModelRenderer(this, 24, 15);
		this.arm_left.setRotationPoint(-2.0F, 0.0F, 2.0F);
		this.arm_left.addBox(0.0F, 0.0F, 0.0F, 3, 13, 4, 0.0F);
		this.setRotateAngle(arm_left, -0.6373942428283291F, 0.6829473363053812F, 0.0F);
		this.anter_11 = new ModelRenderer(this, 0, 0);
		this.anter_11.setRotationPoint(0.0F, -0.1F, 1.0F);
		this.anter_11.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(anter_11, -1.9577358219620393F, 0.8196066167365371F, 0.0F);
		this.antler_2 = new ModelRenderer(this, 0, 0);
		this.antler_2.setRotationPoint(-0.4F, -3.4F, 1.9F);
		this.antler_2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(antler_2, -0.5235987755982988F, 0.0F, -0.12217304763960307F);
		this.antler_9 = new ModelRenderer(this, 0, 0);
		this.antler_9.setRotationPoint(2.6F, -2.0F, 0.2F);
		this.antler_9.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(antler_9, 0.0F, 0.0F, 0.7285004297824331F);
		this.leg_left = new ModelRenderer(this, 30, 36);
		this.leg_left.setRotationPoint(6.8F, 13.7F, 0.0F);
		this.leg_left.addBox(0.0F, 0.0F, 0.0F, 5, 12, 5, 0.0F);
		this.setRotateAngle(leg_left, -0.6981317007977318F, 0.0F, 0.0F);
		this.leg_right_2 = new ModelRenderer(this, 10, 42);
		this.leg_right_2.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.leg_right_2.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
		this.setRotateAngle(leg_right_2, 0.6829473363053812F, 0.0F, 0.0F);
		this.arm_right_forearm = new ModelRenderer(this, 0, 32);
		this.arm_right_forearm.setRotationPoint(0.0F, 11.5F, 0.0F);
		this.arm_right_forearm.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.setRotateAngle(arm_right_forearm, -0.22759093446006054F, 0.045553093477052F, 0.31869712141416456F);
		this.leg_right = new ModelRenderer(this, 30, 36);
		this.leg_right.setRotationPoint(-3.8F, 13.7F, 0.0F);
		this.leg_right.addBox(0.0F, 0.0F, 0.0F, 5, 12, 5, 0.0F);
		this.setRotateAngle(leg_right, -0.6981317007977318F, 0.0F, 0.0F);
		this.arm_right = new ModelRenderer(this, 24, 15);
		this.arm_right.setRotationPoint(8.0F, 0.0F, 0.5F);
		this.arm_right.addBox(0.0F, 0.0F, 0.0F, 3, 13, 4, 0.0F);
		this.setRotateAngle(arm_right, -0.6373942428283291F, -0.6829473363053812F, 0.0F);
		this.antler_8 = new ModelRenderer(this, 0, 0);
		this.antler_8.setRotationPoint(-0.4F, -3.4F, 1.9F);
		this.antler_8.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(antler_8, -0.5235987755982988F, 0.0F, -0.12217304763960307F);
		this.antler_7 = new ModelRenderer(this, 0, 10);
		this.antler_7.setRotationPoint(7.0F, -2.4F, 6.0F);
		this.antler_7.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
		this.setRotateAngle(antler_7, -0.7285004297824331F, 0.22759093446006054F, 0.7740535232594852F);
		this.antler_10 = new ModelRenderer(this, 0, 0);
		this.antler_10.setRotationPoint(-1.6F, -2.0F, 0.0F);
		this.antler_10.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(antler_10, 0.0F, 0.0F, -0.5462880558742251F);
		this.left_claw_1 = new ModelRenderer(this, 6, 8);
		this.left_claw_1.setRotationPoint(0.1F, 10.8F, -1.2F);
		this.left_claw_1.addBox(0.0F, 0.0F, 1.2F, 2, 6, 2, 0.0F);
		this.setRotateAngle(left_claw_1, 0.5009094953223726F, 0.5009094953223726F, 0.0F);
		this.antler_3 = new ModelRenderer(this, 0, 0);
		this.antler_3.setRotationPoint(2.6F, -2.0F, 0.2F);
		this.antler_3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(antler_3, 0.0F, 0.0F, 0.7285004297824331F);
		this.head_base = new ModelRenderer(this, 20, 0);
		this.head_base.setRotationPoint(0.0F, 3.0F, -8.0F);
		this.head_base.addBox(0.0F, 0.0F, 0.0F, 5, 6, 8, 0.0F);
		this.setRotateAngle(head_base, 0.6829473363053812F, 0.0F, 0.0F);
		this.right_claw_2 = new ModelRenderer(this, 6, 8);
		this.right_claw_2.setRotationPoint(0.0F, 11.0F, 2.0F);
		this.right_claw_2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(right_claw_2, 0.5462880558742251F, 0.0F, 0.0F);
		this.anter_12 = new ModelRenderer(this, 0, 0);
		this.anter_12.setRotationPoint(0.6F, 1.9F, 1.0F);
		this.anter_12.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(anter_12, 2.6862362517444724F, 1.5025539530419183F, 1.2747884856566583F);
		this.antler_1 = new ModelRenderer(this, 0, 10);
		this.antler_1.setRotationPoint(-2.9F, -1.6F, 6.0F);
		this.antler_1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
		this.setRotateAngle(antler_1, -0.7285004297824331F, -0.22759093446006054F, -0.7740535232594852F);
		this.leg_left_2 = new ModelRenderer(this, 10, 42);
		this.leg_left_2.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.leg_left_2.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
		this.setRotateAngle(leg_left_2, 0.7740535232594852F, 0.0F, 0.0F);
		this.neck = new ModelRenderer(this, 0, 24);
		this.neck.setRotationPoint(1.5F, -1.9F, -1.9F);
		this.neck.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
		this.setRotateAngle(neck, -0.5462880558742251F, 0.0F, 0.0F);
		this.right_claw_1 = new ModelRenderer(this, 6, 8);
		this.right_claw_1.setRotationPoint(1.5F, 11.0F, -0.7F);
		this.right_claw_1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(right_claw_1, 0.5009094953223726F, -0.5009094953223726F, 0.0F);
		this.anter_5 = new ModelRenderer(this, 0, 0);
		this.anter_5.setRotationPoint(0.0F, -0.1F, 1.0F);
		this.anter_5.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(anter_5, -1.9577358219620393F, 0.8196066167365371F, 0.0F);
		this.arm_left_forearm = new ModelRenderer(this, 0, 32);
		this.arm_left_forearm.setRotationPoint(0.0F, 11.5F, 0.0F);
		this.arm_left_forearm.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.setRotateAngle(arm_left_forearm, -0.22759093446006054F, -0.045553093477052F, -0.31869712141416456F);
		this.left_claw_2 = new ModelRenderer(this, 6, 8);
		this.left_claw_2.setRotationPoint(0.0F, 11.0F, 2.0F);
		this.left_claw_2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(left_claw_2, 0.5462880558742251F, 0.0F, 0.0F);
		this.antler_4 = new ModelRenderer(this, 0, 0);
		this.antler_4.setRotationPoint(-1.6F, -2.0F, 0.0F);
		this.antler_4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(antler_4, 0.0F, 0.0F, -0.5462880558742251F);
		this.right_foot = new ModelRenderer(this, 44, 50);
		this.right_foot.setRotationPoint(0.0F, 13.1F, 0.0F);
		this.right_foot.addBox(0.0F, 0.0F, 0.0F, 5, 6, 5, 0.0F);
		this.setRotateAngle(right_foot, -0.4553564018453205F, 0.0F, 0.0F);
		this.antler_1.addChild(this.anter_6);
		this.leg_left_2.addChild(this.left_foot);
		this.head_base.addChild(this.head_nose);
		this.body.addChild(this.arm_left);
		this.antler_7.addChild(this.anter_11);
		this.antler_1.addChild(this.antler_2);
		this.antler_8.addChild(this.antler_9);
		this.body.addChild(this.leg_left);
		this.leg_right.addChild(this.leg_right_2);
		this.arm_right.addChild(this.arm_right_forearm);
		this.body.addChild(this.leg_right);
		this.body.addChild(this.arm_right);
		this.antler_7.addChild(this.antler_8);
		this.head_base.addChild(this.antler_7);
		this.antler_8.addChild(this.antler_10);
		this.arm_left_forearm.addChild(this.left_claw_1);
		this.antler_2.addChild(this.antler_3);
		this.neck.addChild(this.head_base);
		this.arm_right_forearm.addChild(this.right_claw_2);
		this.antler_7.addChild(this.anter_12);
		this.head_base.addChild(this.antler_1);
		this.leg_left.addChild(this.leg_left_2);
		this.body.addChild(this.neck);
		this.arm_right_forearm.addChild(this.right_claw_1);
		this.antler_1.addChild(this.anter_5);
		this.arm_left.addChild(this.arm_left_forearm);
		this.arm_left_forearm.addChild(this.left_claw_2);
		this.antler_2.addChild(this.antler_4);
		this.leg_right_2.addChild(this.right_foot);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.body.render(scale);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		float fArm = 3F;
		float fLeg = 2F;

		this.neck.rotateAngleY = netHeadYaw * 0.017453292F;

		this.neck.rotateAngleX = -0.5F + (MathHelper.sin(ageInTicks / 15) / 10);
		this.neck.rotateAngleY = 0.25F + (MathHelper.sin(ageInTicks / 10) / 10);

		this.arm_right.rotateAngleX = -0.6373942428283291F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / fArm;
		//this.arm_right_forearm.rotateAngleX = 1 * (-0.22759093446006054F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / fArm);
		this.arm_left.rotateAngleX = -0.6373942428283291F + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / fArm;
		//this.arm_left_forearm.rotateAngleX = 1 * (-0.22759093446006054F + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / fArm);

		this.arm_right.rotateAngleX += (MathHelper.sin(ageInTicks / 15) / 400);
		this.arm_right.rotateAngleY += (MathHelper.sin(ageInTicks / 10) / 400);
		this.arm_left.rotateAngleX -= (MathHelper.sin(ageInTicks / 15) / 400);
		this.arm_left.rotateAngleY -= (MathHelper.sin(ageInTicks / 10) / 400);

		this.arm_right.rotateAngleZ = 0.0F;
		this.arm_left.rotateAngleZ = 0.0F;

		this.leg_right.rotateAngleX = -0.6981317007977318F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / fLeg;
		this.leg_left.rotateAngleX = -0.6981317007977318F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / fLeg;
		this.leg_right.rotateAngleY = 0.0F;
		this.leg_left.rotateAngleY = 0.0F;
		this.leg_right.rotateAngleZ = 0.0F;
		this.leg_left.rotateAngleZ = 0.0F;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
