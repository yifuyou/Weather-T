package com.yifuyou.http.pojo;

public class Alarm {
    public String alarm_type;
    public String alarm_level;
    public String alarm_content;


    public Alarm(){}
    
    public Alarm(String alarm_type, String alarm_level, String alarm_content) {
        this.alarm_type = alarm_type;
        this.alarm_level = alarm_level;
        this.alarm_content = alarm_content;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarm_type='" + alarm_type + '\'' +
                ", alarm_level='" + alarm_level + '\'' +
                ", alarm_content='" + alarm_content + '\'' +
                '}';
    }
    /*
      "alarm": {
                "alarm_type": "",
                "alarm_level": "",
                "alarm_content": ""
            }
     */

}
