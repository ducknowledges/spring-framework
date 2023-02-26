<script setup>
import {RouterLink, useRoute} from 'vue-router';
</script>

<script>
import router from "@/router";

const API_BOOK = "/api/book";
const API_AUTHORS = "/api/authors/all";
const API_GENRES = "/api/genres/all";

export default {
  data() {
    return {
      id: null,
      name: "",
      authorId: null,
      genreId: null,
      authors: [],
      genres: [],
      fieldErrors: {
        name: null
      }
    }
  },

  created() {
    this.fetchBook(this.id);
    this.fetchAuthors();
    this.fetchGenres();
  },

  methods: {
    async fetchBook() {
      const route = useRoute();
      const id = route.params.id;
      const bookPath = `/${id}`;
      const url = API_BOOK + bookPath;
      const response = await fetch(url);
      const data = await response.json();
      if (response.ok) {
        this.setFetchedBook(data);
      } else {
        this.errorHandler(data);
      }
    },
    setFetchedBook(book) {
      const {id, name, authorId, genreId} = book;
      this.id = id;
      this.name = name;
      this.authorId = authorId;
      this.genreId = genreId;
    },
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
    async saveBook(e) {
      e.preventDefault();
      const bookPath = `/${this.id}`;
      const url = API_BOOK + bookPath;
      const response = await fetch(url, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: this.id,
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
    async deleteBook(e) {
      e.preventDefault();
      const bookPath = `/${this.id}`;
      const url = API_BOOK + bookPath;
      const response = await fetch(url, { method: "DELETE" });
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
  <div class="book-edit">
    <h2>Book</h2>

    <div class="container text-end">
      <RouterLink class="btn btn-close" to="/books"></RouterLink>
    </div>

    <form id="edit-form" class="container container-sm">

      <h3>Edit book:</h3>

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
                    :value="author.id"
                    :selected="author.id === authorId">
              {{ author.name }}
            </option>
          </select>
        </div>
      </div>

      <div class="row">
        <div class="dropdown col-4">
          <label class="form-label" for="genres">Choose an genre:</label>

          <select id="genres" name="genreId" class="form-control" v-model="genreId">
            <option v-for="genre in genres"
                    :key="genre"
                    :value="genre.id"
                    :selected="genre.id === genreId">
              {{ genre.name }}
            </option>
          </select>
        </div>
      </div>

    </form>

    <div class="row">
      <div class="col-4 m-2 p-0 row justify-content-around">
        <button class="btn btn-success col-4" @click="saveBook">Save</button>

        <button class="btn btn-danger col-4" @click="deleteBook">Delete</button>
      </div>
    </div>

  </div>
</template>