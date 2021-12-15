package fi.metropolia.practisecalorie.user;

/**
 * creating a singleton of logged user so that a instance of the class can be called to show user
 * is only exposed to their information
 */
public class LoggedUser {
    private static final LoggedUser loggedUser = new LoggedUser();
    private static int userID = 0;

    public static LoggedUser getInstance() {
        return  loggedUser;
    }

    private  LoggedUser(){
    }

    public static int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        LoggedUser.userID = userID;
    }
}
