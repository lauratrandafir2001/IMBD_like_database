package myclasses;
import java.util.ArrayList;
import fileio.*;

public class Database {
    private ArrayList<Actor> actorsList;
    private ArrayList<User> usersList;
    private ArrayList<Movies> moviesList;
    private ArrayList<Shows> showsList;
    private ArrayList<Action> commandsData;

    public Database(final Input  input1) {
      this.actorsList = new ArrayList<>();
       for (ActorInputData inputActor: input1.getActors()) {
           Actor actor = new Actor(inputActor);
           this.actorsList.add(actor);
        }

       this.usersList = new ArrayList<>();
       for (UserInputData inputUser : input1.getUsers()) {
           User user = new User(inputUser);
           this.usersList.add(user);
       }
       this.moviesList = new ArrayList<>();
       for (MovieInputData inputMovie : input1.getMovies()) {
           Movies movie = new Movies(inputMovie);
           this.moviesList.add(movie);
       }
        this.showsList = new ArrayList<>();
        for (SerialInputData inputSerial : input1.getSerials()) {
            Shows show = new Shows(inputSerial);
            this.showsList.add(show);
        }
        for (Actor actor:this.actorsList) {
            actor.parseTemp(this);
        }
        for (Video video:this.moviesList) {
            video.parseTempCast(this);
        }
        for (Video video:this.showsList) {
            video.parseTempCast(this);
        }
        for (User user : this.usersList) {
            user.parseTemp(this);
        }
        this.commandsData = new ArrayList<Action>();
        for (ActionInputData inputCommand : input1.getCommands()) {
            Action action = new Action(inputCommand);
            this.commandsData.add(action);
        }
    }
    /**
     * gets a specific movie from database
     */
    public Movies getMovie(final String movieName) {
        for (Movies movie : this.moviesList) {
            if (movie.getTitle().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }
    /**
     * gets a specific show from database
     */

    public Shows getShows(final String showName) {
        for (Shows show : this.showsList) {
            if (show.getTitle().equalsIgnoreCase(showName)) {
                return show;
            }
        }
        return null;
    }

    /**
     * gets a specific video from database
     */

    public Video getVideo(final String videoName) {
        Video video;
        video = this.getMovie(videoName);
        if (video != null) {
            return video;
        }
        return this.getShows(videoName);
    }

    /**
     * gets a specific actor from database
     */

    public Actor getActor(final String actorName) {
        for (Actor actor : this.actorsList) {
            if (actor.getName().equals(actorName)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * returns the command
     */


    public ArrayList<Action> getCommandsData() {
       return this.commandsData;

    }
    /**
     * returns the users list
     */

    public ArrayList<User> getUsersList() { return this.usersList; }

    /**
     * returns the actors list
     */

    public ArrayList<Actor> getActorsList() {
        return actorsList;
    }

    /**
     * returns the videolist
     */

    public ArrayList<Video> getVideoList() {

        ArrayList<Video> videoList = new ArrayList<>();
        for (Movies movie : moviesList) {
            videoList.add(movie);
        }
        for (Shows show : showsList) {
            videoList.add(show);
        }

        return videoList;
    }
    /**
     * returns the movies list
     */

    public ArrayList<Movies> getMoviesList() {
        return moviesList;
    }
    /**
     * returns the shows list
     */

    public ArrayList<Shows> getShowsList() {
        return showsList;
    }
}
