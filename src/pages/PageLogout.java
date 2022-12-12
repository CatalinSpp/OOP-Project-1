package pages;

public class PageLogout implements Page {
    private static PageLogout instance = null;

    private PageLogout() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageLogout getInstance() {
        if (instance == null) {
            instance = new PageLogout();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
