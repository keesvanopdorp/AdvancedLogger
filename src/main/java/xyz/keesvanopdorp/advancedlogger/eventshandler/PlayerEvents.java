package xyz.keesvanopdorp.advancedlogger.eventshandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.keesvanopdorp.advancedlogger.Main;

import static org.bukkit.event.EventPriority.HIGHEST;

public class PlayerEvents implements Listener {
    final private Main plugin = Main.getInstance();

    @EventHandler
    public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        if (!this.plugin.getConfiguration().getBoolean("loggers.commands")) {
            return;
        }

        Player player = playerCommandPreprocessEvent.getPlayer();
        String command = playerCommandPreprocessEvent.getMessage();

        if (command.split(" ")[0].equals("/msg") && !this.plugin.getConfiguration().getBoolean("loggers.msg")) {
            return;
        }
        String message = String.format("Player: %s - Executed the following command: %s\n", player.getDisplayName(), command);

        this.plugin.getLogUtil().printMessage(message);
    }

    @EventHandler(priority = HIGHEST)
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if (!this.plugin.getConfiguration().getBoolean("loggers.anvils")) {
            return;
        }

        final Player player = (Player) inventoryClickEvent.getWhoClicked();
        final Inventory inventory = inventoryClickEvent.getInventory();
        if (inventory instanceof AnvilInventory) {
            final InventoryView inventoryView = inventoryClickEvent.getView();
            final int rawSlot = inventoryClickEvent.getRawSlot();
            if (rawSlot == inventoryView.convertSlot(rawSlot) && rawSlot == 2) {
                final ItemStack item = inventoryClickEvent.getCurrentItem();
                if (item != null) {
                    final ItemMeta meta = item.getItemMeta();
                    if (meta != null && meta.hasDisplayName()) {
                        final String displayName = meta.getDisplayName().replace("\\", "\\\\");
                        final String itemName = item.getType().name();
                        String message = String.format("Player: %s - Renamed item %s to: %s\n", player.getDisplayName(), itemName, displayName);
                        this.plugin.getLogUtil().printMessage(message);
                    }
                }
            }
        }

    }
}
