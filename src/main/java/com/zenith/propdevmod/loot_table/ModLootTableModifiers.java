package com.zenith.propdevmod.loot_table;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModLootTableModifiers {

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (key.identifier().getPath().equals("blocks/oak_leaves")
                    || key.identifier().getPath().equals("blocks/dark_oak_leaves")) {

                LootPool.Builder pool = LootPool.lootPool()

                        .when(
                                LootItemRandomChanceCondition.randomChance(0.005f)
                        )

                        .add(
                                LootItem.lootTableItem(Items.DIAMOND)
                        );

                tableBuilder.pool(pool.build());
            }
        });
    }
}
