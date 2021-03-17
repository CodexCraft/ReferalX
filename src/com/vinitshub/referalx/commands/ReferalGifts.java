package com.vinitshub.referalx.commands;

import com.vinitshub.referalx.ReferalX;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.List;
import java.util.Objects;

import static net.md_5.bungee.api.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

public class ReferalGifts implements CommandExecutor, Listener {
    public ReferalX plugin = ReferalX.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)    {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Inventory inv = plugin.gc.getInv(player.getName());
            player.openInventory(inv);
        }
        else sender.sendMessage(RED + "This command can only be executed by a Player");
        return true;
    }
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inv = event.getClickedInventory();
        if(event.getClickedInventory() == null) { return; }
        if(inv.getSize() > 44) {
            if (inv.getItem(40) != null && inv.getItem(40).getItemMeta() != null) {
                if (inv.getItem(40).getItemMeta().getDisplayName().equals(GREEN + "Claims all gifts in one click")) {
                    if (event.getCurrentItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                        for (int i = 0; i < 36; i++) {
                            event.getWhoClicked().getWorld()
                                    .dropItemNaturally(event.getWhoClicked().getLocation(), inv.getItem(i));
                        }
                        plugin.gc.removeGifts(event.getWhoClicked().getName());
                    }
                    if (event.getCurrentItem().getType() == Material.BARRIER) {
                        event.getWhoClicked().closeInventory();
                    }
                    if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        LuckPerms api = ReferalX.getLPApi();
                        List<String> refers = plugin.SQL.getRefers(event.getWhoClicked().getName());
                        for (int i = 0; i < refers.size(); i++) {
                            String uuid = refers.get(i);
                            String group = Objects.requireNonNull
                                    (api.getUserManager().getUser(uuid)).getPrimaryGroup();
                            Player player = Bukkit.getPlayer(uuid);
                            String playerName = player.getName();
                            event.getWhoClicked().sendMessage(playerName + ": " + group);
                        }
                    }
                    if(event.getSlot() == 39 && event.getCurrentItem().getType() == Material.ARROW){
                        event.getWhoClicked().sendMessage("PREVIOUS PAGE");
                    }
                    if(event.getSlot() == 41 && event.getCurrentItem().getType() == Material.ARROW){
                        event.getWhoClicked().sendMessage("NEXT PAGE");
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
