package scrapper;

public class Appartment {

    private Double price;
    private String url;
    private String description;
    private String address;
    private Double area;
    private Double noOfRooms;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Double noOfRooms) {
        this.noOfRooms = noOfRooms;
    }
}
