package fi.metropolia.practisecalorie.user;

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
        this.userID = userID;
    }
}
