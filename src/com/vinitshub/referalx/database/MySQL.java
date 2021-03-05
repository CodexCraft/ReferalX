package com.vinitshub.referalx.database;
import java.sql.*;
import java.util.Locale;

public class MySQL {
    //region >-Variables-<
    String host = "localhost";
    String daba = "Ref";
    String port = "3306";
    String user = "root";
    String pass = "root";
    private Connection connection;
    //endregion
    //region >-SQL-<
    public boolean isConnected() {
        return (connection != null);
    }
    public void connect() throws SQLException, ClassNotFoundException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + daba + "?useSSL=false",
                    user, pass);
        }
    }
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void createTable() {
        try {
            PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS REFERAL (" +
                    "    ID INT NOT NULL AUTO_INCREMENT," +
                    "    PLAYERNAME VARCHAR(16) NOT NULL," +
                    "    PLAYERUUID VARCHAR(255) NOT NULL," +
                    "    ISLINKED VARCHAR(5) NOT NULL," +
                    "    LINKEDTO INT," +
                    "    PRIMARY KEY (ID)" +
                    ")AUTO_INCREMENT=1000;");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void insertData(String PLAYERNAME, String PLAYERUUID, String ISLINKED, int LINKEDTO) throws SQLException {
        if(!exists(PLAYERUUID)) {
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
    public boolean exists(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT * FROM REFERAL WHERE PLAYERUUID=?");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public int getCode(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT ID FROM referal WHERE PLAYERUUID=?;");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("ID");
    }
    public void setLinkedTo(String PLAYERUUID, int LINKEDTO) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("UPDATE referal SET LINKEDTO=?, ISLINKED='TRUE' WHERE PLAYERUUID=?");
        ps.setInt(1, LINKEDTO);
        ps.setString(2, PLAYERUUID);
        ps.executeUpdate();
    }
    public int getLinkedTo(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT LINKEDTO FROM referal WHERE PLAYERUUID=?");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        return rs.getInt("LINKEDTO");
        return 0;
    }
    public void resetLinked(String PLAYERUUID) throws SQLException {
        setLinkedTo(PLAYERUUID, 0);
    }
    public boolean isLinked(String PLAYERUUID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT * FROM REFERAL WHERE PLAYERUUID=?");
        ps.setString(1, PLAYERUUID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if(rs.getString("ISLINKED").equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }
    //endregion
}
