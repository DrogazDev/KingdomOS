package nl.drogaz.kingdomOSX.miscellaneous;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class StyledComponent {

    public static @NotNull Component style(String style) {
        return MiniMessage.miniMessage().deserialize(style).decoration(TextDecoration.ITALIC, false);
    }

}
