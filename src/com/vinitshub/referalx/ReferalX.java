package com.vinitshub.referalx;
import com.vinitshub.referalx.commands.ReferalConnect;
import com.vinitshub.referalx.commands.ReferalReset;
import com.vinitshub.referalx.commands.ReferalCode;
import com.vinitshub.referalx.database.MySQL;
import com.vinitshub.referalx.events.BukkitEvents;
import com.vinitshub.referalx.events.LuckPermsEvents;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.LuckPermsEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

import static org.bukkit.ChatColor.*;

public class ReferalX extends JavaPlugin {
    //SQL Class Instance
    public MySQL SQL;

    //LuckPermsAPI Registration to check for Rank Upgrade
    private static LuckPerms luckPerms;
    public static LuckPerms getLPApi(){return luckPerms;}

    //Instance Function to give access to other classes to JavaPlugin (BUKKIT)
    private static ReferalX instance;
    public static ReferalX getInstance(){
        return instance;
    }

    //Executed when plugin enables
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
            instance = this;
            SQL.createTable();
            Objects.requireNonNull
                    (this.getCommand("referalconnect"))
                        .setExecutor(new ReferalConnect());
            Objects.requireNonNull
                    (this.getCommand("referalreset"))
                        .setExecutor(new ReferalReset());
            Objects.requireNonNull
                    (this.getCommand("referalcode"))
                        .setExecutor(new ReferalCode());
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckPerms = provider.getProvider();
            }
            new LuckPermsEvents(this, luckPerms).register();
            Bukkit.getServer().getPluginManager().registerEvents(new BukkitEvents(), this);
        }
    }

    //Executed when plugin disable
    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(RED + ">-ReferalX has stop-<");
        if(SQL.isConnected()){
            SQL.disconnect();
        }
    }
}
