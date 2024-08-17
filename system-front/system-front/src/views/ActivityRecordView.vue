<script>
import {myAxios} from "@/http/httpRequest.js";
import {messageTip} from "@/util/utils.js";

export default {
  name: "ActivityRecordView",
  inject:['reload'],
  data() {
    return {
      // 市场活动表单
      activityQuery: {},
      // 录入市场活动表单验证规则
      activityRules: {
        ownerId: [
          { required: true, message: '请选择负责人', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
        ],
        startTime: [
          { required: true, message: '请选择开始时间', trigger: 'blur' },
        ],
        endTime: [
          { required: true, message: '请选择结束时间', trigger: 'blur' },
        ],
        cost: [
          { required: true, message: '请输入活动预算', trigger: 'blur' },
          {pattern: /^\d+(\.\d{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur'}
        ],
        description: [
          { required: true, message: '请输入活动描述', trigger: 'blur' },
          { min: 5, max:255, message: '活动描述长度为5~255字符', trigger: 'blur' },
        ]
      },
      // 负责人的下拉选项
      ownerOptions: [{}],
    }
  },
  mounted() {
    this.loadOwner()
    this.loadActivity()
  },
  methods: {
    // 加载负责人
    loadOwner(){
      myAxios.get("/api/owner").then(resp=>{
        if (resp.data.code === 200) {
          this.ownerOptions = resp.data.data
        }
      })
    },

    // 市场活动提交
    activitySubmit(){
      let formData = new FormData()
      for(let field in this.activityQuery){
        if(this.activityQuery[field]){
          formData.append(field,this.activityQuery[field])
        }
      }
      this.$refs.activityRefForm.validate(isValid => {
        if (isValid) {
          if(this.activityQuery.id > 0){
            myAxios.put("/api/activity", formData).then(resp => {
              if (resp.data.code === 200) {
                messageTip("编辑成功", "success")
                this.$router.go(-1)
              } else {
                messageTip("编辑失败", "error")
              }
            })
          }else{
            myAxios.postForm("/api/activity", formData).then(resp => {
              if (resp.data.code === 200) {
                messageTip("提交成功", "success")
                this.$router.go(-1)
              } else {
                messageTip("提交失败", "error")
              }
            })
          }
        }
      })
    },

    // 加载要编辑的市场活动数据
    loadActivity(){
      let id = this.$route.params.id;
      if (id) {
        myAxios.get("/api/activity/"+id).then(resp=>{
          if(resp.data.code === 200){
            this.activityQuery = resp.data.data;
          }
        })
      }
    },
  }
}
</script>

<template>
  <el-form ref="activityRefForm" :model="activityQuery" label-width="auto" style="max-width: 600px"
           :rules="activityRules">
    <el-form-item label="负责人" prop="ownerId">
      <el-select v-model="activityQuery.ownerId" placeholder="负责人">
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>
    <el-form-item label="活动名称" prop="name">
      <el-input v-model="activityQuery.name" placeholder="请输入活动名称"/>
    </el-form-item>

    <el-form-item label="开始时间" prop="startTime">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择开始时间"/>
    </el-form-item>

    <el-form-item label="结束时间" prop="endTime">
      <el-date-picker
          v-model="activityQuery.endTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择结束时间"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost" placeholder="请输入活动预算"/>
    </el-form-item>

    <el-form-item label="活动描述" prop="description">
      <el-input v-model="activityQuery.description"
      :rows="6"
      type="textarea"
      placeholder="请输入活动描述"/>
    </el-form-item>
    <el-form-item>
      <el-button type="success" @click="activitySubmit">提交</el-button>
      <el-button type="primary" @click="() => this.$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>