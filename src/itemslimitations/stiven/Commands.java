package itemslimitations.stiven;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Commands implements CommandExecutor {

    public static Main main = Main.getMain();
    public static FileConfiguration config = main.getConfig();

    public static CommandSender cmdSender;
    public static void toSenderMessage(String text) {
        cmdSender.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
    }
    public static void sendString(String string) {
        if (config.getString(string) != null || string.contains(" ")) {
            toSenderMessage(config.getString(string));
        } else {
            toSenderMessage(string);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        cmdSender = sender;
        if (cmd.getName().equalsIgnoreCase("itemslimitations")) {
            if (sender.hasPermission("itemslimitations.cmd")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&lItemsLimitations"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e* &7/itemslimitations reload &8- &7Reload config"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------"));
                } else {
                    if (args[0].equalsIgnoreCase("reload")) {
                        main.checkConfig();
                        main.reloadConfig();
                        sendString("messages.reload");
                        return true;
                    }
                    sendString("messages.invalidsubcommand");
                }
            } else {
                sendString("messages.noperm");
            }
        }
        return false;
    }
}
