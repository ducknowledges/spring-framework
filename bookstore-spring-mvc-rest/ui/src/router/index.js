import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/books',
      name: 'books',
      component: () => import('../views/BooksView.vue')
    },
    {
      path: '/book',
      name: 'book-create',
      component: () => import('../views/BookCreateView.vue')
    },
    {
      path: '/book/:id',
      name: 'book-edit',
      component: () => import('../views/BookEditView.vue')
    },
    {
      path: '/authors',
      name: 'author',
      component: () => import('../views/AuthorsView.vue')
    },
    {
      path: '/genres',
      name: 'genre',
      component: () => import('../views/GenresView.vue')
    },
    {
      path: '/comments',
      name: 'comments',
      component: () => import('../views/BookCommentsView.vue')
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'page-not-found',
      component: () => import('../views/PageNotFound.vue')
    },
    {
      path: '/error',
      name: 'error',
      component: () => import('../views/ErrorView.vue')
    }
  ]
})

export default router
