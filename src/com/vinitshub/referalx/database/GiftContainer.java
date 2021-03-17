package com.vinitshub.referalx.database;
import com.vinitshub.referalx.ReferalX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static net.md_5.bungee.api.ChatColor.*;

public class GiftContainer {

    public Inventory inv;
    public FileWriter GiftContainerJSON;
    public JSONObject root = new JSONObject();
    private final ReferalX plugin = ReferalX.getInstance();

    /** Creates the JSON File everytime the plugin starts */
    public void createFile(){
        try {
            GiftContainerJSON = new FileWriter(plugin.getDataFolder() + "GiftContainerJSON", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Creates the inventory using the Player's name from the JSONFile */
    public Inventory getInv(String PLAYERNAME){

        inv = Bukkit.createInventory(null, 9, PLAYERNAME + "'s Gifts");

        //region ClaimAll
        ItemStack claimAll = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta claimAllMeta = claimAll.getItemMeta();
        assert claimAllMeta != null;
        claimAllMeta.setDisplayName(BOLD + "" + GOLD + "Claim All");
        List<String> claimAllLore = new ArrayList<>();
        claimAllLore.add(GREEN + "Claims all gifts in one click");
        claimAllMeta.setLore(claimAllLore);
        claimAll.setItemMeta(claimAllMeta);
        //endregion
        //region Close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        assert closeMeta != null;
        closeMeta.setDisplayName(BOLD + "" + GOLD + "Close");
        List<String> closeLore = new ArrayList<String>();
        closeLore.add(GREEN + "Closes the GUI");
        closeMeta.setLore(closeLore);
        close.setItemMeta(closeMeta);
        //endregion
        //region Head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta headMeta = head.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName(BOLD + "" + GOLD + "Show Referals");
        List<String> headLore = new ArrayList<String>();
        headLore.add(GREEN + "Shows all the players you have referred");
        headMeta.setLore(headLore);
        head.setItemMeta(headMeta);
        //endregion
        //region ArrowNext
        ItemStack arrowNext = new ItemStack(Material.ARROW);
        ItemMeta arrowNextMeta = arrowNext.getItemMeta();
        assert arrowNextMeta != null;
        arrowNextMeta.setDisplayName(GREEN + "Next Page");
        arrowNext.setItemMeta(arrowNextMeta);
        //endregion
        //region ArrowBack
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        assert arrowBackMeta != null;
        arrowBackMeta.setDisplayName(GREEN + "Previous Page");
        arrowBack.setItemMeta(arrowBackMeta);
        //endregion
        //region Pane
        ItemStack pane = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta paneMeta = pane.getItemMeta();
        assert paneMeta != null;
        paneMeta.setDisplayName(GREEN + "GiftContainer");
        pane.setItemMeta(paneMeta);
        //endregion

        //region Setting Items in Inventory
        inv.setItem(36, head);
        inv.setItem(37, pane);
        inv.setItem(38, pane);
        inv.setItem(39, arrowNext);
        inv.setItem(40, claimAll);
        inv.setItem(41, arrowBack);
        inv.setItem(42, pane);
        inv.setItem(43, pane);
        inv.setItem(44, close);

        //Gets Player JSONObject from GiftContainer.json
        JSONObject player = getGifts(PLAYERNAME);

        //Looping through all items in the Player JSONObject
        for (int i = 0; i < player.size(); i++) {
            JSONObject item = (JSONObject) player.get(i);
            String MATERIAL = (String) item.get("MATERIAL");
            int AMOUNT = (int) item.get("AMOUNT");
            ItemStack giftItem = new ItemStack(Objects.requireNonNull(Material.getMaterial(MATERIAL)), AMOUNT);
            inv.setItem(i, giftItem);
        }
        //endregion


        //Returns the inventory with all items
        return inv;
    }

    /** Returns the Player JSONObject to get gifts from it */
    public JSONObject getGifts(String PLAYERNAME){
        return (JSONObject) root.get(PLAYERNAME);
    }

    /** Adds gift to player's JSONObject */
    public void addGift(String PLAYERNAME, String MATERIAL, int AMOUNT){
        JSONObject player = (JSONObject) root.get(PLAYERNAME);
        int playerSize = player.size();
        JSONObject item = (JSONObject) player.put("item" + playerSize, "");
        item.put("MATERIAL", MATERIAL);
        item.put("AMOUNT", AMOUNT);
    }

    /** Removes all the gifts from player's inventory */
    public void removeGifts(String PLAYERNAME){
        JSONObject player = (JSONObject) root.get(PLAYERNAME);
        player.clear();
    }

    /** Writes the JSONObject to the file */
    public void writeAll(){
        try {
            GiftContainerJSON.write(root.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
