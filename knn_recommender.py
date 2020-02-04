import os
import pickle
import numpy as np
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from scipy.sparse import csr_matrix
from fuzzywuzzy import fuzz

def knn():
    data_path = 'ml-25m/'
    movies_filename = 'movies.csv'
    ratings_filename = 'ratings.csv'
    df_movies = pd.read_csv(
        os.path.join(data_path, movies_filename),

        usecols=['movieId', 'title'],
        dtype={'movieId': 'int32', 'title': 'str'})

    df_ratings = pd.read_csv(
        os.path.join(data_path, ratings_filename),
        usecols=['userId', 'movieId', 'rating'],
        dtype={'userId': 'int32', 'movieId': 'int32', 'rating': 'float32'})


    model_knn = NearestNeighbors(metric='cosine', algorithm='brute', n_neighbors=20, n_jobs=-1)
    num_users = len(df_ratings.userId.unique())
    num_items = len(df_ratings.movieId.unique())
    print('There are {} unique users and {} unique movies in this data set'.format(num_users, num_items))
    
    popularity_thres = 50

    df_movies_cnt = pd.DataFrame(df_ratings.groupby('movieId').size(), columns=['count'])
    popular_movies = list(set(df_movies_cnt.query('count >= @popularity_thres').index))
    df_ratings_drop_movies = df_ratings[df_ratings.movieId.isin(popular_movies)]
    print('shape of original ratings data: ', df_ratings.shape)
    print('shape of ratings data after dropping unpopular movies: ', df_ratings_drop_movies.shape)

    ratings_thres = 50

    df_users_cnt = pd.DataFrame(df_ratings_drop_movies.groupby('userId').size(), columns=['count'])
    active_users = list(set(df_users_cnt.query('count >= @ratings_thres').index))
    df_ratings_drop_users = df_ratings_drop_movies[df_ratings_drop_movies.userId.isin(active_users)]
    print('shape of original ratings data: ', df_ratings.shape)
    print('shape of ratings data after dropping both unpopular movies and inactive users: ', df_ratings_drop_users.shape)

    movie_user_mat = df_ratings_drop_users.pivot(index='movieId', columns='userId', values='rating').fillna(0)
    movie_to_idx = {
        movie: i for i, movie in 
        enumerate(list(df_movies.set_index('movieId').loc[movie_user_mat.index].title))
    }
    movie_user_mat_sparse = csr_matrix(movie_user_mat.values)
    model_knn = NearestNeighbors(metric='cosine', algorithm='brute', n_neighbors=20, n_jobs=-1)

    pickle.dump(movie_user_mat_sparse, open(data_file, 'wb'))
    pickle.dump(movie_to_idx, open(mapper_file, 'wb'))
    pickle.dump(model_knn, open(model_file, 'wb'))

def fuzzy_matching(mapper, fav_movie, verbose=True):
    match_tuple = []
    # get match
    for title, idx in mapper.items():
        ratio = fuzz.ratio(title.lower(), fav_movie.lower())
        if ratio >= 60:
            match_tuple.append((title, idx, ratio))
    # sort
    match_tuple = sorted(match_tuple, key=lambda x: x[2])[::-1]
    if not match_tuple:
        print('Oops! No match is found')
        return
    if verbose:
        print('Found possible matches in our database: {0}\n'.format([x[0] for x in match_tuple]))
    return match_tuple[0][1]


def make_recommendation(model_knn, data, mapper, fav_movie, n_recommendations):

    model_knn.fit(data)
    print('You have input movie:', fav_movie)
    idx = fuzzy_matching(mapper, fav_movie, verbose=True)
    print('Recommendation system start to make inference')
    print('......\n')
    distances, indices = model_knn.kneighbors(data[idx], n_neighbors=n_recommendations+1)
    
    raw_recommends = sorted(list(zip(indices.squeeze().tolist(), distances.squeeze().tolist())), key=lambda x: x[1])[:0:-1]
    reverse_mapper = {v: k for k, v in mapper.items()}

    print('Recommendations for {}:'.format(fav_movie))
    for i, (idx, dist) in enumerate(raw_recommends):
        print('{0}: {1}, with distance of {2}'.format(i+1, reverse_mapper[idx], dist))

def load():
    loaded_model = pickle.load(open(model_file, 'rb'))
    loaded_data = pickle.load(open(data_file, 'rb'))
    loaded_mapper = pickle.load(open(mapper_file, 'rb'))
    make_recommendation(
    model_knn=loaded_model,
    data=loaded_data,
    fav_movie=my_favorite,
    mapper=loaded_mapper,
    n_recommendations=10
    ) 

my_favorite = input("Write your favorite movie: ")

model_file = 'finalized_model2.sav'
data_file = 'finalized_model.sav'
mapper_file = 'finalized_model1.sav'

try:
    load()
except FileNotFoundError:
    knn()
    load()