package party.lemons.corvus.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.entity.EntityWendigo;

import javax.annotation.Nullable;

public class RenderWendigo extends RenderLiving<EntityWendigo>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Corvus.MODID, "textures/entity/wendigo.png");

	public RenderWendigo(RenderManager rendermanager)
	{
		super(rendermanager, new ModelWendigo(), 0.5F);
	}

	public boolean shouldRender(EntityWendigo livingEntity, ICamera camera, double camX, double camY, double camZ)
	{
		if(!SpiritUtil.getSpirit(Minecraft.getMinecraft().player).isAwakened())
			return false;

		return super.shouldRender(livingEntity, camera, camX, camY, camZ);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityWendigo entity)
	{
		return TEXTURE;
	}
}