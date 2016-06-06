package com.ObsidianReach.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;

import com.ObsidianReach.Core;
import com.google.common.collect.Maps;

public class DatabaseHandler {
    private MySQL database;

    public DatabaseHandler(String ip, String port, String dbName, String pass, String user) {
        this.database = new MySQL(user, dbName, pass, port, ip);

        try {
            this.database.openConnection();
            Bukkit.getLogger().info("CONNECTED TO DATABASE");
        } catch (SQLException var8) {
            var8.printStackTrace();

            for(int i = 0; i < 10; ++i) {
                System.out.println("COULDN\'T CONNECT TO DATABASE");
            }

            Bukkit.getPluginManager().disablePlugin(Core.getInstance());
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

    }

    public void closeConnection() {
        if(this.database.connection != null) {
            try {
                this.database.connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }

        }
    }

    public int getIntByUUID(String table, String columnName, String uuid) {
        try {
            ResultSet e = this.database.query("SELECT * FROM " + table + " WHERE UUID = \'" + uuid + "\';");
            if(e.next()) {
                return e.getInt(columnName);
            }
        } catch (Exception var5) {
            Bukkit.getLogger().info(uuid + " data not found in Table: " + table + " Column: " + columnName);
        }

        return 0;
    }

    public String getStringByUUID(String table, String columnName, String uuid) {
        try {
            ResultSet e = this.database.query("SELECT * FROM " + table + " WHERE UUID = \'" + uuid + "\';");
            if(e.next()) {
                return e.getString(columnName);
            }
        } catch (Exception var5) {
            Bukkit.getLogger().info(uuid + " data not found in Table: " + table + " Column: " + columnName);
        }

        return null;
    }

    public Object getObjectByUUID(String table, String columnName, String uuid) {
        try {
            ResultSet e = this.database.query("SELECT * FROM " + table + " WHERE UUID = \'" + uuid + "\';");
            if(e.next()) {
                return e.getObject(columnName);
            }
        } catch (Exception var5) {
            Bukkit.getLogger().info(uuid + " data not found in Table: " + table + " Column: " + columnName);
        }

        return null;
    }

    public ResultSet getResultSetByUUID(String table, String uuid) {
        try {
            ResultSet e = this.database.query("SELECT * FROM " + table + " WHERE UUID = \'" + uuid + "\';");
            if(e.next()) {
                return e;
            }
        } catch (Exception var4) {
            Bukkit.getLogger().info(uuid + " data not found in Table: " + table);
        }

        return null;
    }

    public ResultSet getSet(String query) {
        try {
            System.out.println(query);
            return this.database.query(query);
        } catch (Exception var3) {
            Bukkit.getLogger().info("");
            Bukkit.getLogger().info("Query failed");
            Bukkit.getLogger().info("==========================");
            Bukkit.getLogger().info(query);
            Bukkit.getLogger().info("==========================");
            Bukkit.getLogger().info("Query failed");
            Bukkit.getLogger().info("");
            return null;
        }
    }

    public boolean exists(String table, String column, String value) {
        try {
            return this.database.query("SELECT * FROM " + table + " WHERE " + column + " = \'" + StringEscapeUtils.escapeSql(value) + "\';").next();
        } catch (Exception var5) {
            return false;
        }
    }

    public void createTable(String table, List<String> keys) {
        String query = "";

        try {
            query = "CREATE TABLE IF NOT EXISTS " + table + "(`" + ((String)keys.get(0)).split(Pattern.quote(";"))[0] + "` " + ((String)keys.get(0)).split(Pattern.quote(";"))[1];

            for(int e = 1; e < keys.size(); ++e) {
                String[] s = ((String)keys.get(e)).split(Pattern.quote(";"));
                query = query + ", `" + s[0] + "` " + s[1];
            }

            query = query + ", PRIMARY KEY(`" + ((String)keys.get(0)).split(Pattern.quote(";"))[0] + "`));";
            this.database.update(query);
            Bukkit.getLogger().info(table + " has been created");
            Bukkit.getLogger().info(query);
        } catch (Exception var6) {
            Bukkit.getLogger().info("COULD NOT RUN DB QUERY");
            Bukkit.getLogger().info("");
            Bukkit.getLogger().info(query);
        }

    }

    public void set(String table, HashMap<String, Object> map, String whereClause, String whereValue) {
        try {
            String[] e = (String[])map.keySet().toArray(new String[map.keySet().size()]);
            String query = "";
            int i;
            String s;
            if(this.exists(table, whereClause, whereValue)) {
                query = "UPDATE " + table + " SET " + e[0] + " = \'" + map.get(e[0]) + "\'";
                i = 1;

                while(true) {
                    if(i >= map.keySet().size()) {
                        query = query + " WHERE " + whereClause + " = \'" + whereValue + "\';";
                        break;
                    }

                    s = e[i];
                    query = query + ", " + s + " = \'" + map.get(s) + "\'";
                    ++i;
                }
            } else {
                query = "INSERT INTO " + table + " (`" + e[0] + "`";

                for(i = 1; i < map.keySet().size(); ++i) {
                    s = e[i];
                    query = query + ", `" + s + "`";
                }

                query = query + ") VALUES (\'" + map.get(e[0]) + "\'";

                for(i = 1; i < map.keySet().size(); ++i) {
                    s = e[i];
                    query = query + ", \'" + map.get(s) + "\'";
                }

                query = query + ");";
            }

            this.database.update(query);
            System.out.println("Ran query " + query);
        } catch (Exception var9) {
            var9.printStackTrace();
            Bukkit.getLogger().info("Error setting in " + table);
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void setPlus(String table, String col, Integer val, String whereClause, String whereValue) {
        try {
            int e = val.intValue();
            ResultSet set = this.getSet("SELECT * FROM " + table + " WHERE " + whereClause + "=\'" + whereValue + "\';");
            if(set.next()) {
                e += set.getInt(col);
            }

            HashMap map = Maps.newHashMap();
            map.put(col, Integer.valueOf(e));
            this.set(table, map, whereClause, whereValue);
        } catch (Exception var9) {
            var9.printStackTrace();
            Bukkit.getLogger().info("Error setting+ in " + table);
        }

    }

    public void add(String table, HashMap<String, String> map) {
        String[] keySet = (String[])map.keySet().toArray(new String[map.keySet().size()]);
        String query = "INSERT INTO " + table + " (`" + keySet[0] + "`";

        int e;
        String s;
        for(e = 1; e < map.keySet().size(); ++e) {
            s = keySet[e];
            query = query + ", `" + s + "`";
        }

        query = query + ") VALUES (\'" + (String)map.get(keySet[0]) + "\'";

        for(e = 1; e < map.keySet().size(); ++e) {
            s = keySet[e];
            query = query + ", \'" + (String)map.get(s) + "\'";
        }

        query = query + ");";
        System.out.println(query + " haz been run!");

        try {
            this.database.update(query);
        } catch (SQLException | ClassNotFoundException var7) {
            var7.printStackTrace();
            Bukkit.getLogger().info("Error adding in " + table);
        }

    }

    public void update(String string) {
        try {
            System.out.println(string + " run");
            this.database.update(string);
        } catch (SQLException | ClassNotFoundException var3) {
            var3.printStackTrace();
        }

    }
}