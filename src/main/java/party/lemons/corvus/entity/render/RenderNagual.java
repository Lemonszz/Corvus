package party.lemons.corvus.entity.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.entity.EntityNagual;

public class RenderNagual extends RenderBiped<EntityNagual>
{
	ResourceLocation[] locs = {
			new ResourceLocation(Corvus.MODID, "textures/entity/nagual_1.png"),
			new ResourceLocation(Corvus.MODID, "textures/entity/nagual_2.png"),
			new ResourceLocation(Corvus.MODID, "textures/entity/nagual_3.png"),
			new ResourceLocation(Corvus.MODID, "textures/entity/nagual_4.png")
	};

	public RenderNagual(RenderManager rm)
	{
		super(rm, new ModelBiped(0, 0, 64, 64), 0.5F);
	}

	protected ResourceLocation getEntityTexture(EntityNagual entity)
	{
		return locs[entity.getDataManager().get(entity.VARIANT)];
	}
}
