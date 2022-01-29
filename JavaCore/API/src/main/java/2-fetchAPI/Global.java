import org.json.simple.JSONObject;

import java.util.Map;

public class Global {

    private long newConfirmed;
    private long totalConfirmed;
    private long newDeaths;
    private long totalDeaths;
    private long newRecovered;
    private long totalRecovered;
    private String date;

    public Global () {}

    public Global(
            long newConfirmed,
            long totalConfirmed,
            long newDeaths,
            long totalDeaths,
            long newRecovered,
            long totalRecovered,
            String date
    ) {
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
        this.date = date;
    }

    public Global fromJson(JSONObject obj) {
        this.newConfirmed = (long) obj.get("NewConfirmed");
        this.totalConfirmed = (long) obj.get("TotalConfirmed");
        this.newDeaths = (long) obj.get("NewDeaths");
        this.totalDeaths = (long) obj.get("TotalDeaths");
        this.newRecovered = (long) obj.get("NewRecovered");
        this.totalRecovered = (long) obj.get("TotalRecovered");
        this.date = (String) obj.get("Date");

        return new Global(newConfirmed, totalConfirmed, newDeaths, totalDeaths, newRecovered, totalRecovered, date);
    }

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Data{" +
                "newConfirmed=" + newConfirmed +
                ", totalConfirmed=" + totalConfirmed +
                ", newDeaths=" + newDeaths +
                ", totalDeaths=" + totalDeaths +
                ", newRecovered=" + newRecovered +
                ", totalRecovered=" + totalRecovered +
                ", date='" + date + '\'' +
                '}';
    }
}
