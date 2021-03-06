package com.vinitshub.referalx.database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    //Declaring SQL Variables to connect to the MySQL Database

    //region >-Variables-<
    String host = "mysql.mcprohosting.com";
    String daba = "server_991471_5ce9a28a";
    String user = "server_991471";
    String pass = "OD1x*ajbRfS5JZcyFAkIHiu";
    Connection connection;
    //endregion


    //Functions used to carry out data processes
    //All SQL Stuff is done here, and then used outside
    //First, I make an constructor of this class in Main Class
    //and then use Main Class's instance using ReferalX#getInstance()

    //region >-SQL-<

    /**Checks if plugin is connected to the SQL Connection */
    public boolean isConnected() {
        return (connection != null);
    }

    /**Connect to the SQL Server using the credentials */
    public void connect() throws SQLException, ClassNotFoundException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + "/" + daba + "?useSSL=false",
                    user, pass);
        }
    }

    /**Disconnect from the SQL Connection */
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**Used to get the instance of the connection to prepare statements and stuff */
    public Connection getConnection() {
        return connection;
    }

    /**Creates Table to store the data, creates everytime the plugin loads, wont create if already exists */
    public void createTable() {
        try {
            PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS REFERAL (" +
                    "    ID INT NOT NULL AUTO_INCREMENT," +
                    "    PLAYERNAME VARCHAR(16) NOT NULL," +
                    "    PLAYERUUID VARCHAR(255) NOT NULL," +
                    "    ISLINKED VARCHAR(5) NOT NULL," +
                    "    LINKEDTO INT," +
                    "    REWARDCLAIMED VARCHAR(5)," +
                    "    PRIMARY KEY (ID)" +
                    ")AUTO_INCREMENT=1000;");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**Inserting Data to the table [PLAYERNAME, PLAYERUUID, ISLINKED, LINKEDTO] */
    public void insertData(String PLAYERNAME, String PLAYERUUID, String ISLINKED, int LINKEDTO) throws SQLException {
        if(exists(PLAYERUUID)) {
            PreparedStatement ps = getConnection().prepareStatement
                    ("INSERT INTO referal (PLAYERNAME, PLAYERUUID, ISLINKED, LINKEDTO)" +
                            "VALUES (?, ?, ?, ?);");
            ps.setString(1, PLAYERNAME);
            ps.setString(2, PLAYERUUID);
            ps.setString(3, ISLINKED);
            ps.setInt(4, LINKEDTO);
            ps.executeUpdate();
        }
    }

    /**Checks if player has joined and is present in the Table
     * INVERTED ON 12/03/2021*/
    public boolean exists(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT * FROM REFERAL WHERE PLAYERUUID=?");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    /**Gets the code using PLAYER's UUID, used in com.vinitshub.referalx.commands.ReferalCode */
    public int getCode(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT ID FROM referal WHERE PLAYERUUID=?;");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("ID");
    }

    /**Sets the LinkedCode to the one given in args, mostly after the player resets */
    public void setLinkedTo(String PLAYERUUID, int LINKEDTO) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("UPDATE referal SET LINKEDTO=?, ISLINKED='TRUE' WHERE PLAYERUUID=?");
        ps.setInt(1, LINKEDTO);
        ps.setString(2, PLAYERUUID);
        ps.executeUpdate();
    }

    /**Gets the LinkedCode to give the rewards to player, used in com.vinitshub.referalx.events.Events */
    public String getLinkedTo(String PLAYERUUID) {
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement
                    ("SELECT LINKEDTO FROM referal WHERE PLAYERUUID=?");

            ps.setString(1, PLAYERUUID);
            ResultSet rs = ps.executeQuery();
            int linkedToCode = 0;

            if(rs.next())
                linkedToCode =  rs.getInt("LINKEDTO");

            if(linkedToCode != 0) {
                PreparedStatement ps2 = getConnection().prepareStatement
                        ("SELECT PLAYERNAME FROM referal WHERE LINKEDTO=?");
                ps2.setInt(1, linkedToCode);
                ResultSet rs2 = ps2.executeQuery();
                return rs2.getString("PLAYERNAME");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**Returns if the Player has linked their account to others */
    public boolean isLinked(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT * FROM REFERAL WHERE PLAYERUUID=?");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("ISLINKED").equalsIgnoreCase("true");
    }

    /** Gets Refers Account and their current Rank*/
    public List<String> getRefers(String PLAYERNAME){
        try {
            PreparedStatement ps = getConnection().prepareStatement
                    ("SELECT PLAYERNAME FROM referal WHERE LINKEDTO = ?;");
            Player player = Bukkit.getServer().getPlayer(PLAYERNAME);
            assert player != null;
            ps.setInt(1, getCode(player.getUniqueId().toString()));
            ResultSet rs = ps.executeQuery();
            List<String> refers = new ArrayList<>();
            while (rs.next()){
                refers.add(rs.getString("PLAYERUUID"));
            }
            return refers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /** Updates the Refers account so they cant give the rewards to someone else again and again*/
    public void updateRewardClaim(String PLAYERUUID, boolean claimStatus){

    }
    //endregion
}
