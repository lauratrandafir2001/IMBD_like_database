package myclasses;
import fileio.ActionInputData;
import java.util.List;
public class Action {

    private  int actionId;
    /**
     * Type of action
     */
    private  String actionType;
    /**
     * Used for commands
     */
    private  String type;
    /**
     * Username of user
     */
    private  String username;
    /**
     * The type of object on which the actions will be performed
     */
    private String objectType;
    /**
     * Sorting type: ascending or descending
     */
    private  String sortType;
    /**
     * The criterion according to which the sorting will be performed
     */
    private  String criteria;
    /**
     * Video title
     */
    private  String title;
    /**
     * Video genre
     */
    private  String genre;
    /**
     * Query limit
     */
    private  int number;
    /**
     * Grade for rating - aka value of the rating
     */
    private  double grade;
    /**
     * Season number
     */
    private  int seasonNumber;
    /**
     * Filters used for selecting videos
     */
    private  List<List<String>> filters;

    public Action(final ActionInputData action) {
        this.actionId = action.getActionId();
        this.actionType = action.getActionType();
        this.type = action.getType();
        this.username = action.getUsername();
        this.objectType = action.getObjectType();
        this.sortType = action.getSortType();
        this.criteria = action.getCriteria();
        this.title = action.getTitle();
        this.genre = action.getGenre();
        this.number = action.getNumber();
        this.grade = action.getGrade();
        this.seasonNumber = action.getSeasonNumber();
        this.filters = action.getFilters();
    }

    /**
     * return username
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * return action Id
     */
    public int getActionId() {
        return actionId;
    }
    /**
     * returns actionType
     */
    public String getActionType() {
        return actionType;
    }
    /**
     * returns actions's type
     */
    public String getType() {
        return type;
    }
    /**
     * returns object type
     */
    public String getObjectType() {
        return objectType;
    }
    /**
     * return sort type
     */
    public String getSortType() {
        return sortType;
    }
    /**
     * return criteria
     */
    public String getCriteria() {
        return criteria;
    }
    /**
     * returns action's title
     */
    public String getTitle() { return title; }
    /**
     * returns genre
     */
    public String getGenre() {
        return genre;
    }
    /**
     * returns the number of elements we have to return
     */
    public int getNumber() {
        return number;
    }
    /**
     * returns grade
     */
    public double getGrade() {
        return grade;
    }
    /**
     * returns Season Number
     */
    public int getSeasonNumber() {
        return seasonNumber;
    }
    /**
     * returns the filters we need to sort based on
     */
    public List<List<String>> getFilters() {
        return filters;
    }
}
