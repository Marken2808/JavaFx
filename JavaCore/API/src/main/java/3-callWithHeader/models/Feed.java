package models;

public class Feed {

//    private String feedItemUid;

    private String categoryUid;
    private String direction;
    private int minorUnits;

    public Feed(String categoryUid, String direction, int minorUnits) {
        this.categoryUid = categoryUid;
        this.direction = direction;
        this.minorUnits = minorUnits;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "categoryUid='" + categoryUid + '\'' +
                ", direction='" + direction + '\'' +
                ", minorUnits=" + minorUnits +
                '}';
    }
}
