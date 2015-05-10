package com.comrax.mouseappandroid;

import java.util.ArrayList;

/**
 * Created by betzalel on 16/04/2015.
 */
public class GlobalVars {

    public static final String SERVER_URL = "https://confinder.comrax.com/srvapi";

    public static final String[] ColorNames = {"orange_", "red_", "grey_", "purple_", "blue_", "light_blue_", "green_", "yellow_"};
    public static final String[] ColorCodes = {"#eb8f2c", "#e31232", "#706e73", "#641f89", "#284f94", "#3f98dc", "#7bc741", "#ecf247"};

    public static final String[] Nav10_textList = {"Search", "Show Favorites", "All", "Israeli", "Abroad", "New", "Archive", "Contact Us", "Help", "Logout"};
    public static final String[] Nav10_imageList = {"menu_icon_search", "menu_icon_favorites", "menu_icon_all", "menu_icon_israel", "menu_icon_abroad",
            "menu_icon_new", "menu_icon_archive", "menu_icon_contact", "menu_icon_help", "menu_icon_logout"};


    public static final String[] Nav6_textList = {"Save The Date", "Add To Favorites", "Navigate", "Attending", "Send to Friend", "Contact Us"};
    public static final String[] Nav6_imageList = {"menu_blue_calendar", "menu_blue_star", "menu_blue_gps", "menu_blue_thumb", "menu_blue_envelope", "menu_blue_phone"};

    public static final String[] Nav6_imageList2 = {"menu_blue_calendar", "menu_blue_star", "menu_blue_gps", "thumb_icon_white", "menu_blue_envelope", "menu_blue_phone"};

    public static final String COOKIE_PASSWORD = "CookiePassword";

    public static ArrayList<String> countryKeys, countryValues, subjectKeys, subjectValues, typeKeys, typeValues;

}
