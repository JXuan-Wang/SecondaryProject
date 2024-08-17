<script>
import {
  ArrowDown,
  Expand, Fold,
  Link,
  Location,
  Notebook,
  OfficeBuilding, Promotion,
  Setting,
  SuitcaseLine,
  Switch, User,
  UserFilled
} from "@element-plus/icons-vue";
import {myAxios} from "@/http/httpRequest.js";
import {messageConfirm, messageTip, removeToken} from "@/util/utils.js";

export default {
  name: "DashboardView",
  data() {
    return {
      // 控制左侧菜单左右的展开和折叠
      isCollapse: false,
      // 登录用户对象
      user: {},
      // 控制仪表盘页面右侧内容提是否显示
      isRouterAlive: true,
      currentRouterPath: '',
    }
  },
  components: {
    ArrowDown,
    Fold, Expand, Promotion, User, Setting, Notebook, SuitcaseLine, Switch, UserFilled, OfficeBuilding},

  methods: {
    // 左侧菜单的左右展开和折叠
    showMenu() {
      this.isCollapse = !this.isCollapse;
    },

    // 加载当前登录用户数据
    loadLoginUser() {
      myAxios.get("/api/login/info").then((resp)=>{
        this.user = resp.data.data;
        console.log(resp);
      })
    },

    // 退出登录
    logout(){
      myAxios.get("/api/logout").then(resp=>{
        if(resp.data.code === 200){
          removeToken()
          messageTip("退出成功","success")
          // 跳转到登录页
          window.location.href="/"
        }
      })
    },
    loadCurrentRouterPath(){
      let path=this.$route.path;
      let arr=path.split("/")
      if(arr.length>3){
        this.currentRouterPath="/"+arr[1]+"/"+arr[2]
      }else{
        this.currentRouterPath=path;
      }
    },
  },
  // vue的生命周期中的一个函数钩子，该钩子是在页面渲染后执行
  mounted() {
    // 加载当前登录用户
    this.loadLoginUser();
    this.loadCurrentRouterPath();
  },
  // 提供者
  // 右侧局部刷新函数
  provide() {
    return {
      //提供一个函数（要求是箭头函数）
      reload: () => {
        this.isRouterAlive = false; //右侧内容隐藏
        this.$nextTick(()=>this.isRouterAlive = true) //重新加载右侧页面
      }
    }
  },

}
</script>

<template>
  <el-container>
    <!--左侧-->
    <el-aside :width="isCollapse?'64px':'200px'">
      <div class="menuTitle" @Click="this.$router.push('/dashboard')">@啥也不是</div>
      <el-menu
          active-text-color="#ffd04b"
          background-color="#334157"
          class="el-menu-vertical-demo"
          :default-active="currentRouterPath"
          :collapse="isCollapse"
          :collapse-transition="false"
          unique-opened="unique-opened"
          :router="true"
          text-color="#fff">

        <el-sub-menu :index="index" v-for="(menuPermission,index) in user.menuPermissionList" :key="menuPermission.id">
          <template #title>
            <el-icon>
              <component :is="menuPermission.icon"/>
            </el-icon>
            <span>{{ menuPermission.name }}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item v-for="subPermission in menuPermission.subPermissionList" :key="subPermission.id" :index="subPermission.url">
              <el-icon>
                <component :is="subPermission.icon"/>
              </el-icon>
              {{ subPermission.name }}
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!--右侧-->
    <el-container class="rightContainer">
      <!--右侧：上-->
      <el-header>
        <el-icon class="show" @click="showMenu">
          <Fold/>
        </el-icon>
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            {{ user.name }}<el-icon class="el-icon--right"><arrow-down/></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!--右侧：中-->
      <el-main>
        <router-view v-if="isRouterAlive"/>
      </el-main>
      <!--右侧：下-->
      <el-footer>@没有版权，也不用来干什么</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-menu {
  border-right: 0;
}

.el-aside {
  background: #1a1a1a;
}

.el-header {
  height: calc(5vh);
  line-height: calc(5vh);
  background: #f8eeee;
}

.el-footer {
  line-height: calc(5vh);
  text-align: center;
  height: calc(5vh);
  background: #f8eeee;
}

.rightContainer {
  height: calc(100vh);
}

.el-aside {
  height: calc(100vh);
}

.menuTitle {
  text-align: center;
  line-height: calc(5vh);
  height: calc(5vh);
  color: #f9f9f9;
}

.show {
  cursor: pointer;
}

.el-dropdown {
  line-height: calc(5vh);
  float: right;
}
</style>