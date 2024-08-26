package itemslimitations.stiven;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getMain() {
        return instance;
    }

    public void onEnable() {
        instance = this;

        int pluginId = 9707;
        new Metrics(this, pluginId);

        getCommand("itemslimitations").setExecutor(new Commands());

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        checkConfig();
    }

    public void checkConfig() {
        File config = new File(getDataFolder() + File.pathSeparator + "config.yml");
        if (config.exists()) {
            saveConfig();
        } else {
            saveDefaultConfig();
        }
        reloadConfig();
    }

}
