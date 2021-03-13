import axios from 'axios';
import router from '../router';
axios.defaults.timeout=5000;//超时时间是五秒
axios .defaults.withCredentials=true;//允许跨域
//Content-type响应头
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8';
//基础url
axios.defaults.baseURL="http://192.168.1.60:8888";

//响应拦截器
axios.interceptors.response.use(
    response =>{
        //如果response里边的 status是200,说明反问道接口，否则错误
        if(response.status==200)      {
            return Promise.resolve(response);
        }
        else{
            return Promise.reject(response);
        }
    },
    error =>{
        if(error.response.status){
            switch(error.response.status)
            {
                // 未登录
                case 401:
                    router.replace({
                        path:'/',
                        query:{
                            redirect:router.currentRoute.fullPath
                        }

                    });
                    break;
                // 找不到
                case 404:
                    break;

            }
            return Promise.reject(error.response);
                
        }
    }
);

/**
 * 封装一个get方法
 */

export function get(url,params={}){
    return new Promise((resolve,reject) => {
        axios.get(url,{params:params})
        .then(response =>{
            resolve(response.data);
        })
    .catch(err =>{
        reject(err);
    })
});
}

/**
 * 封装一个post方法
 */
export function post(url,data={}){
    return new Promise((resolve,reject) => {
        axios.post(url,data)
        .then(response =>{
            resolve(response.data);
        })

    .catch(err =>{
        reject(err);
    })
});
}