package scrapper.model;

import java.util.List;

public class CityExpertResponse implements RESTResponse<CityExpertApartment> {

    private List<CityExpertApartment> result;

    public List<CityExpertApartment> getResult() {
        return result;
    }

    public void setResult(List<CityExpertApartment> result) {
        this.result = result;
    }

    @Override
    public List<CityExpertApartment> getContent() {
        return getResult();
    }
}
