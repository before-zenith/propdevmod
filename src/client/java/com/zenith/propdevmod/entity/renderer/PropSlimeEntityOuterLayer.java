package com.zenith.propdevmod.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zenith.propdevmod.Propdevmod;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class PropSlimeEntityOuterLayer extends RenderLayer<SlimeRenderState, SlimeModel> {
    private final SlimeModel model;

    private static final Identifier CUSTOM_TEXTURE = Identifier.fromNamespaceAndPath(
            Propdevmod.MOD_ID,
            "textures/entity/slime/prop_slime.png"
    );

    public PropSlimeEntityOuterLayer(RenderLayerParent<SlimeRenderState, SlimeModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SlimeModel(modelSet.bakeLayer(ModelLayers.SLIME_OUTER));
    }

    @Override
    public void submit(
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final int lightCoords,
            final SlimeRenderState state,
            final float yRot,
            final float xRot
    ) {
        boolean appearsGlowingWithInvisibility = state.appearsGlowing() && state.isInvisible;
        if (!state.isInvisible || appearsGlowingWithInvisibility) {
            int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
            if (appearsGlowingWithInvisibility) {
                submitNodeCollector.order(1)
                        .submitModel(this.model, state, poseStack, RenderTypes.outline(CUSTOM_TEXTURE), lightCoords, overlayCoords, state.outlineColor, null);
            } else {
                submitNodeCollector.order(1)
                        .submitModel(
                                this.model, state, poseStack, RenderTypes.entityTranslucent(CUSTOM_TEXTURE), lightCoords, overlayCoords, state.outlineColor, null
                        );
            }
        }
    }
}