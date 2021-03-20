package com.vinitshub.referalx.commands;
import com.vinitshub.referalx.ReferalX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static org.bukkit.ChatColor.*;

public class ReferalConnect implements CommandExecutor {
    ReferalX plugin = ReferalX.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            String PLAYERUUID = player.getUniqueId().toString();
            try {
                if(!plugin.SQL.isLinked(PLAYERUUID)) {
                    plugin.SQL.setLinkedTo(PLAYERUUID, parseInt(args[0]));
                    sender.sendMessage
                            (GREEN + "You have linked your account to " + GOLD + args[0]);

                }else{
                    sender.sendMessage
                            (RED + "You can only link your account once");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{

            sender.sendMessage("Only player can execute the command");
        }
        return false;
    }
}
