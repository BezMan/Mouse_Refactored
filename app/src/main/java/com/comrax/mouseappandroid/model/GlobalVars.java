package com.comrax.mouseappandroid.model;

import java.util.ArrayList;

/**
 * Created by betzalel on 16/04/2015.
 */
public class GlobalVars {

    public static final String IconFolder = "/sdcard/Mouse_App/Default_master/Images/MenuIcons/";
    public static final String[] Nav10_imageList = {"drawer_items0", "drawer_items1", "drawer_items2", "drawer_items3", "drawer_items4", "drawer_items5", "drawer_items6" };

    public static final String[] detailsListTitles = {"עצירות חובה", "", "", "", "", "", "מידע שימושי", "אפליקציית שיחות מחול", "טיסות לחול", "השכרת רכב", "ביטוח נסיעות"  };
    public static final String[] detailsListImages = {IconFolder+"must_see.png", "", "", "", "", "", "", IconFolder+"info.png", IconFolder+"76x72_bphone.jpg", IconFolder+"icons_flight.png", IconFolder+"spons_car_rent.png", IconFolder+"spons_insurance.png" };


    public static ArrayList<InitDataModel> initDataModelArrayList;
}
