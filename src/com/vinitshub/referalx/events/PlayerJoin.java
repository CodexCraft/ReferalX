package com.vinitshub.referalx.events;
import com.vinitshub.referalx.ReferalX;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.Locale;

public class PlayerJoin implements Listener {
    ReferalX referalX = ReferalX.getInstance();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        try {
            if(referalX.SQL.exists(event.getPlayer().getUniqueId().toString())) {
                Player player = event.getPlayer();
                String PLAYERNAME = player.getName();
                String PLAYERUUID = player.getUniqueId().toString();
                String ISLINKED = "FALSE";
                int LINKEDTO = 0000;
                referalX.SQL.insertData(PLAYERNAME, PLAYERUUID, ISLINKED, LINKEDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
