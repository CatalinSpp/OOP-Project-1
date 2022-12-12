package pages;

public class PageLogin implements Page {
    private static PageLogin instance = null;

    private PageLogin() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageLogin getInstance() {
        if (instance == null) {
            instance = new PageLogin();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
