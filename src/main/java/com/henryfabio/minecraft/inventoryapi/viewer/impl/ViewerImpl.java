package com.henryfabio.minecraft.inventoryapi.viewer.impl;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.editor.impl.InventoryEditorImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.item.util.TextUtil;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.property.ViewerPropertyMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public abstract class ViewerImpl implements Viewer {

    private final String name;
    private final CustomInventory customInventory;

    private final ViewerConfiguration configuration;
    private final ViewerPropertyMap propertyMap = new ViewerPropertyMap();

    @Setter(AccessLevel.PRIVATE) protected InventoryEditor editor;

    @Override
    public Inventory createInventory() {
        Component titleComponent = TextUtil.format(configuration.titleInventory());

        Inventory inventory = Bukkit.createInventory(
                null,
                configuration.inventorySize(),
                titleComponent
        );
        this.editor = new InventoryEditorImpl(inventory);
        return inventory;
    }

    @Override
    public void resetConfigurations() {
        this.configuration.titleInventory(this.customInventory.getTitle());
        this.configuration.inventorySize(this.customInventory.getSize());
        this.configuration.backInventory(null);
    }
}