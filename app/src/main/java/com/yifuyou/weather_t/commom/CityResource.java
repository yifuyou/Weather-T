package com.yifuyou.weather_t.commom;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CityResource {
    private static boolean ready = false;
    private static CityResource instance;

    private ArrayList<CityNode> cityNodesList;

    private ArrayList<ArrayList<String>> data;

    private CityResource(Context context) {
        init(context).start();
    }

    private Thread init(Context context) {
        return new Thread(() -> {
            InputStream inputStream = null;
            data = new ArrayList<>();
            try {
                inputStream = context.getAssets().open("citiesList.json");
                InputStreamReader reader = new InputStreamReader(inputStream);

                StringBuilder builder = new StringBuilder();
                char[] chs = new char[1024];
                while (reader.read(chs) != -1) {
                    builder.append(new String(chs));
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray array = jsonObject.getJSONArray("list");


                ArrayList<String> itemList;
                for (int i = 0; i < array.length(); i++) {
                    itemList = new ArrayList<>();
                    if (array.get(i) == null) {
                        break;
                    }
                    Object obj = array.get(i);

                    if (obj instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) obj;
                        for (int j = 0; j < jsonArray.length(); j++) {
                            itemList.add(jsonArray.getString(j));
                        }
                    }
                    data.add(itemList);
                }
                cityNodesList = parseNodes();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static CityResource getInstance(Context context) {
        if (instance != null) {
            return instance;
        }
        instance = new CityResource(context);
        return instance;
    }

    public static boolean initFinish() {
        return ready;
    }


    AtomicBoolean content = new AtomicBoolean(false);

    public ArrayList<CityNode> parseNodes() {
        if (cityNodesList != null && !cityNodesList.isEmpty()) {
            Log.i("TAG", "parseNodes: " + cityNodesList);
            return cityNodesList;
        }
        cityNodesList = new ArrayList<>();
        //避免栈溢出
        for (ArrayList<String> item : data) {
            if (item.isEmpty()) continue;
            cityNodesList.forEach(
                    (node) -> {
                        if (node.equals(new CityNode(item.get(6), null))) {
                            content.set(true);
                        }
                    }
            );
            if (!content.get()) {
                cityNodesList.add(new CityNode(item.get(6), null));
            }
        }
        content.set(false);
        for (ArrayList<String> item : data) {
            if (item.isEmpty()) continue;
            for (CityNode node : cityNodesList) {
                if (!node.city.equals(item.get(6))) {
                    continue;
                }

                if (node.cityNodes == null) {
                    node.cityNodes = new ArrayList<CityNode>();
                    node.cityNodes.add(new CityNode(item.get(4), null));
                    content.set(true);
                    break;
                } else {
                    for (int i = 0; i < node.cityNodes.size(); i++) {
                        if (item.get(4).equals(node.cityNodes.get(i).city)) {
                            content.set(true);
                            break;
                        }
                    }
                    if (!content.get()) {
                        node.cityNodes.add(new CityNode(item.get(4), null));
                    }
                    content.set(false);
                }
            }
        }
        content.set(false);
        for (ArrayList<String> item : data) {
            if (item.isEmpty()) continue;
            for (CityNode node : cityNodesList) {
                if (!node.city.equals(item.get(6))) {
                    continue;
                }

                for (CityNode cityNode : node.cityNodes) {
                    if (!cityNode.city.equals(item.get(4))) {
                        continue;
                    }

                    if (cityNode.cityNodes == null) {
                        cityNode.cityNodes = new ArrayList<>();
                    }
                    cityNode.cityNodes.add(new CityNode(item.get(2), null));
                    content.set(true);
                    break;
                }
                if (content.get()) {
                    break;
                }
            }
        }

        System.out.println(cityNodesList);
        ready = true;
        data.clear();
        return cityNodesList;


    }


}

