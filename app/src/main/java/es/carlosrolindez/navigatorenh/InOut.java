package es.carlosrolindez.navigatorenh;

import android.os.Parcel;
import android.os.Parcelable;


public class InOut implements Parcelable{

	public String document;
	public String date;
	public String quantity;
	public String source;
	public boolean programmed;	
	public boolean inMode;

	
	InOut (String document, String date, String quantity, String source, boolean[] boolArray)
	{    
        this.document = document;
        this.date = date;
        this.quantity = quantity;
        this.source = source;
        this.programmed = boolArray[0];
        this.inMode = boolArray[1];
	}
	
	public InOut() 
	{	 
        this.document = "";
        this.date = "";
        this.quantity = "";
        this.source = "";
        this.programmed = false;
        this.inMode = false;
}

	@Override
    public int describeContents() {
        return 0;
    }
	
    @Override
    public void writeToParcel(Parcel parcel, int arg1) 
    {
    	boolean[] boolArray={programmed, inMode};
	
        parcel.writeString(document);
        parcel.writeString(date);
        parcel.writeString(quantity);
        parcel.writeString(source);
        parcel.writeBooleanArray(boolArray);
        
      }
     
    public static final Creator<InOut> CREATOR = new Creator<InOut>()
    {
    	boolean[] boolArray;
    	
        @Override
        public InOut createFromParcel(Parcel parcel) {
            String document = parcel.readString();
            String date = parcel.readString();
            String quantity = parcel.readString();
            String source = parcel.readString();
            parcel.readBooleanArray(boolArray);

            return new InOut(document, date, quantity, source, boolArray);
        }
 
        @Override
        public InOut[] newArray(int size) {
            return new InOut[size];
        }
         
    };

}
