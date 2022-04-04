# IMBD_like_database
Object Oriented Programming Course
Homework - VideosDB

November 2021

#Name : Trandafir Laura

#Class : 323CA

# Info
https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema

# IMPLEMENTATIONS:
                package: myclasses:-
                         -class Actor that has all the attributes from ActorInputData and,
                                additionally has the tempVideos which helps us parse the videos from the
                                database. We use getters and implement the methods that we will use later in
                                the program getAwardsSum used to calculate the sum for awards. With the method
                                getRating we calculcate the actor average rating based on the ratings given to the
                                films he played in;

                         -class Action has all the attributes from ActionInputData we add getters to be easier
                                to access the arguments;
                          -class Database: we use this class in order to take the data from Input and parse it;


                         -class Commands, where we implement these methods:
                                -favorite: puts a video in to the user's favorite list. First, we go through
                                all the user to find the given user, and then check if the video is already in the
                                user favorites list, if it is not, we add it;
                                -ratingMovies: we find the given user, check to see if the user saw the video and
                                then add rating. We add this rating to the movie rating and to the user rating history
                                -ratingSeason does the same thing as ratingMovies except the rating is given to the season,
                                not the serial and the userRatingSeason list is updated for the user;
                                -view: with this command we add a video into the history of the user;

                         -Movies: this class contains all the attributes from MovieInputData to which we added a duration
                                    to be easier to sort, a rating list and a views number, this class also contains
                                    a method to calculate the rating;

                         -class Queries, where we implement the following methods:
                                  -getAverage in order to sort the actors by the average rating and the second criteria
                                   is alphabetical order;

                                   -getAwards: we use this to sort the actors that won specific awards given in input,
                                    we sort them by the total number of awards. We use the method string to enum
                                    to transform a string given(the awards name) into the ActorAward enum type;

                                    -moviesSortByRating: we use this method to sort all the movies based on rating and then,
                                    by name. First we filter the videos to see which has all the filters given using the filterVideo
                                    function. We check to see how many of the filters are not null to know wich video has all,
                                    and then we sort them with Collections.sort function

                                    -showsSortByRating: we use this method to sort the Shows by rating, with the same logic used for
                                    movies, but we use a different function to filter the shows called filteredShows

                                    -sortMoviesByDuration: we sort the movies that have the filters given by their duration(for
                                    shows we calculate the sum of the seasons) and we sort them using the moviewSortByDuration;

                                    -sortShowsByDuration: we use the same logic from sortMoviesByDuration;

                                    -sortShowsByFavorites: we sort them based on the appereance in users favorites list;

                                    -filterDescription: we user regex in order to find the right words that the user is looking for;

                                    -SortMoviesByViews: sorts movies based on the number of views;

                                    -SortShowsByViews: sorts the shows in the database by the number of views;

                                    -SsortUsers: this method is used to sort the users based on the number of rating they gave, the most active users.

                             -class Recommendation, where we implement the following methods:
                                    -standard: it returns the first movie/show that is not in the user's history;

                                    -bestUnseen: sorts the movies/series based on rating and returns the first unseen video from that list;

                                    -popularGenre: returns a hashmap with each genre and the number of views,
                                    we sort the map in order to get the most popular genre;

                                    -popular: returns the video from the most popular genre, that is not seen by the user, using the method above;

                                    -favorite: returns the video not seen by the user that has the most appearance in users favorite list

                                    -search: returns all the videos not seen by the user from a given genre;

                            -class Video that has all the attributes from ShowInput;
                            -class Movies represents a movie, and inherits the attributes of Video;
                            -class Show represents a movie, and inherits the attributes of Video;










