package pages;

public class PageSeeDetails implements Page {
    private static PageSeeDetails instance = null;

    private PageSeeDetails() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageSeeDetails getInstance() {
        if (instance == null) {
            instance = new PageSeeDetails();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
