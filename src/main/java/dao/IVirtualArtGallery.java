package dao;

import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.UserNotFoundException;

import java.util.List;

public interface IVirtualArtGallery {
    boolean addArtwork(Artwork artwork);

    boolean updateArtwork(Artwork artwork) throws ArtWorkNotFoundException;

    boolean removeArtwork(int artworkId) throws ArtWorkNotFoundException;

    Artwork getArtworkById(int artworkId) throws ArtWorkNotFoundException;

    List<Artwork> searchArtworks(String keyword);

    boolean addArtworkToFavorite(int userId, int artworkId) throws UserNotFoundException, ArtWorkNotFoundException;

    boolean removeArtworkFromFavorite(int userId, int artworkId) throws UserNotFoundException;

    List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException;
}
