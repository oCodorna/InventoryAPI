package com.henryfabio.minecraft.inventoryapi.item.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MaterialUtil {

    public static ItemStack convertFromLegacy(String materialName) {
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            material = Material.matchMaterial("LEGACY_" + materialName);
        }

        return new ItemStack(material != null ? material : Material.BEDROCK);
    }

}