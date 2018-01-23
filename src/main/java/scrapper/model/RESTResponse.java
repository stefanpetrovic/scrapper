package scrapper.model;

import java.util.List;

public interface RESTResponse<T> {

    List<T> getContent();

}
