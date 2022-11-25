package com.example.waracle

class Cakes {
    var title: String? = null
    var desc: String? = null
    var image: String? = null

    /**
     * Error descriptor used when fetching election results.
     */
    sealed class ResultsRepositoryException : Exception() {
        /**
         * Represents an error decoding the JSON data retrieved by the repository.
         * The JSON data may be malformed, or doesn't match the expected structure.
         */
        object InvalidJSON : ResultsRepositoryException()
    }
}