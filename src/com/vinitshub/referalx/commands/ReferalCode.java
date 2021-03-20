package com.vinitshub.referalx.commands;
import com.vinitshub.referalx.ReferalX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.sql.SQLException;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.DyeColor.GREEN;

public class ReferalCode implements CommandExecutor {
    ReferalX plugin = ReferalX.getInstance();
    /**
    <<=INFO>=>>
    Command to get ReferalXCode, /referalCode
    Works only for Players (NOT CONSOLE)
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            String PLAYERUUID = player.getUniqueId().toString();
            try {
                int code = plugin.SQL.getCode(PLAYERUUID);
                    sender.sendMessage
                            (GREEN + "Your code is " + GOLD + code);
                    sender.sendMessage
                            (GREEN + "Ask your friends to do /referalConnect " + GOLD + code);
                    sender.sendMessage
                            (GREEN + "And everytime they rank up using  /rankup, both of you get rewards");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}
