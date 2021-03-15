package com.vinitshub.referalx.database;

import com.vinitshub.referalx.ReferalX;

import java.sql.*;

public class GiftContainer {
    private final ReferalX plugin = ReferalX.getInstance();
    private final Connection connection = plugin.SQL.getConnection();

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
        PreparedStatement ps = connection.prepareStatement("INSERT");
    }

    public int checkNull() throws SQLException {
        ResultSet rs = null;
        int nullColumn = 0;
        for (int i = 1; i < 36; i++) {
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT GIFTREMAINING" + i + " FROM referalgift");
            rs = ps.executeQuery();
        }
        while (rs.next()) {
            nullColumn++;
        }
        return nullColumn;
    }
}
