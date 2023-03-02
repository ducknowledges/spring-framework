<script setup>
import {RouterLink} from 'vue-router'
</script>

<template>
  <header>

    <nav class="navbar navbar-expand-lg">
      <div class="container xd-container">
        <div class="navbar-brand">
          <img alt="BookStore" src="@/assets/logo.png" width="30" height="36">
        </div>
        <div class="collapse navbar-collapse">
          <div class="navbar-nav me-auto mb-2 mb-lg-0">
            <RouterLink class="nav-link" activeClass="active fs-5" to="/">Home</RouterLink>
            <RouterLink class="nav-link" activeClass="active fs-5" to="/books">Books</RouterLink>
            <RouterLink class="nav-link" activeClass="active fs-5" to="/authors">Authors</RouterLink>
            <RouterLink class="nav-link" activeClass="active fs-5" to="/genres">Genres</RouterLink>
            <RouterLink class="nav-link" activeClass="active fs-5" to="/comments">Comments</RouterLink>
          </div>
        </div>
      </div>
    </nav>

  </header>
</template>