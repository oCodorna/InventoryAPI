package com.henryfabio.minecraft.inventoryapi.viewer.impl.navigator;

import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.navigator.NavigatorInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.border.Border;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

@Getter @Setter
public final class NavigatorViewer extends ViewerImpl {

    private final List<InventoryItemSupplier> screenItemList = new LinkedList<>();

    private int coordinateX = 0;
    private int coordinateY = 0;

    public NavigatorViewer(String name, CustomInventory customInventory) {
        super(name, customInventory, new ViewerConfigurationImpl.Navigator());
    }

    public void changeCoordinate(int nextX, int yNext) {
        this.coordinateX = nextX;
        this.coordinateY = yNext;

        CustomInventory customInventory = this.getCustomInventory();
        customInventory.updateInventory(this.getPlayer());
    }

    public void moveUp() { changeCoordinate(coordinateX, coordinateY + 1); }
    public void moveDown() { changeCoordinate(coordinateX, coordinateY - 1); }
    public void moveLeft() { changeCoordinate(coordinateX - 1, coordinateY); }
    public void moveRight() { changeCoordinate(coordinateX + 1, coordinateY); }

    public void insertScreenItems() {
        ViewerConfigurationImpl.Navigator configuration = this.getConfiguration();
        Border border = configuration.border();
        NavigatorInventory navInventory = (NavigatorInventory) getCustomInventory();

        InventoryItem airItem = InventoryItem.of(new ItemStack(Material.AIR));

        if (this.screenItemList.isEmpty()) {
            editor.fillCenter(airItem, border);
        } else {
            List<InventoryItem> inventoryItems = new LinkedList<>();
            int maxLimit = configuration.itemPageLimit();

            for (int i = 0; i < maxLimit; i++) {
                if (i < this.screenItemList.size()) {
                    InventoryItemSupplier itemSupplier = this.screenItemList.get(i);
                    inventoryItems.add(itemSupplier != null ? itemSupplier.get() : airItem);
                } else {
                    inventoryItems.add(airItem);
                }
            }
            editor.fillPage(inventoryItems, border);
        }

        if (navInventory.hasScreen(coordinateX, coordinateY + 1)) {
            editor.setItem(configuration.upPageSlot(), navInventory.createUpItem(this).defaultCallback(e -> moveUp()));
        } else {
            InventoryItem noUp = navInventory.createNoUpItem(this);
            editor.setItem(configuration.upPageSlot(), noUp != null ? noUp : airItem);
        }

        if (navInventory.hasScreen(coordinateX, coordinateY - 1)) {
            editor.setItem(configuration.downPageSlot(), navInventory.createDownItem(this).defaultCallback(e -> moveDown()));
        } else {
            InventoryItem noDown = navInventory.createNoDownItem(this);
            editor.setItem(configuration.downPageSlot(), noDown != null ? noDown : airItem);
        }

        if (navInventory.hasScreen(coordinateX - 1, coordinateY)) {
            editor.setItem(configuration.leftPageSlot(), navInventory.createLeftItem(this).defaultCallback(e -> moveLeft()));
        } else {
            InventoryItem noLeft = navInventory.createNoLeftItem(this);
            editor.setItem(configuration.leftPageSlot(), noLeft != null ? noLeft : airItem);
        }

        if (navInventory.hasScreen(coordinateX + 1, coordinateY)) {
            editor.setItem(configuration.rightPageSlot(), navInventory.createRightItem(this).defaultCallback(e -> moveRight()));
        } else {
            InventoryItem noRight = navInventory.createNoRightItem(this);
            editor.setItem(configuration.rightPageSlot(), noRight != null ? noRight : airItem);
        }
    }

    public void setScreenItemList(List<InventoryItemSupplier> items) {
        this.screenItemList.clear();
        this.screenItemList.addAll(items);
    }

    @Override
    public void resetConfigurations() {
        super.resetConfigurations();
        ViewerConfigurationImpl.Navigator configuration = this.getConfiguration();
        int inventoryLines = configuration.inventorySize() / 9;

        configuration.border(Border.of(1));

        int itemPageLimit = 7;
        if (inventoryLines > 3) {
            itemPageLimit += (inventoryLines - 3) * 7;
        }
        configuration.itemPageLimit(itemPageLimit);

        configuration.upPageSlot(4);

        int downSlot = (inventoryLines - 1) * 9 + 4;
        configuration.downPageSlot(downSlot);

        int leftSlot;
        int rightSlot;

        switch (inventoryLines) {
            case 2, 3 -> {
                leftSlot = 9;
                rightSlot = 17;
            }
            case 4, 5 -> {
                leftSlot = 18;
                rightSlot = 26;
            }
            case 6 -> {
                leftSlot = 27;
                rightSlot = 35;
            }
            default -> {
                leftSlot = 0;
                rightSlot = 8;
            }
        }

        configuration.leftPageSlot(leftSlot);
        configuration.rightPageSlot(rightSlot);
    }

    @Override
    public ViewerConfigurationImpl.Navigator getConfiguration() {
        return (ViewerConfigurationImpl.Navigator) super.getConfiguration();
    }
}