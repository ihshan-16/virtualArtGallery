package dao;

import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.UserNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualArtGalleryImpl implements IVirtualArtGallery {
    private Connection connection;

    public VirtualArtGalleryImpl() {
        this.connection = DBConnUtil.getConnection("db.properties");
    }

    @Override
    public boolean addArtwork(Artwork artwork) {
        String query = "INSERT INTO Artwork (artworkId, title, description, creationDate, medium, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artwork.getArtworkId());
            stmt.setString(2, artwork.getTitle());
            stmt.setString(3, artwork.getDescription());
            stmt.setDate(4, new java.sql.Date(artwork.getCreationDate().getTime()));
            stmt.setString(5, artwork.getMedium());
            stmt.setString(6, artwork.getImageUrl());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateArtwork(Artwork artwork) throws ArtWorkNotFoundException {
        String query = "UPDATE Artwork SET title = ?, description = ?, creationDate = ?, medium = ?, imageUrl = ? WHERE artworkId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setDate(3, new java.sql.Date(artwork.getCreationDate().getTime()));
            stmt.setString(4, artwork.getMedium());
            stmt.setString(5, artwork.getImageUrl());
            stmt.setInt(6, artwork.getArtworkId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new ArtWorkNotFoundException("Artwork with ID " + artwork.getArtworkId() + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeArtwork(int artworkId) throws ArtWorkNotFoundException {
        String query = "DELETE FROM Artwork WHERE artworkId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkId + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Artwork getArtworkById(int artworkId) throws ArtWorkNotFoundException {
        String query = "SELECT * FROM Artwork WHERE artworkId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Artwork(
                    rs.getInt("artworkId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageUrl")
                );
            } else {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Artwork> searchArtworks(String keyword) {
        String query = "SELECT * FROM Artwork WHERE title LIKE ? OR description LIKE ?";
        List<Artwork> artworks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                artworks.add(new Artwork(
                    rs.getInt("artworkId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageUrl")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    @Override
    public boolean addArtworkToFavorite(int userId, int artworkId) throws UserNotFoundException, ArtWorkNotFoundException {
        
        validateUserExists(userId);
        
        getArtworkById(artworkId);

        String query = "INSERT INTO User_Favorite_Artwork (userId, artworkId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, artworkId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeArtworkFromFavorite(int userId, int artworkId) throws UserNotFoundException {
        validateUserExists(userId);

        String query = "DELETE FROM User_Favorite_Artwork WHERE userId = ? AND artworkId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, artworkId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException {
        validateUserExists(userId);

        String query = "SELECT a.* FROM Artwork a INNER JOIN User_Favorite_Artwork uf ON a.artworkId = uf.artworkId WHERE uf.userId = ?";
        List<Artwork> favoriteArtworks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favoriteArtworks.add(new Artwork(
                    rs.getInt("artworkId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageUrl")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteArtworks;
    }

    private void validateUserExists(int userId) throws UserNotFoundException {
        String query = "SELECT * FROM User WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}