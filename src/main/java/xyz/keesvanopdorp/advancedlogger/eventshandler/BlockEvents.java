package xyz.keesvanopdorp.advancedlogger.eventshandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import xyz.keesvanopdorp.advancedlogger.Main;

import java.util.Arrays;

public class BlockEvents implements Listener {
    final private Main plugin = Main.getInstance();

    @EventHandler
    public void onSignChangeEvent(final SignChangeEvent signChangeEvent) {
        if(!this.plugin.getConfiguration().getBoolean("loggers.signs")) {
            return;
        }

        Player player = signChangeEvent.getPlayer();
        String[] lines = signChangeEvent.getLines();

        String message = String.format("Player: %s - Created a sign with the following text: %s\n", player.getDisplayName(), Arrays.toString(lines));
        this.plugin.getLogUtil().printMessage(message);
    }
}
