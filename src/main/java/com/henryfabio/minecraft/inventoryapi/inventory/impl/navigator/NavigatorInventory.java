package com.henryfabio.minecraft.inventoryapi.inventory.impl.navigator;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.enums.DefaultItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.navigator.NavigatorViewer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Sistema de Navegação Bidimensional (Matriz X,Y)
 */
public abstract class NavigatorInventory extends CustomInventoryImpl {

    public NavigatorInventory(String id, String title, int size) {
        super(id, title, size, new InventoryConfigurationImpl.Paged());
    }

    @Override
    public final <T extends Viewer> void openInventory(@NotNull Player player, Consumer<T> viewerConsumer) {
        Viewer viewer = new NavigatorViewer(player.getName(), this);
        defaultOpenInventory(player, viewer, viewerConsumer);
    }

    @Override
    public final void updateInventory(@NotNull Player player) {
        super.updateInventory(player);
    }

    protected void configureViewer(@NotNull NavigatorViewer viewer) {}

    @Override
    protected final void configureViewer(@NotNull Viewer viewer) {
        this.configureViewer(((NavigatorViewer) viewer));
    }

    protected void update(@NotNull NavigatorViewer viewer, @NotNull InventoryEditor editor) {}

    @Override
    protected final void update(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        NavigatorViewer navigatorViewer = (NavigatorViewer) viewer;
        this.update(navigatorViewer, editor);

        navigatorViewer.setScreenItemList(createScreenItems(
                navigatorViewer,
                navigatorViewer.getCoordinateX(),
                navigatorViewer.getCoordinateY()
        ));
        navigatorViewer.insertScreenItems();
    }

    protected abstract List<InventoryItemSupplier> createScreenItems(@NotNull NavigatorViewer viewer, int x, int y);

    public abstract boolean hasScreen(int x, int y);

    public InventoryItem createUpItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_UP.toInventoryItem(viewer); }
    public InventoryItem createDownItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_DOWN.toInventoryItem(viewer); }
    public InventoryItem createLeftItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_LEFT.toInventoryItem(viewer); }
    public InventoryItem createRightItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_RIGHT.toInventoryItem(viewer); }

    public InventoryItem createNoUpItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_UP.toInventoryItem(viewer); }
    public InventoryItem createNoDownItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_DOWN.toInventoryItem(viewer); }
    public InventoryItem createNoLeftItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_LEFT.toInventoryItem(viewer); }
    public InventoryItem createNoRightItem(@NotNull NavigatorViewer viewer) { return DefaultItem.NAVIGATOR_RIGHT.toInventoryItem(viewer); }
}