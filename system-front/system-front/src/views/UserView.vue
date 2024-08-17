<script>
import {myAxios} from "@/http/httpRequest.js";
import {messageConfirm, messageTip, removeToken} from "@/util/utils.js";

export default {
  name: "UserView",
  inject: ["reload"],
  data() {
    return {
      userList: [],
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      userQuery: {},  // 定义表单对象初始值是空
      // 定义添加用户表单的验证规则
      userRules: {
        loginAct: [
          {required: true, message: '请输入登录账号', trigger: 'blur'},
        ],
        loginPwd: [
          {required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 16, message: '登录密码长度为6~16位', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {pattern: /^[\u4e00-\u9fa5]/, message: '名字必须是中文', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {
            pattern: /^(13[0-9]|14[5-9]|15[0-3,5-9]|16[6]|17[0-8]|18[0-9]|19[8,9])\d{8}$/,
            message: '请输入正确的手机号码', trigger: 'blur'
          },
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/,
            message: '请输入正确的邮箱', trigger: 'blur'
          }
        ],
        accountNoExpired: [
          {required: true, message: '请选择', trigger: 'blur'},
        ],
        credentialsNoExpired: [{required: true, message: '请选择', trigger: 'blur'},],
        accountNoLocked: [{required: true, message: '请选择', trigger: 'blur'},],
        accountEnabled: [{required: true, message: '请选择', trigger: 'blur'},],
      },
      options: [
        {label: "是", value: 1},
        {label: "否", value: 0},
      ],
      //用户id的数据
      userIdArray: [],
    }
  },

  mounted() {
    this.getData()
  },

  methods: {
    // 勾选或者取消勾选，触发该函数
    handleSelectionChange(selectionItemArray) {
      this.userIdArray = []
      selectionItemArray.forEach(item => {
        let userId = item.id
        this.userIdArray.push(userId)
      })
    },

    // 查询用户列表数据
    getData(current) {
      myAxios.get("/api/users", {
        params: {
          current: current, // 当前查询第几页
        }
      }).then(resp => {
        if (resp.data.code === 200) {
          this.userList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // 进入详情页
    view(id) {
      // 跳转到这个路由
      this.$router.push("/dashboard/user/" + id)
    },

    // 添加用户，提交保存
    userSubmit() {
      this.$refs.userRefForm.validate(isValid => {
        if (isValid) {
          if (this.userQuery.id > 0) {
            myAxios.put("/api/user", this.userQuery).then(resp => {
              if (resp.data.code === 200) {
                messageTip("编辑成功", "success")
                this.reload();
              } else {
                messageTip("编辑失败", "error")
              }
            })
          } else {
            myAxios.postForm("/api/user", this.userQuery).then(resp => {
              if (resp.data.code === 200) {
                messageTip("提交成功", "success")
                this.reload();
              } else {
                messageTip("提交失败", "error")
              }
            })
          }
        }
      })
    },

    // 进入编辑页
    edit(id) {
      //打开对话框
      this.dialogVisible = true;
      this.loadUser(id);
    },

    // 添加用户
    add() {
      this.userQuery = {};
      this.dialogVisible = true;
    },

    // 加载用户信息
    loadUser(id) {
      myAxios.get("/api/user/" + id).then(resp => {
        if (resp.data.code === 200) {
          this.userQuery = resp.data.data;
          this.userQuery.loginPwd = "";
        }
      })
    },

    // 删除用户
    del(id) {
      messageConfirm("您确定要删除该数据吗？").then(() => {   // 用户点击确定执行这个函数
        myAxios.delete("/api/user/" + id).then(resp => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success")
            this.reload()
          } else {
            messageTip("删除失败", "error")
          }
        })
      })
          .catch(() => {  // 用户点击取消执行这个函数
            messageTip("取消删除", "warning")
          })
    },

    // 批量删除用户
    batchDel() {
      if (this.userIdArray.length <= 0) {
        messageTip("请选择要删除的数据", "warning")
        return;
      }
      messageConfirm("您确定要删除这些数据吗？").then(() => {   // 用户点击确定执行这个函数
        let ids = this.userIdArray.join(",")
        myAxios.delete("/api/users", {
          params: {
            ids: ids
          }
        }).then(resp => {
          if (resp.data.code === 200) {
            messageTip("批量删除成功", "success")
            this.reload()
          } else {
            messageTip("批量删除失败", "error")
          }
        })
      })
          .catch(() => {  // 用户点击取消执行这个函数
            messageTip("取消批量删除", "warning")
          })
    },
  }
}
</script>

<template>
  <el-button type="primary" @click="add">添加用户</el-button>
  <el-button type="danger" @click="batchDel">批量删除</el-button>
  <el-table
      :data="userList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="loginAct" label="账号" width="120"/>
    <el-table-column property="name" label="姓名" width="80" show-overflow-tooltip/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="email" label="邮箱" show-overflow-tooltip width="120"/>
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip width="170"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
      </template>

    </el-table-column>
  </el-table>
  <el-pagination background layout="prev, pager, next"
                 :page-size="pageSize"
                 @current-change="getData"
                 @prev-click="getData"
                 @next-click="getData"
                 :total="total"/>

  <el-dialog v-model="dialogVisible" :title="userQuery.id > 0 ? '编辑用户' : '添加用户'" width="55%" center draggable>
    <el-form ref="userRefForm" :model="userQuery" label-width="auto" style="max-width: 600px"
             :rules="userRules">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="userQuery.loginAct"/>
      </el-form-item>
      <el-form-item label="密码" v-if="userQuery.id > 0">
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>
      <el-form-item label="密码" prop="loginPwd" v-else>
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="userQuery.name"/>
      </el-form-item>
      <el-form-item label="手机" prop="phone">
        <el-input v-model="userQuery.phone"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userQuery.email"/>
      </el-form-item>
      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="userQuery.accountNoExpired" placeholder="是/否">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="userQuery.credentialsNoExpired" placeholder="是/否">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="userQuery.accountNoLocked" placeholder="是/否">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="账号是否启用" prop="accountEnabled">
        <el-select v-model="userQuery.accountEnabled" placeholder="是/否">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="userSubmit">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.el-table {
  margin-top: 8px;
}

.el-pagination {
  margin-top: 10px;
}
</style>