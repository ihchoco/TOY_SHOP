<script>
import axios from 'axios'
import { useRouter } from 'vue-router';

export default{
  data(){
    return {
      name : '',
      price : '',
      stockQuantity : '',
    }
  },
  methods : {
    onSubmitForm(){
      let json = {
          name : this.name,
          price : this.price,
          stockQuantity : this.stockQuantity
      };
      console.log(json);

      axios.post("/api/products", json).then(() => {

      });
    },
  },
};


</script>

<template>
  <div>
    <form v-on:submit.prevent="onSubmitForm">
      <div class="col-auto">
        <label for="memberName" class="form-label">제품명</label>
        <input type="text" id="memberName" class="form-control" aria-describedby="passwordHelpBlock" v-model="name">
      </div>
      <div class="col-auto">
        <label for="memberCity" class="form-label">가격</label>
        <input type="text" id="memberCity" class="form-control" aria-describedby="passwordHelpBlock" v-model="price">
      </div>
      <div class="col-auto">
        <label for="memberStreet" class="form-label">수량</label>
        <input type="text" id="memberStreet" class="form-control" aria-describedby="passwordHelpBlock" v-model="stockQuantity">
      </div>
      <div class="col-auto mt-3">
        <button type="submit" class="btn btn-primary">저장</button>
      </div>
    </form>
  </div>
</template>
