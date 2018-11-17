package party.lemons.corvus.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityFamiliar;

import javax.annotation.Nullable;

public class RenderFamiliar extends RenderLiving<EntityFamiliar>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Corvus.MODID, "textures/entity/wolf_familiar.png");

	public RenderFamiliar(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelFamiliar(), 0.0F);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityFamiliar entity)
	{
		return TEXTURE;
	}
}
