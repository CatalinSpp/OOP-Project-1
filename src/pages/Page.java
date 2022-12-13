package pages;

import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;

public interface Page {
    void navigateToHere(final Database database);
}
