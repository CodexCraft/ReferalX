package com.vinitshub.referalx.commands;

import com.vinitshub.referalx.ReferalX;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

    //Stops Player from taking items from the Inventory
    //Sets the functionalily of the control bars
    @EventHandler
    public void onClick(InventoryClickEvent event){
        //Gets the inventory which is clicked
        Inventory inv = event.getClickedInventory();

        //Returns if the clicked inventory is null
        if(event.getClickedInventory() == null) { return; }
        assert inv != null;

        //Checks if the Inventory is the GiftContainer
        if (inv.getSize() > 44 && inv.getItem(40).getItemMeta().
                        getDisplayName().equals(GREEN + "Claims all gifts in one click")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case ENCHANTED_GOLDEN_APPLE:
                    for (int i = 0; i < 36; i++) {
                        if(inv.getItem(i).getItemMeta().getDisplayName().contains("Crate Key")){
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            switch (inv.getItem(i).getItemMeta().getDisplayName()) {
                                case "Pet Crate Key":
                                    Bukkit.dispatchCommand
                                            (console, "/crate give to " +
                                                    event.getWhoClicked().getName() + " pet " +
                                                    inv.getItem(i).getAmount());
                                case "VIP Crate Key":
                                    Bukkit.dispatchCommand
                                            (console, "/crate give to " +
                                                    event.getWhoClicked().getName() + " vip " +
                                                    inv.getItem(i).getAmount());
                                break;
                                case "Donator Crate Key":
                                    Bukkit.dispatchCommand
                                            (console, "/crate give to " +
                                                    event.getWhoClicked().getName() + " donator " +
                                                    inv.getItem(i).getAmount());
                                    break;
                                case "Vote Crate Key":
                                    Bukkit.dispatchCommand
                                            (console, "/crate give to " +
                                                    event.getWhoClicked().getName() + " vote " +
                                                    inv.getItem(i).getAmount());
                                    break;
                            }
                        }else {
                            event.getWhoClicked().getWorld()
                                    .dropItemNaturally(event.getWhoClicked().getLocation(), inv.getItem(i));
                        }
                    }
                    plugin.gc.removeGifts(event.getWhoClicked().getName());
                    break;
                case PLAYER_HEAD:
                    LuckPerms api = ReferalX.getLPApi();
                    List<String> refers = plugin.SQL.getRefers(event.getWhoClicked().getName());
                    for (String uuid : refers) {
                        String group = Objects.requireNonNull
                                (api.getUserManager().getUser(uuid)).getPrimaryGroup();
                        Player player = Bukkit.getPlayer(uuid);
                        String playerName = player.getName();
                        event.getWhoClicked().sendMessage(playerName + ": " + group);
                    }
                    break;
                case BARRIER:
                    event.getWhoClicked().closeInventory();
                    break;
                case ARROW:
                    //TODO PAGES
                    if(event.getSlot() == 41)
                        event.getWhoClicked().sendMessage("NEXT PAGE");
                    else
                        event.getWhoClicked().sendMessage("PREVIOUS PAGE");
            }
        }
    }
}
