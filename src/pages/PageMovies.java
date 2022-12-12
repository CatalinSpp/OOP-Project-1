package pages;

public class PageMovies implements Page {
    private static PageMovies instance = null;

    private PageMovies() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageMovies getInstance() {
        if (instance == null) {
            instance = new PageMovies();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
