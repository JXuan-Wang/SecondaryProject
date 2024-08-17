<script>
import {myAxios} from "@/http/httpRequest.js";

export default {
  name: "UserDetailView",
  data(){
    return {
      // 用户详情对象
      userDetail:{
        createUser: {},
        editUser: {},
      }
    }
  },
  mounted() {
    this.loadUserDetail()
  },

  methods: {
    // 加载用户详情
    loadUserDetail(){
      // 从路径中拿出id
      let id = this.$route.params.id
      myAxios.get("/api/user/"+id).then(resp => {
        if(resp.data.code === 200) {
          this.userDetail = resp.data.data
          if(this.userDetail.createUser === null){
            this.userDetail.createUser = {}
          }
          if(this.userDetail.editUser === null){
            this.userDetail.editUser = {}
          }
        }
      })
    }
  }


}
</script>

<template>
  <el-form label-width="auto" style="max-width: 600px">
    <el-form-item label="ID">
      {{ userDetail.id }}
    </el-form-item>
    <el-form-item label="账号">
      {{ userDetail.loginAct }}
    </el-form-item>
    <el-form-item label="密码">
      {{ userDetail.loginPwd }}
    </el-form-item>
    <el-form-item label="姓名">
      {{ userDetail.name }}
    </el-form-item>
    <el-form-item label="手机">
      {{ userDetail.phone }}
    </el-form-item>
    <el-form-item label="邮箱">
      {{ userDetail.email }}
    </el-form-item>
    <el-form-item label="账号未过期">
      {{ userDetail.accountNoExpired === 1 ? '是' : '否' }}
    </el-form-item>
    <el-form-item label="密码未过期">
      {{ userDetail.credentialsNoExpired === 1 ? '是' : '否' }}
    </el-form-item>
    <el-form-item label="账号未锁定">
      {{ userDetail.accountNoLocked === 1 ? '是' : '否' }}
    </el-form-item>
    <el-form-item label="账号是否可用">
      {{ userDetail.accountEnabled === 1 ? '是' : '否' }}
    </el-form-item>
    <el-form-item label="创建时间">
      {{ userDetail.createTime }}
    </el-form-item>
    <el-form-item label="创建人">
      {{ userDetail.createUser.name }}
    </el-form-item>
    <el-form-item label="修改时间">
      {{ userDetail.editTime }}
    </el-form-item>
    <el-form-item label="修改人">
      {{ userDetail.editUser.name }}
    </el-form-item>
    <el-form-item label="最近登录时间">
      {{ userDetail.lastLoginTime }}
    </el-form-item>
  </el-form>
  <el-button type="primary" @click="() => this.$router.go(-1)">返回</el-button>
</template>

<style scoped>

</style>