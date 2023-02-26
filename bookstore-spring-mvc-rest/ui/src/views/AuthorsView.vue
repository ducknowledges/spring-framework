<script>
const API_AUTHORS = "/api/authors";

export default {
  data() {
    return {
      content: [],
      currentPage: 1,
      totalPages: 0,
      totalElements: 0
    }
  },

  created() {
    this.fetchAuthors();
  },

  methods: {
    async fetchAuthors(page = 0, size = 5) {
      const pageQuery = `?page=${page}&size=${size}`;
      const url = API_AUTHORS +  pageQuery;
      const response = await fetch(url);
      const data = await response.json();
      this.setFetchedData(data);
    },
    setFetchedData(data) {
      this.content = data.content;
      this.currentPage = data.currentPage;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
    },
    getFirstPage() {
      this.fetchAuthors();
    },
    getLastPage() {
      this.fetchAuthors(this.totalPages - 1);
    },
    getPrevPage() {
      this.fetchAuthors(this.currentPage - 1);
    },
    getNextPage() {
      this.fetchAuthors(this.currentPage + 1);
    }
  }
}
</script>

<template>
  <div class="authors">

    <h2>Authors</h2>
    <table class="table table-sm table-borderless table-hover">
      <caption>Authors Table</caption>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <tr class="align-middle" v-for="author of content">
        <td>{{author.id}}</td>
        <td>{{author.name}}</td>
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
