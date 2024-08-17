import axios from "axios";
import {getTokenName, messageConfirm, messageTip, removeToken} from "@/util/utils.js";
import {ElMessageBox} from "element-plus";

//定义访问后端接口地址的前缀
export let myAxios = axios.create({
    baseURL: 'http://localhost:8089'
});

// 添加请求拦截器
myAxios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    // 在请求头中放一个token（jwt），传给后端接口
    let token = window.sessionStorage.getItem(getTokenName())
    if (!token) { // 当token不存在时
        token = window.localStorage.getItem(getTokenName())
    }
    if (token) {  // 当token存在时
        config.headers['Authorization'] = token;
    }

    return config;
}, function (error) {
    // 对请求错误做些什么
    messageTip("系统请求出错！！！","error")
    return Promise.reject(error);
});

// 添加响应拦截器
myAxios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    // 拦截token验证的结果，进行相应的提示和页面跳转
    if (response.data.code > 900) {
        // 给前端用户提示，并且跳转页面
        messageConfirm(response.data.msg + ', 是否重新去登录')
            .then(() => {   // 用户点击确定执行这个函数
                // 删除错误的token
                removeToken()

                // 跳转到登录页
                window.location.href="/"
            })
            .catch(() => {  // 用户点击取消执行这个函数
                messageTip("取消去登录","warning")
            })
        return;
    }

    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    messageTip("系统响应出错！！！","error")
    return Promise.reject(error);
});