package model;

public class Artist extends Entity{
    private String name;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(Long artistID, String name) {
        setID(artistID);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
