package itemslimitations.stiven;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    public static Main main = Main.getMain();
    public static FileConfiguration config = main.getConfig();

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                if (config.getBoolean("nohit.enabled")) {
                    if (config.getBoolean("nohit.whitelst")) {
                        if (config.getList("nohit.items").contains(damager.getItemInHand().getTypeId())) {
                            e.setCancelled(true);
                            if (config.getString("nohit.message") != null || config.getString("nohit.message").length() < 1) {
                                damager.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("nohit.message")));
                            }
                        }
                    } else {
                        if (!config.getList("nohit.items").contains(damager.getItemInHand().getTypeId())) {
                            e.setCancelled(true);
                            if (config.getString("nohit.message") != null || config.getString("nohit.message").length() < 1) {
                                damager.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("nohit.message")));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerDeathDrop(PlayerDeathEvent e) {
        for (ItemStack drop : e.getDrops()) {
            int typeId = drop.getTypeId();
            if (config.getBoolean("deathdrop.whitelist")) {
                if (!config.getList("deathdrop.items").contains(typeId)) {
                    if (config.getBoolean("deathdrop.enabled")) {
                        drop.setAmount(0);
                    }
                }
            } else {
                if (config.getList("deathdrop.items").contains(typeId)) {
                    if (config.getBoolean("deathdrop.enabled")) {
                        drop.setAmount(0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void inventoryItemSave(InventoryClickEvent e) {
        if (!e.getWhoClicked().hasPermission("itemslimitations.bypass")) {
            if (config.getBoolean("inventorysave.enabled")) {
                String invName = e.getInventory().getName().replaceAll("[^a-zA-Z0-9]", " ");
                if (config.get("inventorysave.inventories." + invName) != null) {
                    if (config.getBoolean("inventorysave.inventories." + invName + ".whitelist")) {
                        if (!config.getList("inventorysave.inventories." + invName + ".items").contains(e.getCurrentItem().getTypeId()) && e.getCurrentItem().getTypeId() != 0) {
                            e.setCancelled(true);
                            if (config.getString("inventorysave.inventories." + invName + ".message") != null) {
                                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("inventorysave.inventories." + invName + ".message")));
                            }
                        }
                    } else {
                        if (config.getList("inventorysave.inventories." + invName + ".items").contains(e.getCurrentItem().getTypeId()) && e.getCurrentItem().getTypeId() != 0) {
                            e.setCancelled(true);
                            if (config.getString("inventorysave.inventories." + invName +  ".message") != null) {
                                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("inventorysave.inventories." + invName +  ".message")));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!e.getPlayer().hasPermission("itemslimitations.bypass")) {
            if (config.getBoolean("nodrop.enabled")) {
                if (config.getBoolean("nodrop.whitelist")) {
                    if (!config.getList("nodrop.items").contains(e.getItemDrop().getType().getTypeId())) {
                        e.setCancelled(true);
                        if (config.getString("nodrop.message") != null) {
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("nodrop.message")));
                        }
                    }
                } else {
                    if (config.getList("nodrop.items").contains(e.getItemDrop().getType().getTypeId())) {
                        e.setCancelled(true);
                        if (config.getString("nodrop.message") != null) {
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("nodrop.message")));
                        }
                    }
                }
            }
        }
    }

}
