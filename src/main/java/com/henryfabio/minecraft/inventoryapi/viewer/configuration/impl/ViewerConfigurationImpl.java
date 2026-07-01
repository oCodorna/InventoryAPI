package com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl;

import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.border.Border;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Accessors(fluent = true)
@Data
public abstract class ViewerConfigurationImpl implements ViewerConfiguration {

    private String titleInventory;
    private int inventorySize;
    private String backInventory;

    public static class Simple extends ViewerConfigurationImpl {
        // empty implementation
    }

    @Accessors(fluent = true)
    @Setter @Getter
    public static class Paged extends ViewerConfigurationImpl {

        private int itemPageLimit;
        private Border border;

        private int nextPageSlot;
        private int previousPageSlot;
        private int emptyPageSlot;

    }

    @Accessors(fluent = true)
    @Setter @Getter
    public static class Navigator extends ViewerConfigurationImpl {

        private int itemPageLimit;
        private Border border;
        private int emptyPageSlot;

        private int upPageSlot;
        private int downPageSlot;
        private int leftPageSlot;
        private int rightPageSlot;

    }

    public static class Global extends ViewerConfigurationImpl {
        // empty implementation
    }

}