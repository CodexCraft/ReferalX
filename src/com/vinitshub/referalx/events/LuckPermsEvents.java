package com.vinitshub.referalx.events;

import com.vinitshub.referalx.ReferalX;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.track.UserPromoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class LuckPermsEvents {

    //region LuckPerms Registering and Instances
    private final ReferalX plugin;
    private final LuckPerms luckperms;

    public LuckPermsEvents(ReferalX plugin, LuckPerms luckperms) {
        this.plugin = plugin;
        this.luckperms = luckperms;
    }
    public void register() {
        EventBus eventBus = this.luckperms.getEventBus();
        eventBus.subscribe(this.plugin, UserPromoteEvent.class, this::onPlayerPromote);
    }
    //endregion
    //region Main Stuff
    public void onPlayerPromote(UserPromoteEvent event){
        if(event.getTrack().getName().equalsIgnoreCase("")){
            Optional<String> promotedToOptional = event.getGroupTo();
            String promotedTo = promotedToOptional.toString();
            Player player = Bukkit.getPlayer(event.getUser().getUniqueId());
            assert player != null;

            switch(promotedTo){
                case "Chicken":
                    List<String> chickenRewardList = plugin.getConfig()
                            .getStringList("rankReward.Chicken");
                    rewardsGiverCMD(player, chickenRewardList);
                    break;
                case "Wolf":
                    List<String> wolfRewardList = plugin.getConfig()
                            .getStringList("rankReward.Wolf");
                    rewardsGiverCMD(player, wolfRewardList);
                    break;
                case "Lion":
                    List<String> lionRewardList = plugin.getConfig()
                            .getStringList("rankReward.Lion");
                    rewardsGiverCMD(player, lionRewardList);
                    break;
                case "Phoenix":
                    List<String> phoenixRewardList = plugin.getConfig()
                            .getStringList("rankReward.Phoenix");
                    rewardsGiverCMD(player, phoenixRewardList);
                    break;
                case "Griffin":
                    List<String> griffinRewardList = plugin.getConfig()
                            .getStringList("rankReward.Griffin");
                    rewardsGiverCMD(player, griffinRewardList);
                    break;
                case "Manticore":
                    List<String> manticoreRewardList = plugin.getConfig()
                            .getStringList("rankReward.Manticore");
                    rewardsGiverCMD(player, manticoreRewardList);
                    break;
                case "Dragon":
                    List<String> dragonRewardList = plugin.getConfig()
                            .getStringList("rankReward.Dragon");
                    rewardsGiverCMD(player, dragonRewardList);
                    break;
            }
        }
    }
    private void rewardsGiverCMD(Player player, List<String> rewardList) {
        for (String s : rewardList) {
            if(s.contains("/")){
                s = s.replace("%p%", player.getName());
                plugin.gc.addGift
                        (plugin.SQL.getLinkedTo(player.getUniqueId().toString()), s , 0, player.getName());
            }else{
                String[] sArr = s.split(" ");

                String MATERIAL = sArr[0];
                int AMOUNT = Integer.parseInt(sArr[1]);

                plugin.gc.addGift
                        (plugin.SQL.getLinkedTo(player.getUniqueId().toString()), MATERIAL , AMOUNT, player.getName());
            }
        }
    }
    //endregion
}
