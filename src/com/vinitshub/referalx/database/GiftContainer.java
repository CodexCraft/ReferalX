package com.vinitshub.referalx.database;

import com.vinitshub.referalx.ReferalX;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class GiftContainer {
    public ReferalX plugin = ReferalX.getInstance();
    public FileWriter file;
    JSONObject obj = new JSONObject();

    public void createJSONFile() {
        try {
            file = new FileWriter
                    (plugin.getDataFolder() + File.separator + "giftContainer.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(){

    }

    public void getData(){

    }
}
