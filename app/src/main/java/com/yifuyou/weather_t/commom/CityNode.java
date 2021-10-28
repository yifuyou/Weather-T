package com.yifuyou.weather_t.commom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class CityNode {
    public String city;
    public ArrayList<CityNode> cityNodes;

    public CityNode(String city, @Nullable ArrayList<CityNode> cityNodes) {
        this.city = city;
        this.cityNodes = cityNodes;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityNode cityNode = (CityNode) o;
        if(Objects.equals(city, cityNode.city)){
/*            if(cityNodes.size()!=cityNode.cityNodes.size()){
                return false;
            }
            for(int i=0;i<cityNodes.size();i++){
                if(!cityNodes.get(i).equals(cityNode.cityNodes.get(i))){
                    return false;
                }
            }
            */
            return true;
        }
        return  false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, cityNodes);
    }

    @NonNull
    @Override
    public String toString() {
        if(cityNodes!=null){
            return "{" +
                    "\"" + city + "\" : " +
                    cityNodes +
                    '}';
        }else{
            return "\"" + city + "\"";
        }

    }
}
