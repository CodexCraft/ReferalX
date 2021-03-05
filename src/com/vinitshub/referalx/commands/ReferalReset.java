package com.vinitshub.referalx.commands;

import com.vinitshub.referalx.ReferalX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ReferalReset implements CommandExecutor {
    private final ReferalX plugin = new ReferalX();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            try {
                plugin.SQL.resetLinked(((Player) sender).getUniqueId().toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}
