package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;

import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import myclasses.*;
import org.json.simple.JSONArray;
import myclasses.Action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;


import static myclasses.Recommendation.standard;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        Database db = new Database(input);
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> list = db.getUsersList();
        for (User user : list) {
            for (Video video1 : videoList) {
                if (user.getFavVideos().contains(video1)) {
                    video1.favListShows = video1.getFavListShows() + 1;
                }

            }
        }


        for (int i = 0; i < db.getCommandsData().size(); i++) {
            Action action = db.getCommandsData().get(i);
            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("average")) {
                        String message = Queries.getAverage(db, action, action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("filter_description")) {
                        String message = Queries.FilterDescription(db, action);
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("awards")) {
                        String message = Queries.getAwards(db, action);
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("ratings")) {
                        String message = Queries.moviesSortByRating(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("ratings")) {
                        String message = Queries.showsSortByRating(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("longest")) {
                        String message = Queries.moviesSortByDuration(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("longest")) {
                        String message = Queries.showsSortByDuration(db, action,
                                                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("favorite")) {
                        String message = Queries.SortMoviesByFavorites(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("favorite")) {
                        String message = Queries.sortShowsByFavorites(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("users")) {
                    if (action.getCriteria().equals("num_ratings")) {
                        String message = Queries.sortUsers(db, action, action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("most_viewed")) {
                        String message = Queries.sortMoviesByViews(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }
                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("most_viewed")) {
                        String message = Queries.sortShowsByViews(db, action,
                                action.getNumber());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                }

            }
            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    String message;
                    message = standard(db, action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(),
                            null, message));
                }
                if (action.getType().equals("best_unseen")) {
                    String message;
                    message = Recommendation.bestUnseen(db, action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(),
                            null, message));
                }
                if (action.getType().equals("popular")) {
                    String message;
                    message = Recommendation.popular(db, action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(),
                            null, message));
                }
                if (action.getType().equals("favorite")) {
                    String message;
                    message = Recommendation.favorite(db, action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(),
                            null, message));
                }
                if (action.getType().equals("search")) {
                    String message;
                    message = Recommendation.search(db, action, action.getGenre());
                    arrayResult.add(fileWriter.writeFile(action.getActionId(),
                            null, message));
                }
            }




                if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    String message = Commands.favorites(db, action.getTitle(), action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));
                }
                if (action.getType().equals("view")) {
                    String message = Commands.view(db, action.getTitle(), action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));

                }
                if (action.getType().equals("rating")) {
                    if (action.getSeasonNumber() == 0) {
                        String message = Commands.ratingMovies(db, action.getTitle(), action,
                                action.getGrade());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));
                    }
                    if (action.getSeasonNumber() != 0) {
                        String message = Commands.ratingSeasons(db, action.getTitle(),
                                action, action.getSeasonNumber(), action.getGrade());
                        arrayResult.add(fileWriter.writeFile(action.getActionId(),
                                null, message));

                    }
                }
            }

       }

        fileWriter.closeJSON(arrayResult);
    }
}
