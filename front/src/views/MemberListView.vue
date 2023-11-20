<script>
import axios from 'axios'

export default{
  data(){
    return {
      members : [],
    }
  },
  methods : {
    
  },
  mounted(){
    axios.get('/api/members').then((response) => {
      response.data.forEach(r => {
        console.log(r);
        this.members.push(r)
      })
    })
  }
};


</script>

<template>
  <ul>
    <li v-for="member in members" :key="member.name">
      <div class="title">
          {{ member.name }}
      </div>
      <div class="content">
        <div>
          도시 : {{ member.address.city }}
        </div>
        <div>
          거리 : {{ member.address.street }}
        </div>
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2023-11-01</div>
      </div>
    </li>
  </ul>
</template>
