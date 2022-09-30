package com.epiFiAssignment.moviesearch
class Constants {

    companion object {
        const val connectionTimeOutSeconds : Long = 60;
        const val COLUMN_COUNT = 3;

        const val IMAGE_BINDING_ADAPTER_VARIABLE = "imageFromPath"

        enum class Status {
            SUCCESS,
            ERROR,
            LOADING,
            NETWORK_ERROR
        }

        //Movie Types
        private const val TYPE_MOVIE = "movie"
        private const val TYPE_SERIES = "series"
        private const val TYPE_EPISODE = "episode"
        private const val TYPE_HOME = ""

        private const val TYPE_MOVIE_TEXT = "Movie"
        private const val TYPE_SERIES_TEXT = "Series"
        private const val TYPE_EPISODE_TEXT = "Episode"
        private const val TYPE_HOME_TEXT = "Home"

        val movieTypes = listOf<String>(TYPE_HOME,TYPE_MOVIE , TYPE_SERIES , TYPE_EPISODE)
        val movieTypeTexts = listOf<String>(TYPE_HOME_TEXT,TYPE_MOVIE_TEXT , TYPE_SERIES_TEXT , TYPE_EPISODE_TEXT)

        //API Query Params
        const val API_KEY_QUERY = "apikey"
        const val SEARCH_QUERY = "s"
        const val TYPE_QUERY = "type"
        const val PAGE_QUERY = "page"
        const val MOVIE_ID_QUERY = "i"
    }
}