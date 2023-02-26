<script>
import {useRoute} from "vue-router";
import router from "@/router";

const API_COMMENTS = "/api/comments";

export default {
  data() {
    return {
      bookId: 0,
      content: [],
      currentPage: 0,
      totalPages: 0,
      totalElements: 0
    }
  },

  created() {
    this.bookId = useRoute().query.book_id;
    this.fetchComments();
  },

  methods: {
    async fetchComments(page = 0, size = 5) {
      const pageQuery = this.bookId
          ? `?book_id=${this.bookId}&page=${page}&size=${size}`
          : `?page=${page}&size=${size}`;
      const url = API_COMMENTS + pageQuery;
      const response = await fetch(url);
      const data = await response.json();
      if (response.ok) {
        this.setFetchedData(data);
      } else {
        this.errorHandler(date);
      }
    },
    setFetchedData(data) {
      this.content = data.content;
      this.currentPage = data.currentPage;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
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
    getFirstPage() {
      this.fetchComments();
    },
    getLastPage() {
      this.fetchComments(this.totalPages - 1);
    },
    getPrevPage() {
      this.fetchComments(this.currentPage - 1);
    },
    getNextPage() {
      this.fetchComments(this.currentPage + 1);
    }
  }
}
</script>

<template>
  <div class="comments">

    <h2>Book Comments</h2>
    <table class="table table-sm table-borderless table-hover">
      <caption>Book Comments Table</caption>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Book</th>
      </tr>
      </thead>
      <tbody>
      <tr class="align-middle" v-for="comment of content">
        <td>{{comment.id}}</td>
        <td>{{comment.message}}</td>
        <td>{{comment.bookName}}</td>
      </tr>
      </tbody>

    </table>

    <div class="container container-sm text-center">

      <nav aria-label="Page navigation example" v-if="content.length">
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
