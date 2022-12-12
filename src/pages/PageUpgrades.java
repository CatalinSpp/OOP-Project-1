package pages;

public class PageUpgrades implements Page {
    private static PageUpgrades instance = null;

    private PageUpgrades() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageUpgrades getInstance() {
        if (instance == null) {
            instance = new PageUpgrades();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
