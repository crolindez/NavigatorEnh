package es.carlosrolindez.navigatorenh;

import android.os.Parcel;
import android.os.Parcelable;


public class FileDescription implements Parcelable{

	public String fileName;
	public boolean isFolder;
	public long size;
	public String type;
	
	public static final String[] typeList = new String[] {"","CI","EE","ET","MA","MF","PM","H"};
	public static final String[] pathList = new String[] {
			"",
			"Circuitos impresos",
			"Esquemas electricos",
			"Especificaciones tecnicas",
			"Manuales",
			"Montajes fabricacion",
			"Planos mecanicos",
			"Homologaciones",
			""};
	
	FileDescription (String fileName, boolean[] boolArray, long size, String type)
	{
        this.fileName = fileName;
        this.isFolder = boolArray[0];
        this.size = size;
        this.type = type;		

	}
	
	public FileDescription() {
        this.fileName = "";
        this.isFolder = false;
        this.size = 0;
        this.type = "";
	}

	@Override
    public int describeContents() {
        return 0;
    }
	
    @Override
    public void writeToParcel(Parcel parcel, int arg1) 
    {
    	boolean[] boolArray={isFolder};
    	
        parcel.writeString(fileName);
        parcel.writeBooleanArray(boolArray);
        parcel.writeLong(size);
        parcel.writeString(type);
    }
     
    public static final Creator<FileDescription> CREATOR = new Creator<FileDescription>() {
    	
    	boolean[] boolArray;
        
    	@Override
        public FileDescription createFromParcel(Parcel parcel) 
        {        	
            String fileName = parcel.readString();
            parcel.readBooleanArray(boolArray);
            long size = parcel.readLong();
            String type = parcel.readString();
                
            return new FileDescription(fileName,boolArray,size,type);
        }
 
        @Override
        public FileDescription[] newArray(int size) 
        {
            return new FileDescription[size];
        }
         
    };

}
