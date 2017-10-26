package scrapper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config")
public class RecommenderConfig {

    @Id
    private String id;

    private int minPrice;
    private int maxPrice;
    private int minArea;
    private int maxArea;
    private int maxPriceOfSquareMeter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinArea() {
        return minArea;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public int getMaxPriceOfSquareMeter() {
        return maxPriceOfSquareMeter;
    }

    public void setMaxPriceOfSquareMeter(int maxPriceOfSquareMeter) {
        this.maxPriceOfSquareMeter = maxPriceOfSquareMeter;
    }
}
