package com.henryfabio.minecraft.inventoryapi.inventory.impl.global;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.editor.impl.InventoryEditorImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import com.henryfabio.minecraft.inventoryapi.item.util.TextUtil;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.global.GlobalViewer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
public abstract class GlobalInventory extends CustomInventoryImpl {

    private InventoryEditor inventoryEditor;

    public GlobalInventory(String id, String title, int size) {
        super(id, title, size, new InventoryConfigurationImpl.Global());
    }

    @Override
    public final <T extends Viewer> void openInventory(Player player, Consumer<T> viewerConsumer) {
        createInventoryEditor();

        Viewer viewer = new GlobalViewer(player.getName(), this);
        defaultOpenInventory(player, viewer, viewerConsumer);
    }

    public final void updateInventory() {
        if (this.inventoryEditor == null) return;

        this.update(this.inventoryEditor);
        this.inventoryEditor.updateAllItemStacks();
    }

    @Override
    public final void updateInventory(@NotNull Player player) {
        this.updateInventory();
    }

    @Override
    protected final void configureViewer(@NotNull Viewer viewer) {
        // empty method
    }

    protected void configureInventory(@NotNull InventoryEditor editor) {
        // empty method
    }

    @Override
    protected final void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        // empty method
    }

    protected void update(@NotNull InventoryEditor editor) {
        // empty method
    }

    @Override
    protected final void update(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        if (this.inventoryEditor != null) {
            this.update(this.inventoryEditor);
        }
    }

    private void createInventoryEditor() {
        if (this.inventoryEditor != null) return;

        Component titleComponent = TextUtil.format(this.getTitle());

        Inventory inventory = Bukkit.createInventory(
                null,
                this.getSize(),
                titleComponent
        );
        this.inventoryEditor = new InventoryEditorImpl(inventory);

        this.configureInventory(this.inventoryEditor);
        this.update(this.inventoryEditor);
    }

}