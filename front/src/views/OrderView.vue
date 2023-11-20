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
      axios.post(`/api/orders/${param}/cancel`).then((response) => {
        alert("주문 취소");
        this.orders = this.orders.filter((x) => x.id != param);
      })
    }
  },
  mounted() {
    axios.get('/api/orders').then((response) => {
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
      <div class="title">주문날짜 : {{ order.orderDate }}</div>
      <div class="title">주문자 : {{ order.orderer.name }}</div>
      <div class="content">
        <div v-for="item in order.orderItems" :key="item.id">
          <div>제품명 : {{ item.productInfo.Name }}</div>
          <div>수량 : {{ item.count }}</div>
          <div>가격 : {{ item.orderPrice }}</div>
        </div>
      </div>
      <div>총액가격 : {{ order.orderItems[0].totalPrice }}</div>
      <div>
        <button class="btn btn-warning" @click="onCancelEvent(order.id)">주문취소</button>
      </div>
    </li>
  </ul>
</template>
