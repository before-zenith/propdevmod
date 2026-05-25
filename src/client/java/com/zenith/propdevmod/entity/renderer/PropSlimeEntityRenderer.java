package com.zenith.propdevmod.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zenith.propdevmod.Propdevmod;
import com.zenith.propdevmod.entity.PropSlimeEntity;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PropSlimeEntityRenderer extends MobRenderer<PropSlimeEntity, SlimeRenderState, SlimeModel> {

    private static final Identifier CUSTOM_TEXTURE = Identifier.fromNamespaceAndPath(
            Propdevmod.MOD_ID,
            "textures/entity/slime/prop_slime.png"
    );

    public PropSlimeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new PropSlimeEntityOuterLayer(this, context.getModelSet()));
    }

    @Override
    public Identifier getTextureLocation(SlimeRenderState renderState) {
        return CUSTOM_TEXTURE;
    }

    @Override
    public SlimeRenderState createRenderState() {
        return new SlimeRenderState();
    }

    @Override
    public void extractRenderState(PropSlimeEntity entity, SlimeRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        renderState.size = entity.getSize();
        renderState.squish = Mth.lerp(partialTick, entity.oSquish, entity.squish);
    }

    @Override
    protected void scale(SlimeRenderState state, PoseStack poseStack) {
        float s = 0.999F;
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);

        float size = (float)state.size;
        float ss = state.squish / (size * 0.5F + 1.0F);
        float w = 1.0F / (ss + 1.0F);

        poseStack.scale(w * size, 1.0F / w * size, w * size);
    }
}