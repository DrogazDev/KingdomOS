package nl.drogaz.kingdomOSX.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import io.papermc.paper.datacomponent.item.ItemArmorTrim;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.TrimPatternKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import nl.drogaz.kingdomOSX.miscellaneous.StyledComponent;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingTrimRecipe;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.lang.reflect.Method;
import java.util.List;

@CommandAlias("m|moderator|mod")
@CommandPermission("kingdomos.moderator")
public class Moderator extends BaseCommand {

    @Default
    public void commandSyntax(Player player) {
        player.sendMessage("/m <option>");
    }

    @Subcommand("cosmetics")
    public void openCosmetics(Player player) {
         Gui cosmetics = Gui.gui()
                .title(Component.text("Cosmetics"))
                .rows(3)
                .disableAllInteractions()
                .create();

        cosmetics.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem());
        cosmetics.setItem(12, ItemBuilder.from(Material.DIAMOND_SWORD).name(StyledComponent.style("<gold>Loadout Cosmetics")).asGuiItem(inventoryClickEvent -> {
            openLoadoutsCosmetics(player);
        }));
        cosmetics.setItem(14, ItemBuilder.from(Material.FEATHER).name(StyledComponent.style("<gold>Effects")).asGuiItem(inventoryClickEvent -> player.sendMessage("effects")));

        cosmetics.open(player);
    }

    public void openLoadoutsCosmetics(Player player) {
        Gui loadouts = Gui.gui()
                .title(Component.text("Loadout Cosmetics"))
                .rows(4)
                .disableAllInteractions()
                .create();

        loadouts.getFiller().fillBorder(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem());

        loadouts.setItem(11, ItemBuilder.from(Material.DIAMOND_SWORD).name(StyledComponent.style("<white>KB Zwaard")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default")).asGuiItem());
        loadouts.setItem(12, ItemBuilder.from(Material.DIAMOND_SWORD).name(StyledComponent.style("<white>Non KB Zwaard")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default")).asGuiItem());

        loadouts.setItem(13, ItemBuilder.from(Material.BOW).name(StyledComponent.style("<white>Punch Boog")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default")).asGuiItem());
        loadouts.setItem(14, ItemBuilder.from(Material.BOW).name(StyledComponent.style("<white>Non Punch Boog")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default")).asGuiItem());

        loadouts.setItem(15, ItemBuilder.from(Material.GOLDEN_APPLE).name(StyledComponent.style("<white>Gouden Appels")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default")).asGuiItem());

        loadouts.setItem(20, ItemBuilder.from(Material.DIAMOND_HELMET).name(StyledComponent.style("<white>Diamond Helmet")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default"), StyledComponent.style("<yellow>Huidge Trim: <white>Coast")).glow().flags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES).asGuiItem());
        loadouts.setItem(21, ItemBuilder.from(Material.DIAMOND_CHESTPLATE).name(StyledComponent.style("<white>Diamond Chestplate")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default"), StyledComponent.style("<yellow>Huidge Trim: <white>Coast")).glow().flags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES).asGuiItem());
        loadouts.setItem(22, ItemBuilder.from(Material.DIAMOND_LEGGINGS).name(StyledComponent.style("<white>Diamond Leggings")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default"), StyledComponent.style("<yellow>Huidge Trim: <white>Coast")).glow().flags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES).asGuiItem());
        loadouts.setItem(23, ItemBuilder.from(Material.DIAMOND_BOOTS).name(StyledComponent.style("<white>Diamond Boots")).lore(StyledComponent.style(""), StyledComponent.style("<yellow>Huidige skin: <white>Default"), StyledComponent.style("<yellow>Huidge Trim: <white>Coast")).glow().flags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES).asGuiItem());

        loadouts.setItem(24, ItemBuilder.from(Material.ARROW).name(StyledComponent.style("<white>Arrow Trails")).lore(StyledComponent.style("<yellow>Huidige Trail:<white>Flame")).asGuiItem(inventoryClickEvent -> openArrowTrails(player)));

        loadouts.setItem(31, ItemBuilder.from(Material.BARRIER).name(StyledComponent.style("<red>Terug</red>")).asGuiItem(inventoryClickEvent -> {
            openCosmetics(player);
        }));

        loadouts.open(player);
    }

    public void openArmorTrims(Player player) {
        PaginatedGui armorTrims = Gui.paginated()
                .title(Component.text("Armor Trims"))
                .rows(6)
                .pageSize(45)
                .disableAllInteractions()
                .create();

        paginatedHandler(armorTrims);

        var patternRegistry = RegistryAccess.registryAccess()
                        .getRegistry(RegistryKey.TRIM_PATTERN);

        patternRegistry.stream().forEach(trimPattern -> {
            armorTrims.addItem(ItemBuilder.from(Material.PAPER).name(StyledComponent.style("<gray>" + trimPattern)).asGuiItem());
        });

        armorTrims.open(player);
    }
    public void openArrowTrails(Player player) {
        PaginatedGui arrowTrails = Gui.paginated()
                .title(Component.text("Arrow Trails"))
                .rows(6)
                .pageSize(45)
                .disableAllInteractions()
                .create();

        paginatedHandler(arrowTrails);

        List<Particle> particles = List.of(Particle.values());
        particles.stream().forEach(particle -> {
            String particleName = particle.name().toLowerCase().replace("_", " ");
            arrowTrails.addItem(ItemBuilder.from(Material.PAPER).name(StyledComponent.style("<gray>" + particleName)).asGuiItem(inventoryClickEvent -> openLoadoutsCosmetics(player)));
        });

        arrowTrails.open(player);
    }
    public void paginatedHandler(PaginatedGui paginatedGui) {
        paginatedGui.getFiller().fillBottom(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem());
        paginatedGui.setItem(6, 3, ItemBuilder.from(Material.ARROW).name(StyledComponent.style("<gold>Vorige</gold>")).asGuiItem(event -> paginatedGui.previous()));
        paginatedGui.setItem(6, 7, ItemBuilder.from(Material.ARROW).name(StyledComponent.style("<gold>Volgende</gold>")).asGuiItem(event -> paginatedGui.next()));
    }
}
