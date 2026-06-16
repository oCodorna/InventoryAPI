package com.henryfabio.minecraft.inventoryapi.viewer.configuration.border;

import lombok.Data;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data(staticConstructor = "of")
public final class Border {

    private final int top;
    private final int left;
    private final int bottom;
    private final int right;

    public static Border of(int value) {
        return Border.of(value, value, value, value);
    }

}
