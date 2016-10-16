package database.models;

import java.io.Serializable;

public class Exhibit implements Serializable{

    private String id;
    private String name;
    private String description;

    /**
     * Default Constructor for Exhibit Class
     * @param id (Pref. GUID from DB)
     * @param name (The Name of the Exhibit E.g. ColumnRoma128, Spitfire22, WWII.WC.13)
     * @param description (The Description of the Exhibit)
     */
    public Exhibit(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

}
