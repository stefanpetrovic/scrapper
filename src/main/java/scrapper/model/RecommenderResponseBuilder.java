package scrapper.model;

public class RecommenderResponseBuilder {

    private RecommendationResponse response;

    private RecommenderResponseBuilder() {
        response = new RecommendationResponse();
    }

    public static RecommenderResponseBuilder responseWith() {
        return new RecommenderResponseBuilder();
    }

    public RecommenderResponseBuilder recommended(boolean recommended) {
        response.setRecommended(recommended);
        return this;
    }

    public RecommenderResponseBuilder message(String message) {
        response.setMessage(message);
        return this;
    }

    public RecommendationResponse build() {
        return response;
    }
}
