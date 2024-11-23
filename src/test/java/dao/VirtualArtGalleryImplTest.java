package dao;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.UserNotFoundException;
import org.junit.jupiter.api.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VirtualArtGalleryImplTest {

    private VirtualArtGalleryImpl gallery;

 
      @BeforeAll 
      void setup() {
            gallery = new VirtualArtGalleryImpl();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = dateFormat.parse("1503-01-01");
                Date date2 = dateFormat.parse("1889-06-01");
                Date date3 = dateFormat.parse("1931-01-01");

               
                gallery.addArtwork(new Artwork(11, "Mona Lisa", "A famous portrait", date1, "Oil", "monalisa.jpg"));
                gallery.addArtwork(new Artwork(12, "Starry Night", "A famous painting by Van Gogh", date2, "Oil", "starrynight.jpg"));
                gallery.addArtwork(new Artwork(13, "The Persistence of Memory", "A surreal painting by Salvador Dalí", date3, "Oil", "persistence.jpg"));
                gallery.addArtworkToFavorite(2, 13); 
                gallery.addArtworkToFavorite(2,12 ); 

            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to set up test data: " + e.getMessage());
            }
        }

      

   
    @Test
    void testGetArtworkById_Invalid() {
        int invalidArtworkID = 23; 
        assertThrows(ArtWorkNotFoundException.class, () -> {
            gallery.getArtworkById(invalidArtworkID);
        }, "ArtworkNotFoundException should be thrown for an invalid artwork ID.");
    }

    @Test
    void testRemoveArtwork() {
        int artworkID = 11; 
        boolean isRemoved = false;
        try {
            isRemoved = gallery.removeArtwork(artworkID);
        } catch (ArtWorkNotFoundException e) {
            fail("Artwork should exist for removal.");
        }
        assertTrue(isRemoved, "Artwork should be removed successfully.");
    }

    @Test
    void testSearchArtworks() {
        String keyword = "Mona";
        List<Artwork> artworks = gallery.searchArtworks(keyword);
        assertFalse(artworks.isEmpty(), "Search results should not be empty.");
        assertTrue(artworks.stream().anyMatch(a -> a.getTitle().contains(keyword)), "Results should contain the keyword.");
    }

    @Test
    void testAddArtworkToFavorite() {
        int userId = 1;
        int artworkId = 1; 
        boolean isAdded = false;
        try {
            isAdded = gallery.addArtworkToFavorite(userId, artworkId);
        } catch (UserNotFoundException | ArtWorkNotFoundException e) {
            fail("User and artwork should exist.");
        }
        assertTrue(isAdded, "Artwork should be added to user's favorites successfully.");
    }

    @Test
    void testRemoveArtworkFromFavorite() {
        int userId = 1; 
        int artworkId = 1; 
        boolean isRemoved = false;
        try {
            isRemoved = gallery.removeArtworkFromFavorite(userId, artworkId);
        } catch (UserNotFoundException e) {
            fail("User should exist.");
        }
        assertTrue(isRemoved, "Artwork should be removed from user's favorites successfully.");
    }

    @Test
    void testGetUserFavoriteArtworks_Valid() throws UserNotFoundException {
        int validUserID = 1;
        List<Artwork> favorites = gallery.getUserFavoriteArtworks(validUserID);
        assertNotNull(favorites, "Favorites list should not be null.");
        assertFalse(favorites.isEmpty(), "Favorites list should not be empty.");
    }

    @Test
    void testGetUserFavoriteArtworks_Invalid() {
        int invalidUserID = 46;
        assertThrows(UserNotFoundException.class, () -> {
            gallery.getUserFavoriteArtworks(invalidUserID);
        }, "UserNotFoundException should be thrown for an invalid user ID.");
    }

    @Test
    void testArtworkNotFoundException() {
        int invalidArtworkID = 50; 
        assertThrows(ArtWorkNotFoundException.class, () -> {
            gallery.getArtworkById(invalidArtworkID);
        }, "ArtworkNotFoundException should be thrown for an invalid artwork ID.");
    }

    @Test
    void testUserNotFoundException() {
        int invalidUserID = 53; 
        assertThrows(UserNotFoundException.class, () -> {
            gallery.getUserFavoriteArtworks(invalidUserID);
        }, "UserNotFoundException should be thrown for an invalid user ID.");
        
    }
}

