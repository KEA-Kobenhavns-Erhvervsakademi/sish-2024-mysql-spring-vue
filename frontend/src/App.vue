<script setup lang="ts">
import TodoList from './components/TodoList.vue'
</script>

<template>
  <header></header>

  <main>
    <TodoList endpoint="http://127.0.0.1:8080/todo" />
  </main>
</template>

<style scoped></style>
