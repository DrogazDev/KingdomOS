package nl.drogaz.kingdomOSX;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import nl.drogaz.kingdomOSX.commands.admin.Moderator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new Moderator());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
