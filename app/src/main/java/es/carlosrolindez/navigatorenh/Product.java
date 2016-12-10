package es.carlosrolindez.navigatorenh;

import android.os.Parcel;
import android.os.Parcelable;


public class Product implements Parcelable{
	public Boolean inBOM;
	public String reference;
	public String description;
	public String quantity;
	public String stock;
	public String cost;
	public String price;
	
	public Boolean hasBOM;
	
//	public String handWorkCost;
	
	public String purchase;
	public String inProduction;
	public String inPlannedProduction;
	
	public String sale;
	public String usedInProduction;
	public String transfer;
	public String usedInPlannedProduction;
	public String orderPoint;
	
	public static final int NUMBER_OF_MONTHS = 24;
	
	public String[] consumeByMonth = new String[NUMBER_OF_MONTHS];
	
	
	public int itemMode;
	
	
	
	
	Product (String reference, String description, String quantity, String stock, String cost, String price, boolean[] boolArray,
			String purchase, String inProduction, String inPlannedProduction, String sale, String usedInProduction,
			String transfer, String usedInPlannedProduction, String ordenPoint, String[] consumeByMonth, int itemMode)
	{
        this.reference = reference;
        this.description = description;
        this.quantity = quantity;
        this.stock = stock;
        this.cost = cost;
        this.price = price;
        this.inBOM = boolArray[0];
        this.hasBOM = boolArray[1];   
        
        this.purchase = purchase;
        this.inProduction = inProduction;
        this.inPlannedProduction = inPlannedProduction;
    	
        this.sale = sale;
        this.usedInProduction = usedInProduction;
        this.transfer = transfer;
        this.usedInPlannedProduction = usedInPlannedProduction;
        this.orderPoint = ordenPoint;
        
        System.arraycopy(consumeByMonth, 0, this.consumeByMonth, 0, NUMBER_OF_MONTHS);
        
        
        this.itemMode = itemMode;
	}
	
	public Product() {
        this.reference = "";
        this.description = "";
        this.quantity = "";
        this.stock = "";
        this.cost = "";
        this.price = "";
        this.inBOM = false;
        this.hasBOM = false;        
        
        this.purchase = "";
        this.inProduction = "";
        this.inPlannedProduction = "";
    	
        this.sale = "";
        this.usedInProduction = "";
        this.transfer = "";
        this.usedInPlannedProduction = "";
        this.orderPoint = "";
        
        this.consumeByMonth = new String[NUMBER_OF_MONTHS];
    	
        this.itemMode = NavisionTool.LOADER_PRODUCT_SEARCH;
	}

	@Override
    public int describeContents() {
        return 0;
    }
	
    @Override
    public void writeToParcel(Parcel parcel, int arg1) 
    {
    	boolean[] boolArray={inBOM, hasBOM};
    	
        parcel.writeString(reference);
        parcel.writeString(description);
        parcel.writeString(quantity);
        parcel.writeString(stock);
        parcel.writeString(cost);
        parcel.writeString(price);
        parcel.writeBooleanArray(boolArray);

        parcel.writeString(purchase);
        parcel.writeString(inProduction);
        parcel.writeString(inPlannedProduction);
 
        parcel.writeString(sale);
        parcel.writeString(usedInProduction);
        parcel.writeString(transfer);
        parcel.writeString(usedInPlannedProduction);
        parcel.writeString(orderPoint);
        
        parcel.writeStringArray(consumeByMonth);

        parcel.writeInt(itemMode);
    }
     
    public static final Creator<Product> CREATOR = new Creator<Product>()
    {
    	boolean[] boolArray = new boolean[2];
        @Override
        public Product createFromParcel(Parcel parcel) {
            String reference = parcel.readString();
            String description = parcel.readString();
            String quantity = parcel.readString();
            String stock = parcel.readString();
            String cost = parcel.readString();
            String price = parcel.readString();
            parcel.readBooleanArray(boolArray);
   
            String purchase = parcel.readString();
            String inProduction = parcel.readString();
            String inPlannedProduction = parcel.readString();

            String sale = parcel.readString();
            String usedInProduction = parcel.readString();
            String transfer = parcel.readString();
            String usedInPlannedProduction = parcel.readString();
            String orderPoint = parcel.readString();
            
            String[] consumeByMonth = new String[NUMBER_OF_MONTHS];
            parcel.readStringArray(consumeByMonth);
            

            int itemMode = parcel.readInt();           
            
            return new Product(reference, description, quantity, stock, cost, price, boolArray,
            		purchase, inProduction, inPlannedProduction, sale, usedInProduction,
        			transfer, usedInPlannedProduction, orderPoint, consumeByMonth, itemMode);
        }
 
        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
         
    };

}
