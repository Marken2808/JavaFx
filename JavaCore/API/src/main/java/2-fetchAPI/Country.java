import org.json.simple.JSONObject;

public class Country {
    private String country;
    private String code;
    private String slug;
    private Global data;

    public Country() {}

    public Country(String country, String code, String slug, Global data) {
        this.country = country;
        this.code = code;
        this.slug = slug;
        this.data = data;
    }

    public Country fromJson(JSONObject obj) {
        this.country = (String) obj.get("Country");
        this.code = (String) obj.get("CountryCode");
        this.slug = (String) obj.get("Slug");
        this.data = new Global().fromJson(obj);
        return new Country(country, code, slug, data);
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", code='" + code + '\'' +
                ", slug='" + slug + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public String getSlug() {
        return slug;
    }
}


