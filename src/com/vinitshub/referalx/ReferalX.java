package com.vinitshub.referalx;
import com.vinitshub.referalx.commands.ReferalConnect;
import com.vinitshub.referalx.commands.ReferalReset;
import com.vinitshub.referalx.commands.ReferalCode;
import com.vinitshub.referalx.database.MySQL;
import com.vinitshub.referalx.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class ReferalX extends JavaPlugin {
    public MySQL SQL;
    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage(GREEN + ">-ReferalX has start-<");
        this.SQL = new MySQL();
        try {
            SQL.connect();
        } catch (SQLException | ClassNotFoundException throwables) {
            Bukkit.getServer().getConsoleSender().sendMessage(RED + ">-Referal Database Not Connected-<");
        }
        if(SQL.isConnected()){
            Bukkit.getServer().getConsoleSender().sendMessage(GREEN + ">-Referal Database Connected-<");
            SQL.createTable();
            this.getCommand("referalconnect").setExecutor(new ReferalConnect());
            this.getCommand("referalreset").setExecutor(new ReferalReset());
            this.getCommand("referalcode").setExecutor(new ReferalCode());
            Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        }
    }
    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(RED + ">-ReferalX has stop-<");
        if(SQL.isConnected()){
            SQL.disconnect();
        }
    }
}
