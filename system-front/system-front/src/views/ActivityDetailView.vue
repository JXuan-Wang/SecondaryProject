<script>
import {myAxios} from "@/http/httpRequest.js";
import {messageConfirm, messageTip} from "@/util/utils.js";

export default {
  name: "ActivityDetailView",
  inject: ['reload'],
  data() {
    return {
      // 活动详情对象
      activityDetail: {
        ownerDO: {},
        createByDO: {},
        editByDO: {},
      },
      // 活动备注的列表对象
      activityRemarkList: [{
        createByDO: {},
        editByDO: {},
      }],
      // 市场活动备注
      activityRemarkQuery: {},
      activityRemarkRules: {
        noteContent: [
          { required: true, message: '请输入活动备注内容', trigger: 'blur' },
          { min: 5, max:255, message: '活动备注长度为5~255字符', trigger: 'blur' },
        ]
      },
      pageSize: 0,
      total: 0,
      //编辑活动备注的弹窗，true弹，false不弹
      activityRemarkDialogVisible : false,
      editActivityRemarkQuery: {},
      //编辑活动备注的验证规则
      editActivityRemarkRules : {
        noteContent : [
          { required: true, message: '请输入活动备注', trigger: 'blur' },
          { min: 5, max: 255, message: '活动备注长度为5-255个字符', trigger: 'blur' }
        ]
      }
    }
  },
  mounted(){
    this.loadActivityDetail();
    this.getData();
  },
  methods: {
    loadActivityDetail(){
      let id = this.$route.params.id;
      myAxios.get("/api/activity/"+id).then(resp=>{
        if(resp.data.code === 200){
          this.activityDetail = resp.data.data;
          if(!this.activityDetail.ownerDO){
            this.activityDetail.ownerDO = {}
          }
          if(!this.activityDetail.createByDO){
            this.activityDetail.createByDO = {}
          }
          if(!this.activityDetail.editByDO){
            this.activityDetail.editByDO = {}
          }
        }
      })
    },
    activityRemarkSubmit(){
      this.$refs.activityRemarkRefForm.validate(isValid =>{
        console.log(this.activityDetail.id)
        console.log(this.activityRemarkQuery.noteContent)
        if(isValid){
          myAxios.post("/api/activity/remark",{
            activityId: this.activityDetail.id,
            noteContent: this.activityRemarkQuery.noteContent,

          }).then( resp =>{
            if (resp.data.code === 200) {
              messageTip("提交成功", "success")
              this.reload()
            } else {
              messageTip("提交失败", "error")
            }
          })
        }
      })
    },
    // 查询活动备注列表数据
    getData(current) {
      myAxios.get("/api/activity/remark", {
        params: {
          current: current, // 当前查询第几页
          activityId: this.$route.params.id,
        }
      }).then(resp => {
        if (resp.data.code === 200) {
          this.activityRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },
    //编辑备注记录
    edit(id) {
      this.activityRemarkDialogVisible = true;
      myAxios.get("/api/activity/remark/" + id, {}).then(resp => {
        if (resp.data.code === 200) {
          this.editActivityRemarkQuery = resp.data.data;
        }
      })
    },

    //编辑活动备注（提交保存）
    editActivityRemarkSubmit() {
      this.$refs.editActivityRemarkRefForm.validate((isValid) => {
        if (isValid) {
          myAxios.put("/api/activity/remark", {
            activityId : this.editActivityRemarkQuery.id,
            noteContent: this.editActivityRemarkQuery.noteContent
          }).then(resp => {
            console.log(resp)
            if (resp.data.code === 200) {
              messageTip("编辑成功", "success");
              //刷新
              this.reload();
            } else {
              messageTip("编辑失败", "error");
            }
          })
        }
      })
    },
    //删除活动备注
    del(id) {
      messageConfirm("您确定要删除该数据吗？").then(() => { //用户点击“确定”按钮就会触发then函数
        myAxios.delete("/api/activity/remark/" + id).then(resp => {
          console.log(resp);
          if (resp.data.code === 200) {
            messageTip("删除成功", "success");
            //页面刷新
            this.reload();
          } else {
            messageTip("删除失败，原因：" + resp.data.msg, "error");
          }
        })
      }).catch(() => { //用户点击“取消”按钮就会触发catch函数
        messageTip("取消删除", "warning");
      })
    }
  }
}
</script>

<template>
  <el-form ref="activityRemarkRefForm" :model="activityRemarkQuery" label-width="120px" :rules="activityRemarkRules">
    <el-form-item label="ID">
      <div class="desc">{{activityDetail.id}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="负责人">
      <div class="desc"> {{activityDetail.ownerDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动名称">
      <div class="desc">{{activityDetail.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="开始时间">
      <div class="desc">{{activityDetail.startDate}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="结束时间">
      <div class="desc">{{activityDetail.endDate}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动预算">
      <div class="desc">{{activityDetail.cost}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动描述">
      <div class="desc">{{activityDetail.description}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">{{activityDetail.createTime}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">{{activityDetail.createByDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑时间">
      <div class="desc">{{activityDetail.editTime}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑人">
      <div class="desc">{{activityDetail.editByDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写备注" prop="noteContent">
      <el-input
          v-model="activityRemarkQuery.noteContent"
          :rows="8"
          type="textarea"
          placeholder="请输入活动备注"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit">提 交</el-button>
      <el-button @click="this.$router.go(-1)">返 回</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="activityRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="60" show-overflow-tooltip/>
    <el-table-column prop="noteContent" label="备注内容" show-overflow-tooltip/>
    <el-table-column property="createTime" label="备注时间" show-overflow-tooltip/>
    <el-table-column property="createByDO.name" label="备注人" show-overflow-tooltip/>
    <el-table-column property="editTime" label="编辑时间" show-overflow-tooltip/>
    <el-table-column property="editByDO.name" label="编辑人" show-overflow-tooltip/>
    <el-table-column label="操作">
      <template #default="scope">
        <a href="javascript:" @click="edit(scope.row.id)">编辑</a>
        &nbsp;
        <a href="javascript:" @click="del(scope.row.id)">删除</a>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @prev-click="getData"
      @next-click="getData"
      @current-change="getData"/>

  <!--活动备注记录的弹窗-->
  <el-dialog v-model="activityRemarkDialogVisible" title="编辑市场活动备注记录" width="55%" center draggable>
    <el-form ref="editActivityRemarkRefForm" :model="editActivityRemarkQuery" label-width="110px" :rules="editActivityRemarkRules">
      <el-form-item label="活动备注" prop="noteContent">
        <el-input
            v-model="editActivityRemarkQuery.noteContent"
            :rows="8"
            type="textarea"
            placeholder="请输入活动备注"/>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="activityRemarkDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="editActivityRemarkSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.desc {
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}
</style>