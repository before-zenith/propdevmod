package com.zenith.propdevmod.potion;

import com.zenith.propdevmod.Propdevmod;
import com.zenith.propdevmod.effect.PropdevmodModEffects;
import com.zenith.propdevmod.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class PropdevmodModPotions implements ModInitializer {
    public static final Holder<Potion> PROP_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    Identifier.fromNamespaceAndPath(Propdevmod.MOD_ID, "prop_potion"),
                    new Potion("prop_potion",
                            new MobEffectInstance(
                                    PropdevmodModEffects.PROP_EFFECT,
                                    600,
                                    0
                            )));

    @Override
    public void onInitialize() {
        FabricPotionBrewingBuilder.BUILD.register(builder -> {
            builder.addMix(
                    // Input potion.
                    Potions.WATER,
                    // Ingredient
                    ModItems.MANGO,
                    // Output potion.
                    PROP_POTION
            );
        });
    }
}
