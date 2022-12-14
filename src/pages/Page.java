package pages;

import database.Database;

public interface Page {
    /** function that navigate to the present page page*/
    void navigateToHere(Database database);
}
