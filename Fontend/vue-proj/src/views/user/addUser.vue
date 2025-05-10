<template>
  <div class="addBrand-container">
    <HeadLable
      title="添加用户"
      :goback="true"
    />
    <div class="container">
      <el-form
        :inline="false"
        label-width=auto
        class="ruleForm"
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
        class="add-btn"
        @click="submitForm()"
      >
        <span class="inline-txt">添加</span>
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
  // *{
  //   border:1px solid #000000;
  // }
  .addBrand-container {
    background-color: #6CBBB6;
    height:90vh;
    padding:50px;
    overflow: hidden;
    box-sizing: border-box;
  }

  .container{
    background-color: #BCBCBC;
    border:4px solid #A9AFB1;
    border-radius: 10px;
    padding:20px;
    margin-left:30px;
    display: flex;
    flex-direction: column;
    gap:30px;
  }

  .ruleForm{
    background-color: #FFFFFF;
    border-radius: 10px;
    padding: 30px;
    display: flex;
    flex-direction: column;
    justify-self: start;
  }

  :deep(.el-form-item__label) {
  font-size: 20px;
  font-weight: bolder;
  color: #A9AFB1;
  }

  :deep(.el-input__inner) {
  font-size: 20px;
  font-weight: bolder;
  color: #A9AFB1;
  }

  .add-btn{
    width: 100px;
    height: 40px;
    background-color: #A9AFB1;
    border-radius: 10px;
    border:0;
    margin-left: 550px;
  }

  .inline-txt{
    font-size: 20px;
    font-weight: bolder;
    color: #FFFFFF;
  }
  // :deep(.el-input__inner::placeholder) {
  // color: #A9AFB1;
  // font-size: 20px;
  // font-weight: bold;
  // }
  // .addBrand {
  //   &-container {
  //     margin: 30px;
  //     margin-top: 0px;
  //     .container {
  //       position: relative;
  //       z-index: 1;
  //       background: #fff;
  //       padding: 30px;
  //       border-radius: 4px;
  //     }
  //     .el-form-item {
  //       margin-bottom: 29px;
  //     }
  //     .el-input {
  //       width: 293px;
  //     }
  //   }
  // }
  </style>