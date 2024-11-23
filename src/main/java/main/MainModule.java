package main;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.ArtWorkNotFoundException;
import exception.UserNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        VirtualArtGalleryImpl gallery = new VirtualArtGalleryImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nVirtual Art Gallery");
            System.out.println("1. Add Artwork");
            System.out.println("2. Update Artwork");
            System.out.println("3. Remove Artwork");
            System.out.println("4. View Artwork by ID");
            System.out.println("5. Search Artworks");
            System.out.println("6. Add Artwork to Favorites");
            System.out.println("7. Remove Artwork from Favorites");
            System.out.println("8. View User's Favorite Artworks");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addArtwork(gallery, scanner);
                    break;

                case 2:
                    updateArtwork(gallery, scanner);
                    break;

                case 3:
                    removeArtwork(gallery, scanner);
                    break;

                case 4:
                    viewArtworkById(gallery, scanner);
                    break;

                case 5:
                    searchArtworks(gallery, scanner);
                    break;

                case 6:
                    addArtworkToFavorites(gallery, scanner);
                    break;

                case 7:
                    removeArtworkFromFavorites(gallery, scanner);
                    break;

                case 8:
                    viewUserFavorites(gallery, scanner);
                    break;

                case 9:
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addArtwork(VirtualArtGalleryImpl gallery, Scanner scanner) {
        try {
            System.out.println("Enter Artwork Details:");
            System.out.print("Artwork ID: ");
            int artworkId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Creation Date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine();
            Date creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);

            System.out.print("Medium: ");
            String medium = scanner.nextLine();

            System.out.print("Image URL: ");
            String imageUrl = scanner.nextLine();

            Artwork artwork = new Artwork(artworkId, title, description, creationDate, medium, imageUrl);
            if (gallery.addArtwork(artwork)) {
                System.out.println("Artwork added successfully!");
            } else {
                System.out.println("Failed to add artwork.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private static void updateArtwork(VirtualArtGalleryImpl gallery, Scanner scanner) {
        try {
            System.out.println("Enter Artwork ID to Update:");
            int artworkId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("New Title: ");
            String title = scanner.nextLine();

            System.out.print("New Description: ");
            String description = scanner.nextLine();

            System.out.print("New Creation Date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine();
            Date creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);

            System.out.print("New Medium: ");
            String medium = scanner.nextLine();

            System.out.print("New Image URL: ");
            String imageUrl = scanner.nextLine();

            Artwork artwork = new Artwork(artworkId, title, description, creationDate, medium, imageUrl);
            try {
				if (gallery.updateArtwork(artwork)) {
				    System.out.println("Artwork updated successfully!");
				} else {
				    System.out.println("Artwork not found or update failed.");
				}
			} catch (ArtWorkNotFoundException e) {
				e.printStackTrace();
			}
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private static void removeArtwork(VirtualArtGalleryImpl gallery, Scanner scanner) {
        System.out.print("Enter Artwork ID to Remove: ");
        int artworkId = scanner.nextInt();
        try {
			if (gallery.removeArtwork(artworkId)) {
			    System.out.println("Artwork removed successfully!");
			}
		} catch (ArtWorkNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    private static void viewArtworkById(VirtualArtGalleryImpl gallery, Scanner scanner) {
        System.out.print("Enter Artwork ID to View: ");
        int artworkId = scanner.nextInt();
        Artwork artwork = null;
		try {
			artwork = gallery.getArtworkById(artworkId);
		} catch (ArtWorkNotFoundException e) {
			
			e.printStackTrace();
		}
		System.out.println(artwork);
    }

    private static void searchArtworks(VirtualArtGalleryImpl gallery, Scanner scanner) {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        List<Artwork> artworks = gallery.searchArtworks(keyword);
        if (!artworks.isEmpty()) {
            System.out.println("Search Results:");
            artworks.forEach(System.out::println);
        } else {
            System.out.println("No artworks found matching the keyword.");
        }
    }

    private static void addArtworkToFavorites(VirtualArtGalleryImpl gallery, Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Artwork ID to Add to Favorites: ");
        int artworkId = scanner.nextInt();
        try {
			if (gallery.addArtworkToFavorite(userId, artworkId)) {
			    System.out.println("Artwork added to favorites successfully!");
			}
		} catch (UserNotFoundException e) {
			
			e.printStackTrace();
		} catch (ArtWorkNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    private static void removeArtworkFromFavorites(VirtualArtGalleryImpl gallery, Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Artwork ID to Remove from Favorites: ");
        int artworkId = scanner.nextInt();
        try {
			if (gallery.removeArtworkFromFavorite(userId, artworkId)) {
			    System.out.println("Artwork removed from favorites successfully!");
			}
		} catch (UserNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    private static void viewUserFavorites(VirtualArtGalleryImpl gallery, Scanner scanner)  {
        System.out.print("Enter User ID to View Favorites: ");
        int userId = scanner.nextInt();
        List<Artwork> favoriteArtworks = null;
		try {
			favoriteArtworks = gallery.getUserFavoriteArtworks(userId);
		} catch (UserNotFoundException e) {
			
			e.printStackTrace();
		}
		if (!favoriteArtworks.isEmpty()) {
		    System.out.println("User's Favorite Artworks:");
		    favoriteArtworks.forEach(System.out::println);
		} else {
		    System.out.println("No favorite artworks found for this user.");
		}
    }
}
