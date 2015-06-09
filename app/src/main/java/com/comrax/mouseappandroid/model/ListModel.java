package com.comrax.mouseappandroid.model;

/**
 * Created by betzalel on 30/03/2015.
 */
public class ListModel {
    private  String Title="";
    private  String Date="";
    private  String StartDate="";
    private  String EndDate="";
    private  String Country="";

    private  String ImageLarge="";
    private  String ImageSmall="";
    private  String Nid="";


    /*********** Set Methods ******************/
    public void setTitle(String Title) { this.Title = Title; }

    public void setDate(String Date)
    {
        this.Date = Date;
    }

    public void setCountry(String Country)
    {
        this.Country = Country;
    }

    public void setImageLarge(String ImageLarge)
    {
        this.ImageLarge= ImageLarge;
    }

    public void setImageSmall(String ImageSmall)
    {
        this.ImageSmall = ImageSmall;
    }

    public void setNid(String Nid)
    {
        this.Nid = Nid;
    }


    public void setRawDateStart(String StartDate)
    {
        this.StartDate = StartDate;
    }

    public void setRawDateEnd(String EndDate)
    {
        this.EndDate = EndDate;
    }



    /*********** Get Methods ****************/
    public String getTitle()
    {
        return this.Title;
    }

    public String getDate()
    {
        return this.Date;
    }

    public String getCountry()
    {
        return this.Country;
    }

    public String getImageLarge()
    {
        return this.ImageLarge;
    }

    public String getImageSmall()
    {
        return this.ImageSmall;
    }

    public String getNid()
    {
        return this.Nid;
    }


    public String getStartDate()
    {
        return this.StartDate;
    }

    public String getEndDate()
    {
        return this.EndDate;
    }


}

