package edu.sharif.snappfoodminus.temp;

public class Request {
    public String requester;
    public String foodName;
    public String foodJSON;
    public boolean deleteMode;

    public Request(String requester, String foodName, String foodJSON, boolean deleteMode) {
        this.requester = requester;
        this.foodName = foodName;
        this.foodJSON = foodJSON;
        this.deleteMode = deleteMode;
    }
}
