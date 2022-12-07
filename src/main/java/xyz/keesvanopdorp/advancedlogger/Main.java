package xyz.keesvanopdorp.advancedlogger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.keesvanopdorp.advancedlogger.eventshandler.BlockEvents;
import xyz.keesvanopdorp.advancedlogger.eventshandler.PlayerEvents;
import xyz.keesvanopdorp.advancedlogger.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

public class Main extends JavaPlugin {
    private String configPath;
    private String logsPath;

    private LogUtil logUtil;
    private YamlConfiguration configuration = new YamlConfiguration();

    private static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        // Begin config folder check
        final File configFolder = new File(this.getDataFolder().getPath());
        if (!configFolder.exists()) {
            if (configFolder.mkdir()) {
                this.getLogger().log(Level.INFO, "Config folder created");
            }
        }
        // End config folder check

        // Start load configuration
        this.configPath = this.getDataFolder().getPath() + "/config.yml";
        final File configFile = new File(this.configPath);
        if (!configFile.exists()) {
            this.saveDefaultConfig();
        }

        try {
            this.configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            this.getLogger().log(SEVERE, e.getMessage());
        }
        // End load configuration

        // Begin logs folder check
        this.logsPath = this.getDataFolder().getPath() + "/logs";
        final File logFolder = new File(this.logsPath);
        if (!logFolder.exists()) {
            if (logFolder.mkdir()) {
                this.getLogger().log(Level.INFO, "Log folder created");
            }
        }
        // End logs folder check

        this.logUtil = new LogUtil(this.logsPath);

        this.getLogger().log(INFO, String.format("Commands events are %s", this.configuration.getBoolean("loggers.commands") ? "enabled" : "disabled"));
        this.getLogger().log(INFO, String.format("Msg capture is %s", this.configuration.getBoolean("loggers.msg") ? "enabled" : "disabled"));
        this.getLogger().log(INFO, String.format("Sign events are %s", this.configuration.getBoolean("loggers.signs") ? "enabled" : "disabled"));
        this.getLogger().log(INFO, String.format("Anvil events are %s", this.configuration.getBoolean("loggers.anvil") ? "enabled" : "disabled"));

        this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);

        this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public String getLogsPath() {
        return this.logsPath;
    }

    public LogUtil getLogUtil() {
        return this.logUtil;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public static Main getInstance() {
        return INSTANCE;
    }
}
