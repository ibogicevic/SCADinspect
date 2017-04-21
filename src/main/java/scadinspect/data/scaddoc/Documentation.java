package scadinspect.data.scaddoc;

import com.sun.javafx.image.IntPixelGetter;
import javax.swing.text.Document;
import java.util.ArrayList;

/**
 * Created by Großkreutz on 30.03.2017.
 */

//THIS IS A DUMMY CLASS for entering dummy data to the documentation list!!
    //This has to be removed with the actual Object later on
public class Documentation {

    private final String part;
    private final String material;
    private final String url;
    private final double weight;
    private final double price;
    private final int amount;

    public Documentation (String part, String material, String url, double weight, double price, int amount){
        this.part = part;
        this.material = material;
        this.url = url;
        this.weight = weight;
        this.price = price;
        this.amount = amount;
    }


    public String getPart() {
        return part;
    }

    public String getMaterial() {
        return material;
    }

    public String getUrl() {
        return url;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}