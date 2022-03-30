package myclasses;
import actor.ActorsAwards;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.util.Collections;
import static java.lang.Double.compare;

public  class Queries  {

    //public static final int awards = 3;




    /**
     * returns the average rating of an actor
     */
    public static String getAverage(final Database db, final Action action, final Integer n) {
        ArrayList<Actor> list = new ArrayList<>();
        ArrayList<Actor> newList = new ArrayList<>();
        list = db.getActorsList();
        for (Actor actor:list) {       //verify if the rating is 0
            if (actor.getRating(db) != 0 && !Double.isNaN(actor.getRating(db))) {
            newList.add(actor);
            }

        }
        ArrayList<String> actorsName = new ArrayList<>();
        newList.sort((o1, o2) -> {  //ccompares them bu rating then by name
            if (Double.compare(o1.getRating(db), o2.getRating(db)) == 0) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return Double.compare(o1.getRating(db), o2.getRating(db));
            }
        });
        if (action.getSortType().equals("desc")) {
            Collections.reverse(newList);
        }
        int i = 0;
        for (Actor actor : newList) {
            if (i < n) {
                actorsName.add(actor.getName());
                i++;
            }
        }

        return "Query result: " + actorsName.toString();
    }

    /**
     * takes an enum from actor_Awards and transforms it into enum type
     */

    public static ActorsAwards stringToEnum(final String award) {
        ActorsAwards searchedAward;
        switch (award) {
            case "BEST_PERFORMANCE" :
                searchedAward = ActorsAwards.BEST_PERFORMANCE;

                break;
            case "BEST_DIRECTOR":
                searchedAward = ActorsAwards.BEST_DIRECTOR;
                break;
            case "PEOPLE_CHOICE_AWARD":
                searchedAward = ActorsAwards.PEOPLE_CHOICE_AWARD;
                break;

            case "BEST_SUPPORTING_ACTOR":
                searchedAward = ActorsAwards.BEST_SUPPORTING_ACTOR;
                break;

            case "BEST_SCREENPLAY" :
                searchedAward = ActorsAwards.BEST_SCREENPLAY;
                break;
            default:
                searchedAward = null;
        }
        return searchedAward;

    }

    /**
     * sorts the actors by the number of awards won
     */


    public static String getAwards(final Database db, final Action action) {
        ArrayList<Actor> list = db.getActorsList();
        ArrayList<Actor> resultList = new ArrayList<>();
        List<String> awardsList = action.getFilters().get(3);
        for (Actor actor : list) {
            int k = 0;
            for (int i = 0; i < awardsList.size(); i++) {
                ActorsAwards searchedaward = stringToEnum(awardsList.get(i));
                if (actor.getAwards().containsKey(searchedaward)) {
                        k++;
                }
                if (i == awardsList.size() - 1) {
                    if (k == awardsList.size()) {
                        resultList.add(actor);
                    }
                }
            }
        }
        if (action.getSortType().equals("asc")) {
            resultList.sort((o1, o2) -> {
                if ((o1.getAwardsNumber()) == (o2.getAwardsNumber())) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return compare(o1.getAwardsNumber(), o2.getAwardsNumber());
                }
            });
        }
        if (action.getSortType().equals("desc")) {
            resultList.sort((o2, o1) -> {
                if ((o1.getAwardsNumber()) == (o2.getAwardsNumber())) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return compare(o1.getAwardsNumber(), o2.getAwardsNumber());
                }
            });
        }
        ArrayList<String> actorName = new ArrayList<>();
        for (Actor actor : resultList) {
            actorName.add(actor.getName());
        }
        return "Query result: " + actorName.toString();
    }

    /**
     * method to compare movies by rating and then by name
     */

    static class SortMoviesByRating implements Comparator<Movies> {
        public int compare(final Movies movie1, final  Movies movie2) {
            if (Double.compare(movie1.getRating(), movie2.getRating()) == 0) {
                return movie1.getTitle().compareTo(movie2.getTitle());
            } else {
                return Double.compare(movie1.getRating(), movie2.getRating());
            }

        }
    }

    /**
     * sorts the movies by rating
     */
    public static String moviesSortByRating(final Database db, final Action action,
                                            final Integer n) {
        ArrayList<Movies> list = filteredVideos(db, action);
        if (list == null) {
            return "Query result: " + list;
        }

        Collections.sort(list, new SortMoviesByRating());
        ArrayList<String> moviesTitles = new ArrayList<>();
        int k = 0;
        for (Movies movie : list) {
            if (movie.getRating() == 0) {
                k++;
            }
        }
        if (k < n) {
            return "Query result: []";
        }
        for (Movies movies : list) {
            if (movies.getRating() != 0) {
                moviesTitles.add(movies.getTitle());
            }
        }
        return "Query result: " + moviesTitles;

    }
    /**
     * method to sort the shows by rating
     */

    static class SortShowsByRating implements Comparator<Shows> {
        public int compare(final Shows show1, final Shows show2) {

            if (Double.compare(show1.getRating(), show2.getRating()) == 0) {
                return show1.getTitle().compareTo(show2.getTitle());
            } else {
                return Double.compare(show1.getRating(), show2.getRating());
            }

        }
    }
    /**
     * returns the shows sorted by rating and then by name
     */

    public static String showsSortByRating(final Database db, final Action action,
                                           final Integer n) {
        ArrayList<Shows> list = filteredShows(db, action);
        if (list == null) {
            return "Query result: " + list;
        }
        Collections.sort(list, new SortShowsByRating());
        ArrayList<String> showsTitle = new ArrayList<>();
        int k = 0;
        for (Shows show : list) {
            if (show.getRating() == 0) {
                k++;
            }
        }
        if (k < n) {
            return "Query result: []";
        }
        for (Shows show : list) {
            if (show.getRating() != 0) {
                showsTitle.add(show.getTitle());
            }
        }
        return "Query result: " + showsTitle;
    }

    /**
     * method that helps us get the movies with the specified filters
     */
    static ArrayList<Movies> filteredVideos(final Database db, final Action action) {
        ArrayList<Movies> moviesWithFilters = new ArrayList<>();
        ArrayList<Movies> moviesResults = new ArrayList<>();
        ArrayList<Movies> list = db.getMoviesList();
        List<String> launchYears = action.getFilters().get(0);
        List<String> genres = action.getFilters().get(1);
        int ok = 0; //to determine if tha action has this filter
        int k = 0;  //to determine if tha action has this filter
        for (Movies movie : list) {
            if (genres != null) { //if the filter is not null then
                for (String genre : genres) {   //we see if the movie has it
                    if (movie.getGenre().contains(genre)) {
                        moviesWithFilters.add(movie);
                        ok = 1;     //it means the movie we look for has to
                                    // have this filter
                    }
                }
            }
            if (launchYears != null) {
                for (String year : launchYears) {
                    if (year != null) {
                        if (movie.getPremiere() == Integer.valueOf(year)) {
                            moviesWithFilters.add(movie);
                            k = 1;
                        }
                    }
                }
            }
        }
        if (genres.get(0) == null && launchYears.get(0) == null) {
            moviesResults = db.getMoviesList();
        }
        if (moviesWithFilters == null) {
            moviesResults = null;
        }
        for (Movies movie : moviesWithFilters) { //to determine if
                                             // the movie has both filters
            if ((Collections.frequency(moviesWithFilters, movie)) == (ok + k)) {
                moviesResults.add(movie);
            }
        }

        return moviesResults;
    }
    /**
     * method to compare the movies by duration and then alphabetically
     */
    static class SortByDuration implements Comparator<Movies> {
        public int compare(final Movies o1, final Movies o2) {
            if (o1.getDuration() == o2.getDuration()) {
                return o1.getTitle().compareTo(o2.getTitle());
            } else {
                return Integer.compare(o1.getDuration(), o2.getDuration());
            }
        }
    }


    /**
     * returns the movies sort by duration
     */

    public static String moviesSortByDuration(final Database db, final Action action,
                                              final Integer n) {
        ArrayList<Movies> list = filteredVideos(db, action);
        if (list == null) {
            return "Query result: []";
        }
        ArrayList<String> moviesTitles = new ArrayList<>();
        ArrayList<Movies> resultList = new ArrayList<>();
        for (Movies movie : list) {
            if (!resultList.contains(movie)) {
                resultList.add(movie);
            }
        }

        Collections.sort(resultList, new SortByDuration());
        if (action.getSortType() == "desc") {
            Collections.reverse(resultList);
        }

        if (resultList.size() == 0) {
            return "Query result: []";
        }
        if (n > resultList.size()) {
            for (int i = 0; i < resultList.size(); i++) {

                moviesTitles.add(resultList.get(i).getTitle());
            }
            return "Query result: " + moviesTitles;
        }

        for (int i = 0; i < n; i++) {
            moviesTitles.add(resultList.get(i).getTitle());
        }
        return "Query result " + moviesTitles;
    }

    /**
     * method to sort the shows by duration
     */
    static class SortShowsByDuration implements Comparator<Shows> {
    public int compare(final Shows show1, final Shows show2) {

        if (Integer.compare(show1.getDuration(), show2.getDuration()) == 0) {
            return show1.getTitle().compareTo(show2.getTitle());
        } else {
            return Integer.compare(show1.getDuration(), show2.getDuration());
        }

    }
}
    /**
     * method to return the shows with the given filters
     */
    static ArrayList<Shows> filteredShows(final Database db, final Action action) {
        ArrayList<Shows> showsWithFilters = new ArrayList<>();
        ArrayList<Shows> showsResults = new ArrayList<>();
        ArrayList<Shows> list = db.getShowsList();
        List<String> launchYears = action.getFilters().get(0);
        List<String> genres = action.getFilters().get(1);
        int ok = 0;
        int k = 0;
        for (Shows show : list) {
            if (genres != null) {
                for (String genre : genres) {
                    if (show.getGenre().contains(genre)) {
                        showsWithFilters.add(show);
                        ok = 1;
                    }
                }
            }
            if (launchYears != null) {
                for (String year : launchYears) {
                    if (year != null) {
                        if (show.getPremiere() == Integer.valueOf(year)) {
                            showsWithFilters.add(show);
                            k = 1;
                        }
                    }
                }
            }
        }
        for (Shows show : showsWithFilters) {
            if ((Collections.frequency(showsWithFilters, show))  == (ok + k)) {
                showsResults.add(show);
            }
        }
        if (genres.get(0) == null && launchYears.get(0) == null) {
            showsResults = db.getShowsList();
        }
        if (showsResults == null) {
            showsResults = db.getShowsList();
        }
        return showsResults;
    }

    /**
     * method that returns Shows sorted by their duration
     */

    public static String showsSortByDuration(final Database db, final Action action,
                                             final Integer n) {
        ArrayList<Shows> list = filteredShows(db, action);
        ArrayList<Shows> resultList = new ArrayList<>();
        ArrayList<String> showsTitles = new ArrayList<>();
            for (Shows show : list) {
                if (!resultList.contains(show)) {
                    resultList.add(show);
                }
            }

        Collections.sort(resultList, new SortShowsByDuration());
        if (resultList.size() == 0) {
            return "Query result: []";
        }
        if (n > resultList.size()) {
            for (int i = 0; i < resultList.size(); i++) {

                showsTitles.add(resultList.get(i).getTitle());
            }
            return "Query result: " + showsTitles;
        }

        for (int i = 0; i < n; i++) {
            showsTitles.add(resultList.get(i).getTitle());
        }
        return "Query result " + showsTitles;
    }



    /**
     * returns the movies sorted by the appereance of them in users favorites list
     */

    public static String SortMoviesByFavorites(final Database db, final Action action,
                                               final Integer n) {
        ArrayList<Movies> list = filteredVideos(db, action);
        ArrayList<String> videosTitles = new ArrayList<>();
        ArrayList<Movies> newResult = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!newResult.contains(list.get(i))) {
                newResult.add(list.get(i));
            }
        }
        Collections.sort(newResult, new SortVideosByFav());
        Collections.reverse(newResult);
        if (newResult.size() == 0) {
            return "Query result: []";
        }
        if (n > newResult.size()) {
            for (int i = 0; i < newResult.size(); i++) {
                videosTitles.add(newResult.get(i).getTitle());
            }
            return "Query result: " + videosTitles;
        }
        for (int i = 0; i < n; i++) {
            videosTitles.add(newResult.get(i).getTitle());
        }
        return "Query result " + videosTitles;
    }

    /**
     * helps us sort the videos by the appearance is users favorites list
     *
     */

    static class SortVideosByFav implements Comparator<Video> {
        public int compare(final Video video1, final Video video2) {
                if (video1.getFavListShows() == video2.getFavListShows()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return video1.getFavListShows() - video2.getFavListShows();
                }
        }
    }

    /**
     * helps us sort the shows by the appereance is users favorites list
     *
     */

    public static String sortShowsByFavorites(final Database db, final Action action,
                                              final Integer n) {
        ArrayList<Shows> list = filteredShows(db, action);
        ArrayList<Shows> resultList = new ArrayList<>();
        ArrayList<String> videosTitles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!(resultList.contains(list.get(i)))) {
                resultList.add(list.get(i));
            }
        }
        for (int i = 0; i < (resultList.size() - 1); i++) {
           if (resultList.get(i).getFavListShows() == 0
                   && resultList.get(i + 1).getFavListShows() != 0) {
               resultList.remove(resultList.get(i));
           }
        }
        Collections.sort(resultList, new SortVideosByFav());
        Collections.reverse(resultList);
        if (resultList.size() == 0) {
            return "Query result: []";
        }
        if (n > resultList.size()) {
            for (int i = 0; i < resultList.size(); i++) {

                videosTitles.add(resultList.get(i).getTitle());
            }
            return "Query result: " + videosTitles;
        }
        for (int i = 0; i < n; i++) {
            videosTitles.add(resultList.get(i).getTitle());
        }
        return "Query result " + videosTitles;
    }

    /**
     * method that helps us sort the movies based on views number
     *
     */

    static class SortMoviesByViews implements Comparator<Movies> {
        public int compare(final Movies movie1, final Movies movie2) {

            if (Integer.compare(movie1.getViewsNo(), movie2.getViewsNo()) == 0) {
                return movie1.getTitle().compareTo(movie2.getTitle());
            } else {
                return Integer.compare(movie1.getViewsNo(), movie2.getViewsNo());
            }

        }
    }

    /**
     * returns the movies sorted by views number
     *
     */

    public static String sortMoviesByViews(final Database db, final Action action,
                                           final Integer n) {

        ArrayList<Movies> list = filteredVideos(db, action);
        ArrayList<User> users = db.getUsersList();
        ArrayList<Movies> newList = new ArrayList<>();
        ArrayList<String> videosTitles = new ArrayList<>();
        for (User user : users) {
            for (Movies movie : list) {
                if (user.getHistory() != null) {
                    if (user.getHistory().containsKey(movie)) {
                        movie.viewsNo = user.getHistory().get(movie) + movie.getViewsNo();
                    }
                }
            }
        }
        for (Movies movie : list) {
            if (!newList.contains(movie)) {
                newList.add(movie);
            }
        }

        for (int i = 0; i < newList.size() - 1; i++) {
            if (newList.get(i).getViewsNo() != 0 && newList.get(i + 1).getViewsNo() == 0) {
                newList.remove(newList.get(i + 1));
            }
        }
        Collections.sort(newList, new SortMoviesByViews());
        Collections.reverse(newList);
        for (Movies movie: newList) {
            videosTitles.add(movie.getTitle());
        }

        return "Query result: " + videosTitles.toString();
    }

    /**
     * method that helps us sort the shows based on views number
     *
     */

    static class SortShowsByViews implements Comparator<Shows> {
        public int compare(final Shows show1, final Shows show2) {
            if (show1.getViewsNo() == show2.getViewsNo()) {
                return show1.getTitle().compareTo(show2.getTitle());
            } else {
                return Integer.compare(show1.getViewsNo(), show2.getViewsNo());
            }
        }
    }

    /**
     * method that returns the shows sorted by views
     *
     */

    public static String sortShowsByViews(final Database db, final Action action,
                                          final Integer n) {

        ArrayList<Shows> list = filteredShows(db, action);
        ArrayList<Shows> newList = new ArrayList<>();
        ArrayList<User> users = db.getUsersList();
        ArrayList<String> videosTitles = new ArrayList<>();
        for (User user : users) {
            for (Shows show : list) {
                if (user.getHistory() != null) {
                    if (user.getHistory().containsKey(show)) {
                        show.viewsNo = user.getHistory().get(show) +  show.getViewsNo();
                    }
                }
            }
        }
        for (Shows show : list) {
            if (!newList.contains(show)) {
                newList.add(show);
            }
        }
        if (newList.size() == 1) {
            if (newList.get(0).getViewsNo() == 0) {
                return "Query result: []";
            }
        }
        for (int i = 0; i < newList.size() - 1; i++) {
            if (newList.get(i).getViewsNo() != 0 && newList.get(i + 1).getViewsNo() == 0) {
                newList.remove(newList.get(i + 1));
            }
        }
        Collections.sort(newList, new SortShowsByViews());
        Collections.reverse(newList);

        for (Shows show: newList) {
            videosTitles.add(show.getTitle());
        }

        return "Query result: " + videosTitles.toString();
    }

    /**
     *  returns the most active users
     *
     */


    public static String sortUsers(final Database db, final Action action,
                                   Integer n) {


        ArrayList<User> users = db.getUsersList();
        ArrayList<String> usersName = new ArrayList<>();
        users.sort((o1, o2) -> {
            if (compare((o1.getRatingMoviesHistory().size()
                            + o1.getRatingSeasonsHistory().size()),
                            (o2.getRatingMoviesHistory().size()
                                    + o2.getRatingSeasonsHistory().size())) == 0) {
                return o1.getUserName().compareTo(o2.getUserName());
            } else {
                return compare((o1.getRatingMoviesHistory().size()
                                + o1.getRatingSeasonsHistory().size()),
                                (o2.getRatingMoviesHistory().size()
                                        + o2.getRatingSeasonsHistory().size()));
            }
            });
        if (users.size() == 0) {
            return "Query result: []";
        }
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getRatingSeasonsHistory().size()
                    + users.get(i).getRatingMoviesHistory().size()) != 0) {
                    usersName.add(users.get(i).getUserName());
            }
        }
        if (n > usersName.size()) {
            n = usersName.size();
        }
        if (action.getSortType().equals("desc")) {
            Collections.reverse(usersName);
            return "Query result: " + usersName.subList(0, n).toString();
        }
        return "Query result: " + usersName.subList(0, n).toString();


    }
    /**
     * sorts users based on the rating that gave(active users)
     *
     */

    static class SortByNUmberOfRatings implements Comparator<User> {
        public int compare(final User user1, final User user2) {
            return (int) ((user1.getRatingMoviesHistory().size()
                    + user1.getRatingSeasonsHistory().size())
                    - (user2.getRatingMoviesHistory().size()
                    + user2.getRatingSeasonsHistory().size()));
        }
    }

    /**
     * returns the actors that have the given words in their description
     *
     */
    public static String FilterDescription(final Database db, final Action action) {
        ArrayList<Actor> list = db.getActorsList();
        List<String> words = action.getFilters().get(2);
        List<Actor> new_list = new ArrayList<>();
        List<Actor> resultList = new ArrayList<>(); //the list with actors who have all the words
        if (list == null) {
            return null;
        }
        for (Actor actor : list) {
            if (words != null) {
                for (String word : words) {
                    if (Pattern.compile(("[ -?!'.,(]" + word.toLowerCase() + "[ ?!-'.,)]"),
                            Pattern.CASE_INSENSITIVE)
                    .matcher(actor.getCareerDescription()).find()) {
                        new_list.add(actor);
                    }
                }
            }
        }

        for (Actor actor : new_list) {
            if ((Collections.frequency(new_list, actor)) == words.size()) {
                resultList.add(actor);
            }
        }
        ArrayList<Actor> resultListNew = new ArrayList<>();
        for (Actor actor : resultList) {
            if (resultListNew.contains(actor) == false) {
                resultListNew.add(actor);
            }
        }

        Collections.sort(resultListNew, new sortByName());
        if (action.getSortType() == "desc") {
            Collections.reverse(resultListNew);
        }
        ArrayList<String> actors_name = new ArrayList<>();
        for (int i = 0; i < resultListNew.size(); i++) {
            actors_name.add(resultListNew.get(i).getName());
        }
        return "Query result: " + actors_name;
    }

    /**
     * sort actors by name
     *
     */
    static class sortByName implements Comparator<Actor> {
        public int compare(Actor ac1, Actor ac2) {
            return (int) ((ac1.getName().compareTo(ac2.getName())));
        }
    }
}





