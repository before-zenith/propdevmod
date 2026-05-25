package com.zenith.propdevmod.effect;

import com.zenith.propdevmod.Propdevmod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class PropdevmodModEffects implements ModInitializer {
    public static final Holder<MobEffect> PROP_EFFECT =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Propdevmod.MOD_ID, "prop_effect"), new PropEffect());

    @Override
    public void onInitialize() {
    }
}
