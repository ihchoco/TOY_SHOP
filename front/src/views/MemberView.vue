<script>
import axios from 'axios'
import { useRouter } from 'vue-router';

export default{
  data(){
    return {
      name : '',
      city : '',
      street : '',
      zipcode : '',
    }
  },
  methods : {
    onSubmitForm(){
      axios.post("/api/members", {
        name : this.name,
        address : {
          city : this.city,
          street : this.street,
          zipcode : this.zipcode
        }
      }).then(() => {
      });
    },
  },
};


</script>

<template>
  <div>
    <form v-on:submit.prevent="onSubmitForm">
      <div class="col-auto">
        <label for="memberName" class="form-label">이름</label>
        <input type="text" id="memberName" class="form-control" aria-describedby="passwordHelpBlock" v-model="name">
      </div>
      <div class="col-auto mt-3">
        [주소]
      </div>
      <div class="col-auto">
        <label for="memberCity" class="form-label">도시</label>
        <input type="text" id="memberCity" class="form-control" aria-describedby="passwordHelpBlock" v-model="city">
      </div>
      <div class="col-auto">
        <label for="memberStreet" class="form-label">거리</label>
        <input type="text" id="memberStreet" class="form-control" aria-describedby="passwordHelpBlock" v-model="street">
      </div>
      <div class="col-auto">
        <label for="memberZipcode" class="form-label">우편번호</label>
        <input type="text" id="memberZipcode" class="form-control" aria-describedby="passwordHelpBlock" v-model="zipcode">
      </div>
      <div class="col-auto mt-3">
        <button type="submit" class="btn btn-primary">저장</button>
      </div>
    </form>
  </div>
</template>
