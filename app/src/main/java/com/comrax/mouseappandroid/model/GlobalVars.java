package com.comrax.mouseappandroid.model;

import java.util.ArrayList;

/**
 * Created by betzalel on 16/04/2015.
 */
public class GlobalVars {

    public static final String IconFolder = "/sdcard/Mouse_App/Default_master/Images/MenuIcons/";
    public static final String[] Nav10_imageList = {"drawer_items0", "drawer_items1", "drawer_items2", "drawer_items3", "drawer_items4", "drawer_items5", "drawer_items6" };

    public static final String[] detailsListTitles = {"עצירות חובה", "", "", "", "", "", "מידע שימושי", "אפליקציית שיחות מחול", "טיסות לחול", "השכרת רכב", "ביטוח נסיעות"  };
    public static final String[] detailsListImages = {IconFolder+"must_see_BIG.png", "", "", "", "", "", IconFolder+"info_BIG.png", IconFolder+"228x216-bphone.jpg", IconFolder+"icons_flight_BIG.png", IconFolder+"spons_car_rent_BIG.png", IconFolder+"spons_insurance_BIG.png" };


    public static ArrayList<InitDataModel> initDataModelArrayList;
}
