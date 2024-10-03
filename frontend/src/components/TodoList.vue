<script setup lang="ts">
import { ref, onMounted, type Ref } from 'vue'
import axios from 'axios'

const props = defineProps<{endpoint: string}>()
const todoEndpoint = props.endpoint

enum TodoStatus {
    Pending = 'Pending',
    InProgress = 'InProgress',
    Completed = 'Completed',
}

interface Todo {
  id: number
  title: string
  status: string
  createdAt: string
  updatedAt: string
}

const todos: Ref<Todo[]> = ref([])
const newTitle = ref("")
const createError = ref("")

const fetchTodos = async () => {
  try {
    const response = await axios.get<Todo[]>(todoEndpoint + '/list')
    todos.value = response.data
  } catch (error) {
    console.error(error)
  }
}

const createTodo = async () => {
  const title = newTitle.value
  const status = TodoStatus.Pending.toString()
  const params = { title, status }

  await axios.post(todoEndpoint + '/create', null, { params }).then((response) => {
    newTitle.value = ''
    createError.value = ''
    todos.value.push(response.data.item)
    console.log(response)
  }).catch((err) => {
    createError.value = 'Could not create :('
    console.error(err.response)
  })
}

const completeTodo = async (id: number) => {
  const params = { id, status: 'Completed' }
  await axios.post(todoEndpoint + '/update', null, { params }).then((response) => {
    const item = todos.value.find(item => item.id == id)
    if (item !== undefined) { item.status = 'Completed' }
    console.log(response)
  }).catch((err) => {
    console.error(err.response)
  })
}

const deleteTodo = async (id: number) => {
  const params = { id }
  await axios.post(todoEndpoint + '/delete', null, { params }).then((response) => {
    todos.value = todos.value.filter(item => item.id !== id)
    console.log(response)
  }).catch((err) => {
    console.error(err.response)
  })
}

onMounted(() => {
  fetchTodos()
})
</script>

<template>
  <div>
    <h1>Todo List</h1>
    <ul>
      <li v-for="todo in todos" :key="todo.id">
        <span class="control" @click="completeTodo(todo.id)">✅</span>
        <span class="control" @click="deleteTodo(todo.id)">❌</span>
        <span :class="{ completed: todo.status == 'Completed' }">{{ todo.title }}</span>
      </li>
      <li>
        <input type="text" placeholder="New item..." v-model="newTitle">
        <button @click="createTodo()">Add</button>
        <span class="error" v-if="createError">{{ createError }}</span>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.control {
  cursor: default;
}
.completed {
  color: gray;
  text-decoration: line-through;
}
.error {
  color: red;
}
</style>