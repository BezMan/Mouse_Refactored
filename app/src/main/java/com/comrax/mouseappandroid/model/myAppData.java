package com.comrax.mouseappandroid.model;

/**
 * Created by betzalel on 25/03/2015.
 */
public class myAppData {

    public static String userID, userCode, footerTxt, privacyTxt, legalTxt, helpTxt;


    public static void setFooterTxt(String s){
        footerTxt=s;
    }

    public static void setPrivacyTxt(String s){
        privacyTxt=s;
    }

    public static void setLegalTxt(String s){
        legalTxt=s;
    }



    public static String getFooter(){ return footerTxt; }

    public static String getPrivacyTxt(){
        return privacyTxt;
    }

    public static String getLegalTxt(){
        return legalTxt;
    }




}
