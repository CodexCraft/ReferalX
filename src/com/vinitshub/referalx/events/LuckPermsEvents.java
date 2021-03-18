package com.vinitshub.referalx.events;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import com.vinitshub.referalx.ReferalX;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.track.UserPromoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LuckPermsEvents {

    private ReferalX plugin;
    private LuckPerms luckperms;

    public LuckPermsEvents(ReferalX plugin, LuckPerms luckperms) {
        this.plugin = plugin;
        this.luckperms = luckperms;
    }
    public void register() {
        EventBus eventBus = this.luckperms.getEventBus();
        eventBus.subscribe(this.plugin, UserPromoteEvent.class, this::onPlayerPromote);
    }
    public void onPlayerPromote(UserPromoteEvent event){
        if(event.getTrack().getName().equalsIgnoreCase("")){
            Optional<String> promotedToOptional = event.getGroupTo();
            String promotedTo = promotedToOptional.toString();
            boolean isCrate = false;
            switch(promotedTo){
                case "Egg":
                    List<String> eggRewardList = plugin.getConfig()
                            .getStringList("rankReward.Egg");
                    for (String s : eggRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;

                        if(s.contains("crate")){
                            isCrate = true;
                            //ToDo finish this
                        }
                    }
                    break;
                case "Chicken":
                    List<String> chickenRewardList = plugin.getConfig()
                            .getStringList("rankReward.Chicken");
                    for (String s : chickenRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));;

                    }
                    break;
                case "Wolf":
                    List<String> wolfRewardList = plugin.getConfig()
                            .getStringList("rankReward.Wolf");
                    for (String s : wolfRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
                case "Lion":
                    List<String> lionRewardList = plugin.getConfig()
                            .getStringList("rankReward.Lion");
                    for (String s : lionRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
                case "Phoenix":
                    List<String> phoenixRewardList = plugin.getConfig()
                            .getStringList("rankReward.Phoenix");
                    for (String s : phoenixRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
                case "Griffin":
                    List<String> griffinRewardList = plugin.getConfig()
                            .getStringList("rankReward.Griffin");
                    for (String s : griffinRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
                case "Manticore":
                    List<String> manticoreRewardList = plugin.getConfig()
                            .getStringList("rankReward.Manticore");
                    for (String s : manticoreRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
                case "Dragon":
                    List<String> dragonRewardList = plugin.getConfig()
                            .getStringList("rankReward.Dragon");
                    for (String s : dragonRewardList) {
                        UUID playerUUID = event.getUser().getUniqueId();
                        Player eventPlayer = Bukkit.getServer().getPlayer(playerUUID);
                        assert eventPlayer != null;
                        String cmd = s;
                        cmd = s.replace("%P%", plugin.SQL.getLinkedToName(playerUUID.toString()));
                    }
                    break;
            }
        }
    }
}
