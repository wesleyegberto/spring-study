# Spring GraphQL - Basics

GraphiQL: http://localhost:8080/graphiql

## Query

Recents movies:

```graphql
query {
  recentsMovies {
    id
    title
    category
    description
  }
}
```

Search by title:

```graphql
query {
  searchMovies(title: "Matrix", page: 0, pageSize: 10) {
    id
    title
    category
    description
  }
}
```

Search by category:

```graphql
query {
  searchMovies(category: "action", page: 0, pageSize: 10) {
    id
    title
    category
    description
  }
}
```

## Mutation

Add movie:

```graphql
# Variables
{
  "newMovie": {
    "title": "Hackers",
    "description": "A young boy is arrested by the U.S. Secret Service for writing a computer virus and is banned from using a computer until his 18th birthday. Years later, he and his new-found friends discover a plot to unleash a dangerous computer virus, but they must use their computer skills to find the evidence while being pursued by the Secret Service and the evil computer genius behind the virus.",
    "category": "Crime, Drama, Romance, Thriller",
    "starring": [
      "Jonny Lee Miller", "Angelina Jolie", "Jesse Bradford", "Matthew Lillard"
    ]
  }
}

# Query
mutation addNewMovie($newMovie: MovieRequest!) {
  addMovie(movie: $newMovie) {
    id
    title
    description
    category
    starring {
      name
    }
  }
}
```

Add movie verbose:

```graphql
mutation {
  addMovieVerbose(
    title: "Hackers"
    description: "A young boy is arrested by the U.S. Secret Service for writing a computer virus and is banned from using a computer until his 18th birthday. Years later, he and his new-found friends discover a plot to unleash a dangerous computer virus, but they must use their computer skills to find the evidence while being pursued by the Secret Service and the evil computer genius behind the virus."
    category: "Crime, Drama, Romance, Thriller"
    starring: ["Jonny Lee Miller", "Angelina Jolie", "Jesse Bradford", "Matthew Lillard"]
  ) {
    id
    title
    description
    category
    starring {
      name
    }
  }
}
```

