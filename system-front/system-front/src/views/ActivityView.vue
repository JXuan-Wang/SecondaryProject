<script>
import {myAxios} from "@/http/httpRequest.js";

export default {
  name: "ActivityView",
  data() {
    return {
      // 市场活动搜索表单对象
      activityQuery: {},
      // 市场列表活动对象
      activityList: [{}],
      // 分页时每页显示多少条数据
      pageSize: 0,
      // 分页总共查询多少条数据
      total: 0,
      // 负责人名单（下拉列表）
      ownerOptions:[],
      // 定义市场活动搜索表单验证规则
      activityRules: {
        cost: [
          {pattern: /^\d+(\.\d{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur'}
        ]
      }
    }
  },
  mounted(){
    this.getData(1);
  },

  methods: {
    handleSelectionChange(){

    },

    // 查询用户列表数据
    getData(current) {
      let startTime = '';
      let endTime = '';
      for(let key in this.activityQuery.activityTime){
        if(key === '0'){
          startTime =  this.activityQuery.activityTime[key];
        }
        if(key === '1'){
          endTime =  this.activityQuery.activityTime[key];
        }
      }
      myAxios.get("/api/activities", {
        params: {
          current: current, // 当前查询第几页
          ownerId: this.activityQuery.ownerId,
          name: this.activityQuery.name,
          startTime: startTime,
          endTime: endTime,
          cost: this.activityQuery.cost,
          createTime: this.activityQuery.createTime,

        }
      }).then(resp => {
        if (resp.data.code === 200) {
          this.activityList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // 加载负责人
    loadOwner(){
      myAxios.get("/api/owner").then(resp=>{
        if (resp.data.code === 200) {
          this.ownerOptions = resp.data.data
        }
      })
    },

    // 搜索
    onSearch(){
      this.getData(1);
    },

    // 录入市场活动
    add(){
      this.$router.push("/dashboard/activity/add")
    },

    // 重置搜索条件
    onReset(){
      this.activityQuery = {};
    },

    // 编辑活动详情
    edit(id){
      this.$router.push("/dashboard/activity/edit/"+id);
    },

    // 活动详情页
    view(id) {
      this.$router.push("/dashboard/activity/"+id);
    }
  },
}
</script>

<template>
  <el-form :inline="true" :model="activityQuery" :rules="activityRules">
    <el-form-item label="负责人">
      <el-select
          style="width: 200px"
          v-model="activityQuery.ownerId"
          placeholder="请选择负责人"
          clearable
          @click="loadOwner"
          width="200px"
      >
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>
    <el-form-item label="活动名称">
      <el-input v-model="activityQuery.name" placeholder="请输入活动名称" clearable />
    </el-form-item>
    <el-form-item label="活动时间">
      <el-date-picker
          v-model="activityQuery.activityTime"
          type="datetimerange"
          range-separator="到"
          value-format="YYYY-MM-DD HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
      />
    </el-form-item>
    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost" placeholder="请输入活动预算" clearable />
    </el-form-item>
    <el-form-item label="创建时间">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择创建时间"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSearch">搜索</el-button>
      <el-button type="primary" plain @click="onReset">重置</el-button>
    </el-form-item>
  </el-form>
  <el-button type="primary" @click="add">录入市场活动</el-button>
  <el-button type="danger" @click="">批量删除</el-button>
  <el-table
      :data="activityList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerName" label="负责人" width="100"/>
    <el-table-column property="name" label="活动名称" width="80" show-overflow-tooltip/>
    <el-table-column property="startTime" label="开始时间" width="100" show-overflow-tooltip/>
    <el-table-column property="endTime" label="结束时间" show-overflow-tooltip width="100"/>
    <el-table-column property="cost" label="活动预算" show-overflow-tooltip width="100"/>
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip width="100"/>
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
</template>

<style scoped>

</style>