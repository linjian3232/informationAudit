import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../pages/Login.vue'], resolve)
    },
    {
      path: '/Home',
      component: resolve => require(['../components/Home.vue'], resolve),
      children:[
        {
          path: '/Info',
          component: resolve => require(['../pages/InfoPage.vue'], resolve)
        },
        {
          path: '/Consumer',
          component: resolve => require(['../pages/ConsumerPage.vue'], resolve)
        },
        {
          path: '/Uploader',
          component: resolve => require(['../pages/UploaderPage.vue'], resolve)
        },
        {
          path: '/Unreview',
          component: resolve => require(['../pages/UnreviewPage.vue'], resolve)
        },
        {
          path: '/Review',
          component: resolve => require(['../pages/ReviewPage.vue'], resolve)
        },
        {
          path: '/Reviewer',
          component: resolve => require(['../pages/Reviewer.vue'], resolve)
        },
        {
          path: '/Teminator',
          component: resolve => require(['../pages/Teminator.vue'], resolve)
        },
        {
          path: '/PersonalFile',
          component: resolve => require(['../pages/PersonalFilePage.vue'], resolve)
        }
        ,
        {
          path: '/Refuse',
          component: resolve => require(['../pages/RefusePage.vue'], resolve)
        }
        ,
        {
          path: '/Agree',
          component: resolve => require(['../pages/AgreePage.vue'], resolve)
        }
        ,
        {
          path: '/Disagree',
          component: resolve => require(['../pages/DisagreePage.vue'], resolve)
        }
        ,
        {
          path: '/Wait',
          component: resolve => require(['../pages/WaitPage.vue'], resolve)
        }
      ]
    }
   
  ]
})
