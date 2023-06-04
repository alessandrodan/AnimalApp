package it.uniba.dib.sms222334.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class Pathology extends Document implements Parcelable{
    private String name;

    private Pathology(String id, String name) {
        super(id);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private String bID;
        private String bName;

        private Builder(final String id, final String name){
            this.bID = id;
            this.bName=name;
        }

        public static Builder create(final String id, final String name){
            return new Builder(id,name);
        }

        public Builder setName(final String name){
            this.bName=name;
            return this;
        }

        public Pathology build(){
            return new Pathology(bID, bName);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getFirebaseID());
        dest.writeString(getName());
    }

    protected Pathology(Parcel in) {
        super(in.readString());

        this.name = in.readString();
    }

    public static final Parcelable.Creator<Pathology> CREATOR = new Parcelable.Creator<Pathology>() {
        @Override
        public Pathology createFromParcel(Parcel in) {
            return new Pathology(in);
        }

        @Override
        public Pathology[] newArray(int size) {
            return new Pathology[size];
        }
    };
}
