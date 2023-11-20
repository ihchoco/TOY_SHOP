<script>
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  data() {
    return {
      product: '',
      members : [],
      selected : '',
      orderCount : '',
    }
  },
  props: ['productId'],
  methods: {
    onSubmitForm() {
      let json = {
          'memberId' : this.selected,
          'orderItemRequestDtos' : [{
            'productId' : this.product.id,
            'count' : this.orderCount,
            'price' : this.product.price
          }],
          'shippingInfo' : {
            'receiver' : {
              'name' : '홍길동',
              'phoneNumber' : '010-1234-5678'
            },
            'address' : {
              'city' : '경기도',
              'street' : '거리',
              'zipcode' : '12312'
            },
            'request' : '-'
          }
        }
        console.log(json);

        axios.post(`/api/orders`, json).then((response) => {
          console.log(response);
          this.product = response.data;
        })
    },
    addCartItem(){
      alert("장바구니 추가");
      let json = {
        'memberId' : this.selected,
        'productId' : this.product.id,
        'quantity' : this.orderCount,
        'price' : this.product.price
      }
      console.log(json);
      
      axios.post(`/api/cart`, json).then((response) => {
        console.log(response);
        this.product = response.data;
      })
    },
  },
  mounted() {
    axios.get(`/api/products/${this.productId}`).then((response) => {
      console.log(response);
      this.product = response.data;
    })

    axios.get(`/api/members`).then((response) => {
      console.log(response);
      this.members = response.data;
    })
  }
}
</script>

<template>
  <main>
    <div>상품명 : {{ product.name }}</div>
    <div>수량 : {{ product.stockQuantity }}</div>
    <div>가격 : {{ product.price }}</div>

    <div class="mt-3">주문 수량 : <input type="number" v-model="orderCount" /></div>
    <div class="mt-3">주문자 선택 : 
      <select class="form-control mt-3" v-model="selected">
        <option v-for="member in members" :key="member.id" :value="member.id">{{ member.name }}</option>
      </select>
    </div>
    <div class="mt-3">
      <button @click="onSubmitForm" class="btn btn-primary">주문하기</button>
    </div>
    <div class="mt-3">
      <button @click="addCartItem" class="btn btn-warning">장바구니 담기</button>
    </div>
   
  </main>
</template>
