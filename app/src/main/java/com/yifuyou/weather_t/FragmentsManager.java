package com.yifuyou.weather_t;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yifuyou.weather_t.base.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentsManager {
    public static final String TAG = "FragmentsManager(MY)";


    private HashMap<Integer,BaseFragment> fragments;
    private ArrayList<Integer> indexes;
    private FragmentManager fManager;
    private int layoutId;


    public FragmentsManager(@NonNull FragmentManager manager,int layout){
        this.layoutId=layout;
        fragments=new HashMap<>();
        indexes=new ArrayList<>();
        fManager=manager;

    }


    public FragmentsManager addFragment(@NonNull BaseFragment fragment){
        Log.i(TAG, "addFragment: "+fragment.getType());
        if(fragment.getType()==null||fragment.getType().equals("")){throw new IllegalArgumentException("Fragment type was request!");}
        if(!fragments.containsValue(fragment)) {
            int index=indexes.size();
            while (indexes.contains(index)){
                index++;
            }

            fragments.put(index,fragment);
            indexes.add(index);
        }
        return this;
    }

    private int removeIndex;
    public FragmentsManager removeFragment(@NonNull BaseFragment fragment){
        if(fragments.containsValue(fragment)){
            fragments.forEach((k,v)->{
                if(v.getType().equals(fragment.getType())){
                    removeIndex=k;
                }
            });
            fragments.remove(removeIndex);
            indexes.remove(removeIndex);
        }
        return this;
    }



    /**
     * re-import all fragments
     */
    public void flash(){
        FragmentTransaction transaction = fManager.beginTransaction();
        if (!fManager.getFragments().isEmpty()) {
            for (Fragment fragment : fManager.getFragments()) {
                transaction.remove(fragment);
            }
        }
        for(int i:indexes){
            BaseFragment fragment = fragments.get(indexes.get(i));
            transaction.add(layoutId,fragment,fragment.getType());
        }
        transaction.commitNow();
    }

    private boolean isContain;
    public void addCommit(){
        FragmentTransaction transaction = fManager.beginTransaction();
        if (fManager.getFragments().isEmpty()) {

            System.out.println("================");
            for(int i:indexes){
                BaseFragment fragment = fragments.get(indexes.get(i));
                transaction.add(layoutId,fragment,fragment.getType());
            }
        }else{
            System.out.println("----------------");
            List<Fragment> fragmentList = fManager.getFragments();
            for(int i:indexes){
                BaseFragment fragment = this.fragments.get(i);
                isContain=false;
                fragmentList.forEach((f)->{
                    if(f.getTag().equals(fragment.getType())){
                        isContain=true;
                    }

                });
                //标签一致则不修改
                if(!isContain) {
                    fragment.log("add" + fragment.getType());
                    transaction.add(layoutId, fragment, fragment.getType());
                }
            }
        }
        transaction.commitNow();
    }

}


