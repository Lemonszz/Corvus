package party.lemons.corvus.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityCrow;

import javax.annotation.Nullable;

public class RenderCrow extends RenderLiving<EntityCrow>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Corvus.MODID, "textures/entity/crow.png");

	public RenderCrow(RenderManager rendermanager)
	{
		super(rendermanager, new ModelCrow(), 0.25F);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityCrow entity)
	{
		return TEXTURE;
	}
}
