import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/icon.css';
import './components/common/directives';
import "babel-polyfill";
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import { Base64 } from 'js-base64';
import iView from 'iview';
import 'iview/dist/styles/iview.css';


window.Vue = Vue
Vue.use(ElementUI,{
  size: 'small'
});
Vue.use(iView);

const i18n = new VueI18n({
  locale: 'zh',
  messages
})

router.beforeResolve((to,form,next) => {
  let user = store.state.user;
  if(user == null || sessionStorage.getItem("user") == null){
    user = sessionStorage.getItem("user");
    if(user != null){
      store.state.user = JSON.parse(Base64.decode(user));
      user = store.state.user;
      if(user.authoriztion){
        axios.defaults.headers.post['Authoriztion'] = user.authoriztion;
        axios.defaults.headers.get['Authoriztion'] = user.authoriztion;
        axios.defaults.headers.delete['Authoriztion'] = user.authoriztion;
        axios.defaults.headers.put['Authoriztion'] = user.authoriztion;
        if(to.path == "/login") next("/");
      }else next("/login")
    }else{
      if(to.path != "/login"){
        axios.defaults.headers.post['Authoriztion'] = "";
        axios.defaults.headers.get['Authoriztion'] = "";
        axios.defaults.headers.delete['Authoriztion'] = "";
        axios.defaults.headers.put['Authoriztion'] = "";
        next("/login")
      }
    }
  }
  if(to.path != "/login" && to.path != "/403" && to.path != "/404"){
    axios.get("/api"+to.path+"/api").then(data => {
      if(data.data.code == 10002){
        sessionStorage.removeItem("user");
        next("/login")
      }else if(data.data == "未授权"){
        next("/403")
      }
      next(true);
    })
  }else{
    next(true);
  }
})


Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.config.productionTip = false

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
