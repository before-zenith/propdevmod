package com.zenith.propdevmod.entity;

import com.zenith.propdevmod.Propdevmod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.monster.Monster;

public class ModEntityTypes {
    public static final EntityType<PropSlimeEntity> PROP_SLIME = register(
            "prop_slime",
            EntityType.Builder.<PropSlimeEntity>of(PropSlimeEntity::new, MobCategory.MONSTER)
                    .sized(0.52f, 0.52f) // Matching vanilla slime bounding box scaling
    );

    public static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Propdevmod.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        Propdevmod.LOGGER.info("Registering EntityTypes for " + Propdevmod.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(PROP_SLIME, Monster.createMonsterAttributes());
    }
}
