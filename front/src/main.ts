import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus' //ElementPlus 2줄 추가
import 'element-plus/dist/index.css'

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'


const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)


app.mount('#app')
