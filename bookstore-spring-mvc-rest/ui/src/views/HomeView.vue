<script>
import router from "@/router";

const API_STATISTICS = "/api/statistics"

export default {
  data() {
    return {
      bookCounter: 0,
      authorCounter: 0,
      genreCounter: 0,
    }
  },

  created() {
    this.fetchStatistics();
  },

  methods: {
    async fetchStatistics() {
      const response = await fetch(API_STATISTICS);
      if (response.ok) {
        const statistics = await response.json();
        this.setFetchedStatistics(statistics);
      } else {
        await router.push({path: "/error"});
      }
    },
    setFetchedStatistics(statistics) {
      this.bookCounter = statistics.bookCounter;
      this.authorCounter = statistics.authorCounter;
      this.genreCounter = statistics.genreCounter;
    }
  }
}
</script>

<template>
  <p class="text-center fs-1">Welcome to Bookstore!</p>
  <div class="text-center">
    <img src="@/assets/statistics.png" alt="Statistics" width="96" height="96">
  </div>
  <p class="text-center fs-3">
    Book counter: <span>{{bookCounter}}</span>
  </p>
  <p class="text-center fs-3">
    Author counter: <span>{{authorCounter}}</span>
  </p>
  <p class="text-center fs-3">
    Genre counter: <span>{{genreCounter}}</span>
  </p>
</template>
