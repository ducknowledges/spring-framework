<script setup>
import {RouterLink} from 'vue-router';
</script>

<script>
const API_BOOKS = "/api/books";

export default {
  data() {
    return {
      content: [],
      currentPage: 0,
      totalPages: 0,
      totalElements: 0
    }
  },

  created() {
    this.fetchBooks();
  },

  methods: {
    async fetchBooks(page = 0, size = 5) {
      const pageQuery = `?page=${page}&size=${size}`;
      const url = API_BOOKS + pageQuery;
      const response = await fetch(url);
      const data = await response.json();
      this.setFetchedData(data);
    },
    setFetchedData(data) {
      const {content, currentPage, totalPages, totalElements} = data;
      this.content = content;
      this.currentPage = currentPage;
      this.totalPages = totalPages;
      this.totalElements = totalElements;
    },
    getFirstPage() {
      this.fetchBooks();
    },
    getLastPage() {
      this.fetchBooks(this.totalPages - 1);
    },
    getPrevPage() {
      this.fetchBooks(this.currentPage - 1);
    },
    getNextPage() {
      this.fetchBooks(this.currentPage + 1);
    }
  }
}
</script>

<template>
  <div class="comments">

    <h2>Books</h2>

    <div class="container container-sm text-end">
      <button class="btn btn-primary">
        <RouterLink class="nav-link" to="/book">New</RouterLink>
      </button>
    </div>

    <table class="table table-sm table-borderless table-hover">
      <caption>Book Comments Table</caption>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Author</th>
        <th>Genre</th>
      </tr>
      </thead>
      <tbody>
      <tr class="align-middle" v-for="book of content">
        <td>{{book.id}}</td>
        <td>{{book.name}}</td>
        <td>{{book.author}}</td>
        <td>{{book.genre}}</td>

        <td>
          <button class="btn btn-outline-primary">
            <RouterLink class="nav-link" :to="`/book/${book.id}`">Edit</RouterLink>
          </button>
        </td>

        <td>
          <button class="btn btn-outline-primary">
            <RouterLink class="nav-link" :to="`/comments?book_id=${book.id}`">Comments</RouterLink>
          </button>
        </td>

      </tr>
      </tbody>

    </table>

    <div class="container container-sm text-center">

      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{disabled: currentPage === 0}">
            <button class="page-link" @click="getPrevPage">Previous</button>
          </li>
          <li class="page-item" v-if="currentPage !== 0">
            <button class="page-link" @click="getFirstPage">1</button>
          </li>
          <li class="page-item">
            <span class="page-link active" :class="{active: currentPage !== 0}" >
              {{currentPage + 1}}
            </span>
          </li>
          <li class="page-item" v-if="currentPage + 1 !== totalPages">
            <button class="page-link" @click="getLastPage">{{totalPages}}</button>
          </li>
          <li class="page-item" :class="{disabled: currentPage + 1 >= totalPages}">
            <button class="page-link" @click="getNextPage">Next</button>
          </li>
        </ul>
      </nav>

    </div>

  </div>
</template>