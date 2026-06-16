package com.henryfabio.minecraft.inventoryapi.item;

import com.henryfabio.minecraft.inventoryapi.event.impl.CustomInventoryClickEvent;
import com.henryfabio.minecraft.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.minecraft.inventoryapi.item.callback.update.ItemUpdateCallback;
import lombok.Data;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data(staticConstructor = "of")
public final class InventoryItem {

    @NotNull
    private final ItemStack itemStack;

    @NotNull
    private final ItemCallback itemCallback = new ItemCallback();

    public InventoryItem callback(@Nullable ClickType clickType, @NotNull Consumer<CustomInventoryClickEvent> eventConsumer) {
        this.itemCallback.callback(clickType, eventConsumer);
        return this;
    }

    public InventoryItem defaultCallback(@NotNull Consumer<CustomInventoryClickEvent> eventConsumer) {
        return this.callback(null, eventConsumer);
    }

    public InventoryItem updateCallback(@NotNull ItemUpdateCallback updateCallback) {
        this.itemCallback.setUpdateCallback(updateCallback);
        return this;
    }

}