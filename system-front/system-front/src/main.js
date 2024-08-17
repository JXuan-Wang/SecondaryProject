import 'element-plus/dist/index.css'
import './assets/global.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'

import router from "@/router/index.js";
import App from "@/App.vue";

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import {zhCn} from "element-plus/es/locale/index";
import {myAxios} from "@/http/httpRequest.js";

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(createPinia())
app.use(router)
app.use(ElementPlus,{
    locale: zhCn
})
app.directive("hasPermission",(el, binding)=>{
    let flag=false;
    myAxios.get("/api/login/info").then(resp=>{
        let user = resp.data.data;
        let permissionList = user.permissionList;
        for(let key in permissionList){
            if(key === binding.value){
                flag=true;
                break;
            }
        }
        if(!flag){
            el.style.display='none';
        }
    })
})
app.mount('#app')
