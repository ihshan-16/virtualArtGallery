package entity;


public class Artist {
    private int artistId;
    private String name;
    private String biography;
    private String birthDate;
    private String nationality;
    private String website;
    private String contactInfo;

   
    public Artist(int artistId, String name, String biography, String birthDate, String nationality, String website, String contactInfo) {
        this.artistId = artistId;
        this.name = name;
        this.biography = biography;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.website = website;
        this.contactInfo = contactInfo;
    }

   
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}

