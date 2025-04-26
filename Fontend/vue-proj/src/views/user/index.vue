<template>
  <div class="container">

    <div class="list">
      <el-table
        :data="records"
        class="list-table"
        style="width: 100%;"
      >
        <el-table-column
          prop="name"
          label="姓名"
          width="180"
        />
        <el-table-column
          prop="username"
          label="账户"
          width="180"
        />
        <el-table-column
            prop="password"
            label="密码">
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button 
                 type="text" 
                 class="delete-btn" 
                 icon="el-icon-delete"
                 @click="deleteUser(scope.row)">删除</el-button>  
            </template>
        </el-table-column>
        </el-table>
        <div class="nav">
      <el-button class="register-btn" round @click="userRegister">
        <span class="btn-text">注册</span>
      </el-button>
    </div>
    </div>
    <div class="block">
      <el-pagination
        :current-page="page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts">
import{getUserList,deleteUserById} from '@/api/user'
export default {

    // created(){
    //     this.pageQuery()
    // },

    data(){
        return{
            page :1,
            pageSize :10,
            total :0,
            records :[
                // {
                //     id: 1,
                //     name: 'John Doe',
                //     username: 'john_doe',
                //     password: 'password123'
                // },
                // {
                //     id: 2,
                //     name: 'Jane Smith',
                //     username: 'jane_smith',
                //     password: 'securepass'
                // },
                // {
                //     id: 3,
                //     name: 'Alice Johnson',
                //     username: 'alice_johnson',
                //     password:'letmein'
                // },
                // {
                //     id: 4,
                //     name: 'Bob Brown',
                //     username: 'bob_brown',
                //     password:'qwerty'
                // },
                // {
                //     id: 5,
                //     name: 'Charlie Davis',
                //     username: 'charlie_davis',
                //     password:'password123'
                // },
                // {
                //     id: 6,
                //     name: 'Diana Wilson',
                //     username: 'diana_wilson',
                //     password:'securepass' 
                // },
                // { 
                //     id: 7,
                //     name: 'Ethan Martinez',
                //     username: 'ethan_martinez',
                //     password:'letmein'
                // },
                // {
                //     id: 8,
                //     name: 'Fiona Garcia',
                //     username: 'fiona_garcia',
                //     password:'qwerty'
                // },
                // {
                //     id: 9,
                //     name: 'George Hernandez',
                //     username: 'george_hernandez',
                //     password:'password123'  
                // },
                // {
                //     id: 10,
                //     name: 'Hannah Lopez',
                //     username: 'hannah_lopez',
                //     password:'securepass'
                // },
                // {
                //     id: 11,
                //     name: 'Isaac Martinez',
                //     username: 'isaac_martinez',
                //     password:'letmein'  
                // },
                // {
                //     id: 12,
                //     name: 'Julia Garcia',
                //     username: 'julia_garcia',
                //     password:'qwerty'
                // }
            ],
        }
    },
    methods: {
        pageQuery() {
            const param = {page :this.page,pageSize :this.pageSize}
            getUserList(param).then(res => {
                if(res.data.code===1){
                    this.total = res.data.data.total
                    this.records = res.data.data.records
                }
            }).catch(err => {
                this.$message.error('请求出错了：',err.message)
            })
        },
        handleSizeChange(val: any){
            this.pageSize = val
            this.pageQuery()
        },
        handleCurrentChange(val: any){
            this.page = val
            this.pageQuery()
        },
        userRegister(){
            this.$router.push({path: 'userManage/add'})
        },
        deleteUser(row, event){
            console.log(row.id)
            deleteUserById(row.id)
            this.pageQuery()
        }     
    }
}
</script>

<style scoped>
/* *{
  border:1px solid #000000;
} */
.container{
  background-color: #6CBBB6;
  height: 90vh;
  overflow: hidden;
}

.register-btn {
  background-color: #A9AFB1 ;
  border-radius: 10px 10px 10px 10px !important;
  border:0px;
  box-shadow: none;
  width: 65px;
  height: 40px; 
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.btn-text{
  color: #FFFFFF;
  font-weight:bolder; 
  font-size: 20px;
  /* margin-left:-10px; */
}

.list{
  box-sizing: border-box;;
  margin-left:80px;
  margin-top: 50px;
  /* min-height:285px; */
  height:500px;
  width:1200px;
  background-color: #BCBCBC;
  /* padding-top:50px; */
  display: flex;
  flex-direction: column;
  /* position:relative; */
  padding:20px;
  gap:10px;
  border: 4px solid #A9AFB1!important;
  border-radius: 10px 10px 10px 10px!important;
}

.list-table{
  background-color: #FFFFFF; 
  border-radius: 10px 10px 10px 10px!important;
  overflow:auto;
  border-bottom: none!important;
  border-color: transparent!important;
}

.delete-btn{
  color:#6CBBB6;
}

.block{
  margin-top:10px;
}

/* 修改前后箭头颜色 */
:deep .el-pagination .btn-prev,
:deep .el-pagination .btn-next {
  color:#FFFFFF;
  background-color: #BCBCBC;
  border-radius: 10px;
  margin:0 3px;
}

/* 修改页码颜色 */
::v-deep .el-pagination .el-pager li{
  color:#FFFFFF;
  background-color: #BCBCBC;
  border-radius: 10px;
  margin:0 3px;
  display: flex;
  align-items: center;
  justify-content: center;
}

::v-deep .el-pagination .el-pager li.active {
  background-color: #A9AFB1; /* 选中页码字体颜色（通常设为白色，因为背景色较深） */
}

::v-deep .el-pagination .el-select .el-input__inner,
::v-deep .el-pagination .el-input__inner {
  border:1px solid #A9AFB1;
  color:#A9AFB1;
}
</style>