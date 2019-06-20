import Vue from 'vue'
import Vuex from 'vuex'
import crypto from 'crypto'

Vue.use(crypto)

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
    imgPath:'G:/img'
  },
  mutations: {

  },
  actions: {

  }
})
