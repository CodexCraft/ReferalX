package com.vinitshub.referalx.database;
import com.vinitshub.referalx.ReferalX;
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
import java.util.List;
import java.util.Objects;

import static net.md_5.bungee.api.ChatColor.*;

public class GiftContainer {

    private final ReferalX plugin = ReferalX.getInstance();
    FileWriter GiftContainerJSON;
    JSONObject root = new JSONObject();

    public Inventory inv;

    public Inventory getInv(String PLAYERNAME){
        //region ClaimAll
        ItemStack claimAll = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta claimAllMeta = claimAll.getItemMeta();
        assert claimAllMeta != null;
        claimAllMeta.setDisplayName(BOLD + "" + GOLD + "Claim All");
        List<String> claimAllLore = new ArrayList<String>();
        claimAllLore.add(GREEN + "Claims all gifts in one go");
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

        inv.setItem(37, head);
        inv.setItem(40, arrowNext);
        inv.setItem(41, claimAll);
        inv.setItem(42, arrowBack);
        inv.setItem(45, close);

//        for (int i = 1; i < 36; i++) {
//            ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(getGifts(PLAYERNAME))));
//            inv.addItem(itemStack);
//        }
        return inv;
    }

    public void createFile(){
        try {
            GiftContainerJSON = new FileWriter("GiftContainerJSON", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getGifts(String PLAYERNAME){
        JSONObject player = (JSONObject) root.get(PLAYERNAME);
        for (JSONObject items: player) {

        }
        return null;
    }

    public void addGift(String PLAYERNAME, String MATERIAL){

    }
}
