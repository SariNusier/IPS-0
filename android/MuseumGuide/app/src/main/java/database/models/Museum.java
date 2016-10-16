package database.models;

import java.io.Serializable;

public class Museum implements Serializable{

    private String id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String website;
    private Building[] buildings;

    /***
     * Default Constructor for Museum Class
     * @param id (Pref. GUID from DB)
     * @param name (The Name of the Museum E.g. Louvre Museum)
     * @param description (The Description of the Museum)
     * @param address (The Address of the Museum)
     * @param phone (The Phone of the Museum)
     * @param website (The Website of the Museum)
     * @param buildings (Array of Buildings in that Museum. In DB is the FK from Building to Museum)
     */
    public Museum(String id, String name, String description, String address, String phone,
                  String website, Building[] buildings) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.id = id;
        this.buildings = buildings;
    }

    /**
     * Default Getter for ID
     * @return Id of type String (I personally would like GUID)
     */
    public String getId() {
        return id;
    }

    /**
     * Default Getter for Name
     * @return Name of type String
     */
    public String getName() {
        return name;
    }

    /**
     * Default Getter for Description
     * @return Description of type String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Default Getter for Address
     * @return Address of type String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Default Getter for Phone
     * @return Phone of type String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Default Getter for Website
     * @return Website of type String
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Default Getter for Buildings
     * @return Array of Buildings
     */
    public Building[] getBuildings() {
        return buildings;
    }

}
