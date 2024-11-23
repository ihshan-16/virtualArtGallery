package entity;
import java.util.Date;
import entity.Artist;

public class Artwork {
    private int artworkId;
    private String title;
    private String description;
    private Date creationDate;
    private String medium;
    private String imageUrl;
    

public Artwork(int artworkId, String title, String description, Date creationDate, String medium, String imageUrl) {
        this.artworkId = artworkId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageUrl = imageUrl;
    }

   
    public Artwork(int artworkId2, String title2, String description2, String string, String medium2,
			String imageUrl2) {
		
	}


	public int getArtworkId() { 
    	return artworkId;
    	}
    public void setArtworkId(int artworkId) {
    	this.artworkId = artworkId;
    	}

    public String getTitle() {
    	return title; 
    	}
    public void setTitle(String title) {
    	this.title = title;
    	}

    public String getDescription() {
    	return description; 
    	}
    public void setDescription(String description) {
    	this.description = description;
    	}

    public Date getCreationDate() {
    	return creationDate; 
    	}
    public void setCreationDate(Date creationDate) { 
    	this.creationDate = creationDate; 
    	}

    public String getMedium() {
    	return medium;
    	}
    public void setMedium(String medium) {
    	this.medium = medium; 
    	}

    public String getImageUrl() { 
    	return imageUrl; 
    	}
    public void setImageUrl(String imageUrl) {
    	this.imageUrl = imageUrl; 
    	}
    
    @Override
    public String toString() {
        return "Artwork{" +
                "artworkID=" + artworkId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", medium='" + medium + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                
                '}';
    }


	public Integer getArtworkID() {
		
		return 1;
	}
}
