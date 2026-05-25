package com.zenith.propdevmod.item;

import com.zenith.propdevmod.Propdevmod;
import com.zenith.propdevmod.entity.ModEntityTypes;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;

public class ModItems {
    public static final Item MANGO = register("mango", Item::new, new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.3f)
            .build()));

    public static final Item PROP_SLIME_SPAWN_EGG = register(
            "prop_slime_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(ModEntityTypes.PROP_SLIME)
    );

    public static final Item PROP_SLIME_BALL = register(
            "prop_slime_ball",
            Item::new,
            new Item.Properties()
    );

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Propdevmod.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the key.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((creativeTab) -> creativeTab.accept(ModItems.MANGO));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS)
                .register((creativeTab) -> creativeTab.accept(ModItems.PROP_SLIME_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register((creativeTab) -> creativeTab.accept(ModItems.PROP_SLIME_BALL));
    }
}
