package com.conntac.hackernews.util;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by Omer YILDIRIM on 7/18/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class Tools {
    public  static String ConvertToDateTimeString(Long timeLong){
      try {
         return DateFormat.format("MM/dd/yyyy hh:mm:ss", new Date(timeLong)).toString();
      }catch (Exception e){
          return "";
      }
    }
    public static boolean isNullOrBlank(String s)
    {
        return (s==null || s.trim().equals(""));
    }
}
