package com.henryfabio.minecraft.inventoryapi.item.callback.update;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@FunctionalInterface
public interface ItemUpdateCallback {

    void accept(@NotNull ItemStack itemStack);

}