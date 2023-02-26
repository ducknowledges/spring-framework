<script setup>
import {RouterLink} from 'vue-router';</script>

<script>
import router from "@/router";

const API_BOOK = "/api/book";
const API_AUTHORS = "/api/authors/all";
const API_GENRES = "/api/genres/all";

export default {
  data() {
    return {
      name: "",
      authorId: 1,
      genreId: 1,
      authors: [],
      genres: [],
      fieldErrors: {
        name: null
      }
    }
  },

  created() {
    this.fetchAuthors();
    this.fetchGenres();
  },

  methods: {
    async fetchAuthors() {
      const response = await fetch(API_AUTHORS);
      const authors = await response.json();
      this.setFetchedAuthors(authors);
    },
    setFetchedAuthors(authors) {
      this.authors = authors;
    },
    async fetchGenres() {
      const response = await fetch(API_GENRES);
      const genres = await response.json();
      this.setFetchedGenres(genres);
    },
    setFetchedGenres(genres) {
      this.genres = genres;
    },
    async createBook() {
      const response = await fetch(API_BOOK, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: this.name,
          authorId: this.authorId,
          genreId: this.genreId
        }),
      });
      const data = await response.json();
      if (response.ok) {
        await router.push({path: "/books"});
      } else {
        this.errorHandler(data);
      }
    },
    errorHandler(data) {
      const { fieldErrors } = data;
      if (fieldErrors) {
        this.fieldErrors = fieldErrors;
      } else {
        router.push({
          path: "/error",
          query: {
            message: data.message,
            status: data.status
          }
        });
      }
    },
  }
}
</script>

<template>
  <div class="book-create">
    <h2>Book</h2>

    <div class="container text-end">
      <RouterLink class="btn btn-close" to="/books"></RouterLink>
    </div>

    <form id="edit-form" class="container container-sm">

      <h3>Create book:</h3>

      <div class="row">
        <div class="col-4">
          <label class="form-label" for="book-name">Name:</label>
          <input :class="fieldErrors.name ? 'form-control is-invalid' : 'form-control'"
                 id="book-name" name="name" type="text" v-model="name"
          />
          <p class="invalid-feedback" v-if="fieldErrors.name">{{fieldErrors.name}}</p>
        </div>
      </div>

      <div class="row">
        <div class="dropdown col-4">
          <label class="form-label" for="authorId">Choose an author:</label>

          <select id="authors" name="authorId" class="form-control" v-model="authorId">
            <option v-for="author in authors"
                    :key="author"
                    :value="author.id">
              {{ author.name }}
            </option>
          </select>
        </div>
      </div>

      <div class="row">
        <div class=" col-4">
          <label class="form-label" for="genres">Choose an genre:</label>

          <select id="genres" name="genreId" class="form-control" v-model="genreId" >
            <option v-for="genre in genres"
                    :key="genre"
                    :value="genre.id">
              {{ genre.name }}
            </option>
          </select>
        </div>
      </div>

    </form>

    <div class="row">
      <div class="col-4 m-2 p-0 row justify-content-around">
        <button class="btn btn-success col-4" @click="createBook">Create</button>
      </div>
    </div>

  </div>
</template>