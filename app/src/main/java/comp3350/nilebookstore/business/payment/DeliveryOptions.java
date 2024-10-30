package comp3350.nilebookstore.business.payment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.persistence.ItemPersistence;
import comp3350.nilebookstore.persistence.PurchasePersistence;

public class DeliveryOptions implements Parcelable
{
    private ItemPersistence itemPersistence;
    private PurchasePersistence purchasePersistence;
    private boolean isPickUp;
    private String receiverEmail;
    private String address, city, province, postalCode; //correspond to location[0],[1],[2],[3]

    public DeliveryOptions()
    {
        itemPersistence     = Services.getItemPersistence();
        purchasePersistence = Services.getPurchasePersistence();

        this.isPickUp       = false;
        this.receiverEmail  = "";
        this.address        = "";
        this.city           = "";
        this.province       = "";
        this.postalCode     = "";
    }

    public DeliveryOptions(Boolean isPickUp, String receiverEmail, String address, String city, String province, String postalCode)
    {
        itemPersistence     = Services.getItemPersistence();
        purchasePersistence = Services.getPurchasePersistence();

        this.isPickUp       = isPickUp;
        this.receiverEmail  = receiverEmail;
        this.address        = address;
        this.city           = city;
        this.province       = province;
        this.postalCode     = postalCode;
    }

    public boolean getPickUp()          {return isPickUp;}
    public String getReceiverEmail()    {return receiverEmail;}
    public String getAddress()          {return address;}
    public String getCity()             {return city;}
    public String getProvince()         {return province;}
    public String getPostalCode()       {return postalCode;}

    //mutator
    //If choice is true means the order is pick up order
    public void setPickUp(boolean choice)       {isPickUp = choice;}
    public void setReceiverEmail(String email)  {receiverEmail = email;}
    public void setAddress(String address)      {this.address = address;}
    public void setCity(String city)            {this.city = city;}
    public void setProvince(String province)    {this.province = province;}
    public void setPostalCode(String postal)    {this.postalCode = postal;}

    //Estimate the time in how many day(s)
    //return -1 when the Province Name Format Error
    public int estimateDeliveryTime()
    {
        if(province.equals("MB-Pickup")){return 1;}
        if(province.equals("MB")){return 2;}
        if(province.equals("SK")){return 3;}
        if(province.equals("ON")){return 3;}
        if(province.equals("NU")){return 3;}
        if(province.equals("NT")){return 4;}
        if(province.equals("AB")){return 4;}
        if(province.equals("QC")){return 4;}
        if(province.equals("YT")){return 5;}
        if(province.equals("BC")){return 5;}
        if(province.equals("NL")){return 6;}
        if(province.equals("NB")){return 6;}
        if(province.equals("PE")){return 7;}
        if(province.equals("NS")){return 7;}
        return -1;
    }

    public boolean checkValidEmail(String inputEmail)
    {
        //Goal of this function is to test if the email is valid or not
        boolean isValid = false;
        int atIndex  = inputEmail.indexOf("@");
        int dotIndex = inputEmail.indexOf(".");

        //The email address is considered valid if it contains a single "@"  with a "." symbol after the @ symbol, but not as the last character.
        if (atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < inputEmail.length() - 1)
        {
            isValid = true;
        }
        return isValid;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DeliveryOptions {");
        sb.append("\n  isPickUp: " + isPickUp);
        sb.append("\n  receiverEmail: " + receiverEmail);
        sb.append("\n  address: " + address);
        sb.append("\n  city: " + city);
        sb.append("\n  province: " + province);
        sb.append("\n  postalCode: " + postalCode);
        sb.append("\n}");
        return sb.toString();
    }

    public static DeliveryOptions fromString(String str)
    {
        DeliveryOptions options = new DeliveryOptions();

        String[] lines = str.split("\n");

        for (String line : lines)
        {
            if (line.startsWith("  isPickUp: ")) {
                options.setPickUp(Boolean.valueOf(line.substring(12)));
            } else if (line.startsWith("  receiverEmail: ")) {
                options.setReceiverEmail(line.substring(17));
            } else if (line.startsWith("  address: ")) {
                options.setAddress(line.substring(11));
            } else if (line.startsWith("  city: ")) {
                options.setCity(line.substring(8));
            } else if (line.startsWith("  province: ")) {
                options.setProvince(line.substring(12));
            } else if (line.startsWith("  postalCode: ")) {
                options.setPostalCode(line.substring(14));
            }
        }
        return options;
    }

    public String generatePurchaseID()
    {
        String result = "RECPT";

        Random random = new Random();
        int number = random.nextInt(1000);
        result += Integer.toString(number);

        return result;
    }

    //For Bundle
    private DeliveryOptions(Parcel in)
    {
        isPickUp = in.readByte() != 0;
        receiverEmail = in.readString();
        address = in.readString();
        city = in.readString();
        province = in.readString();
        postalCode = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeByte((byte) (isPickUp ? 1 : 0));
        dest.writeString(receiverEmail);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(postalCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<DeliveryOptions> CREATOR = new Parcelable.Creator<DeliveryOptions>() {
        @Override
        public DeliveryOptions createFromParcel(Parcel in) {
            return new DeliveryOptions(in);
        }
        @Override
        public DeliveryOptions[] newArray(int size) {
            return new DeliveryOptions[size];
        }
    };
}
