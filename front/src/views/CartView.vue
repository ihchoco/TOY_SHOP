<script>
import axios from 'axios'

export default {
  data() {
    return {
      orders: []
    }
  },
  methods: {
    onCancelEvent(param){
      alert(param);
      axios.post(`/api/cart/${param}/cancel`).then((response) => {
        alert("장바구니 취소");
        this.orders = this.orders.filter((x) => x.id != param);
      })
    }
  },
  mounted() {
    axios.get('/api/cart').then((response) => {
      response.data.forEach((r) => {
        this.orders.push(r)
      })
      console.log(this.orders)
    })
  }
}
</script>

<template>
  <ul>
    <li v-for="order in orders" :key="order.name">
      <div class="content">
        <div>제품명 : {{ order.product.name }}</div>
        <div>수량 : {{ order.quantity }}</div>
        <div>가격 : {{ order.price }}</div>
      </div>
      <div>
        <button class="btn btn-warning" @click="onCancelEvent(order.id)">장바구니취소</button>
      </div>
    </li>
  </ul>
</template>
