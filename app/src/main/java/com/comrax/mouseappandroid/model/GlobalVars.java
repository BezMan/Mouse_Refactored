package com.comrax.mouseappandroid.model;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by betzalel on 16/04/2015.
 */
public class GlobalVars {

    public static final String IconFolder = "/sdcard/Mouse_App/Default_master/Images/MenuIcons/";

    public static final String[] Drawer_textList = {"מדריך לערים נוספות", "המועדפים שלי", "שתף אפליקציה", "הוסף תגובה על האפליקציה", "עזרה", "תקנון", "אודות" , "", "", ""};
    public static final String[] Drawer_imageList = {"drawer0", "drawer1", "drawer2", "drawer3", "drawer4", "drawer5", "drawer6", "", "", "" };

    public static final String[] detailsListTitles = {"עצירות חובה", "מידע שימושי", "אפליקציית שיחות מחול", "טיסות לחול", "השכרת רכב", "ביטוח נסיעות"  };
    public static final String[] detailsListImages = {IconFolder+"must_see_BIG.png", IconFolder+"info_BIG.png", IconFolder+"228x216-bphone.jpg", IconFolder+"icons_flight_BIG.png", IconFolder+"spons_car_rent_BIG.png", IconFolder+"spons_insurance_BIG.png" };


    public static ArrayList<InitDataModel> initDataModelArrayList;

    public static JSONArray staticPagesArray;
}
