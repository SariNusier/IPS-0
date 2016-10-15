package database.models;


public class Museum {
    private String name;
    private String description;
    private String address;
    private String phone;
    private String website;
    private String id;
    private Building[] buildings;

    public Museum(String name, String description, String address, String phone, String website,
                  String id, Building[] buildings) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.id = id;
        this.buildings = buildings;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getId() {
        return id;
    }

    public Building[] getBuildings() {
        return buildings;
    }
}
