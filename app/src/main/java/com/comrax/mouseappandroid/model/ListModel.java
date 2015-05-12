package com.comrax.mouseappandroid.model;

/**
 * Created by betzalel on 30/03/2015.
 */
public class ListModel {
    private  String Id ="";
    private  String CityId ="";
    private  String File="";
    private  String Update_date="";


    /*********** Set Methods ******************/
    public void setId(String Id)
    {
        this.Id = Id;
    }

    public void setCityId(String CityId)
    {
        this.CityId = CityId;
    }

    public void setFile(String File) { this.File = File; }

    public void setUpdate_date(String Update_date)
    {
        this.Update_date = Update_date;
    }






    /*********** Get Methods ****************/
    public String getId()
    {
        return this.Id;
    }

    public String getCityId()
    {
        return this.CityId;
    }

    public String getFile()
    {
        return this.File;
    }

    public String getUpdate_date()
    {
        return this.Update_date;
    }






}

