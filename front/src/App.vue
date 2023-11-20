<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <el-header class="header">
      <el-menu mode="horizontal" router>
          <el-menu-item index="/">HOME</el-menu-item>
          <el-menu-item index="/member">멤버 등록</el-menu-item>
          <el-menu-item index="/memberList">멤버 조회</el-menu-item>
          <el-menu-item index="/product">제품 등록</el-menu-item>
          <el-menu-item index="/productList">제품 조회</el-menu-item>
          <el-menu-item index="/order">주문 조회</el-menu-item>
          <el-menu-item index="/cart">장바구니 조회</el-menu-item>
      </el-menu>
  </el-header>
  <el-main class="content">
      <el-row>
          <el-col>
              <RouterView />
          </el-col>
      </el-row>
  </el-main>
</template>

<style scoped>


.content{
    height: calc(100vh - 60px);
}
</style>
