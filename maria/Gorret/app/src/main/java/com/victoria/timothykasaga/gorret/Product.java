package com.victoria.timothykasaga.gorret;

/**
 * Created by Leontymo on 4/23/2016.
 */

        import android.os.Parcel;
        import android.os.Parcelable;

public class Product
        implements Parcelable
{
    public static final Creator CREATOR = new Creator()
    {
        public Product createFromParcel(Parcel paramAnonymousParcel)
        {
            return new Product(paramAnonymousParcel);
        }

        public Product[] newArray(int paramAnonymousInt)
        {
            return new Product[paramAnonymousInt];
        }
    };
    String name;
    String prodt_id;
    double unit_cost;
    String units;

    protected Product(Parcel paramParcel)
    {
        this.name = paramParcel.readString();
        this.prodt_id = paramParcel.readString();
        this.units = paramParcel.readString();
        this.unit_cost = paramParcel.readDouble();
    }

    public Product(String name, String prodt_id, String units, double unit_cost)
    {
        this.name = name;
        this.prodt_id = prodt_id;
        this.units = units;
        this.unit_cost = unit_cost;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.name);
        paramParcel.writeString(this.prodt_id);
        paramParcel.writeString(this.units);
        paramParcel.writeDouble(this.unit_cost);
    }
}

