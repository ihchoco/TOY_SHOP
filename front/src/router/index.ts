import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MemberView from '../views/MemberView.vue'
import MemberListView from '../views/MemberListView.vue'
import ProductView from '../views/ProductView.vue'
import ProductListView from '../views/ProductListView.vue'
import ReadProductView from '../views/ReadProductView.vue'
import OrderView from '../views/OrderView.vue'
import CartView from '../views/CartView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
       path: '/member',
       name: 'member',
       component: MemberView
    },
    {
      path: '/memberList',
      name: 'memberList',
      component: MemberListView
    },
    {
      path: '/product',
      name: 'product',
      component: ProductView
    },
    {
      path: '/productList',
      name: 'productList',
      component: ProductListView
    },
    {
      path: '/readProduct/:productId',
      name: 'readProduct',
      component: ReadProductView,
      props: true
    },
    {
      path: '/order',
      name: 'order',
      component: OrderView
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView
    }
    
  ]
})

export default router
