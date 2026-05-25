package com.zenith.propdevmod.entity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

public class PropdevmodModEntity implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntityTypes.registerModEntityTypes();
        ModEntityTypes.registerAttributes();

        SpawnPlacements.register(
                ModEntityTypes.PROP_SLIME,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PropSlimeEntity::canSpawn
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.all(),
                MobCategory.MONSTER,
                ModEntityTypes.PROP_SLIME,
                30,
                3,
                4
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_FOREST),
                MobCategory.MONSTER,
                ModEntityTypes.PROP_SLIME,
                50,
                4,
                5
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.HAS_SWAMP_HUT),
                MobCategory.MONSTER,
                ModEntityTypes.PROP_SLIME,
                50,
                4,
                5
        );
    }
}