package com.henryfabio.minecraft.inventoryapi.item.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class TextUtil {

    private TextUtil() {

    }

    public static @NotNull Component format(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        if (text.contains("<") && text.contains(">")) {
            return MiniMessage.miniMessage().deserialize(text);
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }
}