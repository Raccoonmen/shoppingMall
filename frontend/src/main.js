import { createApp } from 'vue'
import App from './App.vue'
import router from "@/scripts/router"
import store from "@/scripts/store";

createApp(App).use(store).use(router).mount('#app')
