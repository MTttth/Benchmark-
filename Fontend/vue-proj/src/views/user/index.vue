<template>
  <div class="container">
    <div class="nav">
        <el-button type="primary" @click="userRegister" round>注册</el-button>
    </div>
    <div class="list">
        <el-table
        :data="records"
        style="width: 100%;">
        <el-table-column
            prop="name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="username"
            label="账户"
            width="180">
        </el-table-column>
        <el-table-column
            prop="password"
            label="密码">
        </el-table-column>
        </el-table>
    </div>
    <div class="block">
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
        </el-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import{getUserList} from '@/api/user'
export default {

    // created(){
    //     this.pageQuery()
    // },

    data(){
        return{
            page :1,
            pageSize :10,
            total :0,
            records :[],
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
            this.$router.push({path: 'user/add'})
        }     
    }
}
</script>

<style>

</style>