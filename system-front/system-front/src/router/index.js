import {createRouter, createWebHistory} from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import DashboardView from "@/views/DashboardView.vue";
import UserView from "@/views/UserView.vue";
import UserDetailView from "@/views/UserDetailView.vue";
import ActivityView from "@/views/ActivityView.vue";
import ActivityRecordView from "@/views/ActivityRecordView.vue";
import ActivityDetailView from "@/views/ActivityDetailView.vue";
import ClueView from "@/views/ClueView.vue";
import ClueRecordView from "@/views/ClueRecordView.vue";
import ClueDetailView from "@/views/ClueDetailView.vue";
import CustomerView from "@/views/CustomerView.vue";
import StatisticView from "@/views/StatisticView.vue";

//  创建一个路由实例
const router = createRouter({
    // 路由历史
    history: createWebHistory(import.meta.env.BASE_URL),
    // 配置路由，里面可以配置多个路由
    routes: [
        {
            // 路由路径
            path: '/',
            // 路由名字
            name: 'login',
            // 路由路径对应的页面
            component: LoginView
        },
        {
            // 路由路径
            path: '/dashboard',
            // 路由名字
            name: 'dashboard',
            // 路由路径对应的页面
            component: DashboardView,
            // 子路由
            children: [
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'user',
                    // 路由名字
                    name: 'user',
                    // 路由路径对应的页面
                    component: UserView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠,id是动态变量
                    path: 'user/:id',
                    // 路由名字
                    name: 'userDetail',
                    // 路由路径对应的页面
                    component: UserDetailView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'activity',
                    // 路由名字
                    name: 'activity',
                    // 路由路径对应的页面
                    component: ActivityView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'activity/add',
                    // 路由名字
                    name: 'activityAdd',
                    // 路由路径对应的页面
                    component: ActivityRecordView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'activity/edit/:id',
                    // 路由名字
                    name: 'activityEdit',
                    // 路由路径对应的页面
                    component: ActivityRecordView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'activity/:id',
                    // 路由名字
                    name: 'activityEdit',
                    // 路由路径对应的页面
                    component: ActivityDetailView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'clue',
                    // 路由名字
                    name: 'clue',
                    // 路由路径对应的页面
                    component: ClueView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'clue/add',
                    // 路由名字
                    name: 'clueAdd',
                    // 路由路径对应的页面
                    component: ClueRecordView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'clue/edit/:id',
                    // 路由名字
                    name: 'clueEdit',
                    // 路由路径对应的页面
                    component: ClueRecordView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'clue/detail/:id',
                    // 路由名字
                    name: 'clueDetail',
                    // 路由路径对应的页面
                    component: ClueDetailView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: 'customer',
                    // 路由名字
                    name: 'customer',
                    // 路由路径对应的页面
                    component: CustomerView,
                },
                {
                    // 路由路径:子路由路径前不能有斜杠
                    path: '',
                    // 路由路径对应的页面
                    component: StatisticView,
                },
            ]
        },
    ]
})
export default router;