package com.zenith.propdevmod.client;

import com.zenith.propdevmod.entity.ModEntityTypes;
import com.zenith.propdevmod.entity.renderer.PropSlimeEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PropdevmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntityTypes.PROP_SLIME, PropSlimeEntityRenderer::new);
	}
}