package models;

import org.json.simple.JSONObject;

public class Feed {

//    private String feedItemUid;

    private String categoryUid;
    private String direction;
    private long minorUnits;

    public Feed() {}

    public Feed(String categoryUid, String direction, long minorUnits) {
        this.categoryUid = categoryUid;
        this.direction = direction;
        this.minorUnits = minorUnits;
    }

    public Feed fromJson(JSONObject obj) {
        this.categoryUid = (String) obj.get("categoryUid");
        this.direction = (String) obj.get("direction");
        this.minorUnits = (long) (((JSONObject)obj.get("amount")).get("minorUnits"));

        return new Feed(categoryUid, direction, minorUnits);
    }

    @Override
    public String toString() {
        return "Feed{" +
                "categoryUid='" + categoryUid + '\'' +
                ", direction='" + direction + '\'' +
                ", minorUnits=" + minorUnits +
                '}';
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public String getDirection() {
        return direction;
    }

    public long getMinorUnits() {
        return minorUnits;
    }
}
