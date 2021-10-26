package com.yifuyou.weather_t.commom;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
                StringBuilder builder = new StringBuilder();
                byte[] bytes = new byte[1024];
                while (inputStream.read(bytes) != -1) {
                    builder.append(new String(bytes));
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
                Log.i("TAG", "parseNodes: "+cityNodesList);
            return cityNodesList;
        }
        cityNodesList = new ArrayList<>();
        System.out.println(data);
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
                if (node.city.equals(item.get(6))) {
                    if (node.cityNodes == null) {
                        node.cityNodes = new ArrayList<CityNode>();
                    }
                    node.cityNodes.add(new CityNode(item.get(4), null));
                    content.set(true);
                    break;
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


        ready = true;
        data.clear();
        return cityNodesList;


    }

    private ArrayList<CityNode> getMoreNode(int type, String node) {
        ArrayList<CityNode> arrayList = new ArrayList<>();
        if (type == 1) {
            for (ArrayList<String> item : data) {
                if (node.equals(item.get(6))) {
                    arrayList.add(new CityNode(item.get(4), getMoreNode(2, item.get(4))));
                }
            }
        } else if (type == 2) {
            for (ArrayList<String> item : data) {
                if (node.equals(item.get(4))) {
                    arrayList.add(new CityNode(item.get(2), null));
                }
            }
        }
        return arrayList;
    }

}

