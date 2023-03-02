<script>
import {useRoute} from "vue-router";

export default {
  data() {
    return {
      status: null,
      message: null,
    }
  },
  created() {
    this.setError();
  },
  methods: {
    setError() {
      const route = useRoute();
      const {status, message} = route.query;
      this.status = status;
      this.message = message;
    }
  }
}
</script>

<template>
  <h2 class="text-center fs-1">Oups...</h2>
  <p class="text-center">Something goes wrong!</p>
  <p class="text-center" v-if="status">{{status}}</p>
  <p class="text-center" v-if="message">{{message}}</p>
</template>