<template>
  <div class="addBrand-container">
    <HeadLable
      title="添加用户"
      :goback="true"
    />
    <div class="container">
      <el-form
        :inline="false"
        label-width="180px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="账号:"
          prop="username"
        >
          <el-input
            v-model="username"
            placeholder="请输入账号"
            maxlength="20"
          />
        </el-form-item>
        <el-form-item
          label="姓名:"
          prop="name"
        >
          <el-input
            v-model="name"
            placeholder="请姓名"
            maxlength="12"
          />
        </el-form-item>
        <el-form-item
          label="密码:"
          prop="password"
        >
          <el-input
            v-model="password"
            type="password"
            autocomplete="off"
            placeholder="请输入密码"
          />
        </el-form-item>  
      </el-form>
      <el-button
        type="primary"
        @click="submitForm()"
      >
        添加
      </el-button>
    </div>
  </div>
</template>
  
<script lang="ts">
    import {addUser} from '@/api/user'
    export default {
    data(){
        return{
            username: '',
            password: '',
            name: ''
        }
    },

    created() {
    },
    methods: {
        submitForm(){
            const param = {username: this.username,name: this.name, password: this.password,type: 0}
            addUser(param).then(res => {
                if(res.data.code === 1){
                    this.$message.success('员工添加成功！')
                }else{
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                this.$message.error(err.data.msg)
            })
        }
    }
}
  </script>
  
  <style lang="scss" scoped>
  .addBrand {
    &-container {
      margin: 30px;
      margin-top: 0px;
      .container {
        position: relative;
        z-index: 1;
        background: #fff;
        padding: 30px;
        border-radius: 4px;
      }
      .el-form-item {
        margin-bottom: 29px;
      }
      .el-input {
        width: 293px;
      }
    }
  }
  </style>