package com.vinitshub.referalx.database;
import com.vinitshub.referalx.ReferalX;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.md_5.bungee.api.ChatColor.*;

public class GiftContainer {

    public Inventory inv;

    private final ReferalX plugin = ReferalX.getInstance();
    private final Connection connection = plugin.SQL.getConnection();
    String[] columns = {"IGNORE","GIFTREMAINING1", "GIFTREMAINING2", "GIFTREMAINING3", "GIFTREMAINING4", "GIFTREMAINING5", "GIFTREMAINING6", "GIFTREMAINING7", "GIFTREMAINING8", "GIFTREMAINING9", "GIFTREMAINING10", "GIFTREMAINING11", "GIFTREMAINING12", "GIFTREMAINING13", "GIFTREMAINING14", "GIFTREMAINING15", "GIFTREMAINING16", "GIFTREMAINING17", "GIFTREMAINING18", "GIFTREMAINING19", "GIFTREMAINING20", "GIFTREMAINING21", "GIFTREMAINING22", "GIFTREMAINING23", "GIFTREMAINING24", "GIFTREMAINING25", "GIFTREMAINING26", "GIFTREMAINING27", "GIFTREMAINING28", "GIFTREMAINING29", "GIFTREMAINING30", "GIFTREMAINING31", "GIFTREMAINING32", "GIFTREMAINING33", "GIFTREMAINING34", "GIFTREMAINING35", "GIFTREMAINING36"};



    public Inventory getInv(String PLAYERUUID) throws SQLException {
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
        closeMeta.setLore(claimAllLore);
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
        arrowNextMeta.setDisplayName(GREEN + "Next Page");
        arrowNext.setItemMeta(arrowNextMeta);
        //endregion

        //region ArrowBack
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowNext.getItemMeta();
        arrowBackMeta.setDisplayName(GREEN + "Previous Page");
        arrowBack.setItemMeta(arrowBackMeta);
        //endregion
        inv.setItem(37, head);
        inv.setItem(40, arrowNext);
        inv.setItem(41, claimAll);
        inv.setItem(42, arrowBack);
        inv.setItem(45, close);

        for (int i = 1; i < 36; i++) {
            ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(getGifts(i, PLAYERUUID))));
            inv.addItem(itemStack);
        }
        return inv;
    }

    public void createTable() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS REFERALGIFT (" +
                "ID INT NOT NULL AUTO_INCREMENT," +
                "PLAYERNAME VARCHAR(16) ," +
                "PLAYERUUID VARCHAR(255) ," +
                "GIFTREMAINING1 VARCHAR(255) ," +
                "GIFTREMAINING2 VARCHAR(255) ," +
                "GIFTREMAINING3 VARCHAR(255) ," +
                "GIFTREMAINING4 VARCHAR(255) ," +
                "GIFTREMAINING5 VARCHAR(255) ," +
                "GIFTREMAINING6 VARCHAR(255) ," +
                "GIFTREMAINING7 VARCHAR(255) ," +
                "GIFTREMAINING8 VARCHAR(255) ," +
                "GIFTREMAINING9 VARCHAR(255) ," +
                "GIFTREMAINING10 VARCHAR(255) ," +
                "GIFTREMAINING11 VARCHAR(255) ," +
                "GIFTREMAINING12 VARCHAR(255) ," +
                "GIFTREMAINING13 VARCHAR(255) ," +
                "GIFTREMAINING14 VARCHAR(255) ," +
                "GIFTREMAINING15 VARCHAR(255) ," +
                "GIFTREMAINING16 VARCHAR(255) ," +
                "GIFTREMAINING17 VARCHAR(255) ," +
                "GIFTREMAINING18 VARCHAR(255) ," +
                "GIFTREMAINING19 VARCHAR(255) ," +
                "GIFTREMAINING20 VARCHAR(255) ," +
                "GIFTREMAINING21 VARCHAR(255) ," +
                "GIFTREMAINING22 VARCHAR(255) ," +
                "GIFTREMAINING23 VARCHAR(255) ," +
                "GIFTREMAINING24 VARCHAR(255) ," +
                "GIFTREMAINING25 VARCHAR(255) ," +
                "GIFTREMAINING26 VARCHAR(255) ," +
                "GIFTREMAINING27 VARCHAR(255) ," +
                "GIFTREMAINING28 VARCHAR(255) ," +
                "GIFTREMAINING29 VARCHAR(255) ," +
                "GIFTREMAINING30 VARCHAR(255) ," +
                "GIFTREMAINING31 VARCHAR(255) ," +
                "GIFTREMAINING32 VARCHAR(255) ," +
                "GIFTREMAINING33 VARCHAR(255) ," +
                "GIFTREMAINING34 VARCHAR(255) ," +
                "GIFTREMAINING35 VARCHAR(255) ," +
                "GIFTREMAINING36 VARCHAR(255) ," +
                "PRIMARY KEY (ID));");
        ps.executeUpdate();
    }

    public String getGifts(int index, String PLAYERUUID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("SELECT GIFTREMAINING"+ index +" FROM referalgift WHERE PLAYERUUID = ?;");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        return rs.getString("GIFTREMAINING" + index);
    }

    public void addGift(String PLAYERUUID, String COMMAND) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO referalgift(?)" +
                "VALUES(?);");
        int nullColumn = checkNull(PLAYERUUID);
        ps.setString(1, columns[nullColumn]);
        ps.setString(2, COMMAND);
    }

    public int checkNull(String PLAYERUUID) throws SQLException {
        ResultSet rs = null;
        int nullColumn = 0;
        for (int i = 1; i < 36; i++) {
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT GIFTREMAINING" + i + " FROM referalgift WHERE PLAYERUUID=?");
            ps.setString(1, PLAYERUUID);
            rs = ps.executeQuery();
        }
        while (rs.next()) {
            nullColumn++;
        }
        return nullColumn;
    }


}
