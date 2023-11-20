<script>
import axios from 'axios'

export default{
  data(){
    return {
      products : [],
      members : [],
      member : '',
    }
  },
  methods : {
    
  },
  mounted(){
    axios.get('/api/products').then((response) => {
      response.data.forEach(r => {
        this.products.push(r)
      })
    })
  }
};


</script>

<template>
  <ul>
    <li v-for="product in products" :key="products.name">
      <router-link :to="{ name: 'readProduct', params: { productId: product.id } }">
      <div class="title">
          {{ product.name }}
      </div>
      <div class="content">
        <div>
          가격 : {{ product.price }}
        </div>
        <div>
          수량 : {{ product.stockQuantity }}
        </div>
      </div>
      </router-link>
    
    </li>
  </ul>
</template>
