<template>
    <div class="table">
         <div class="crumbs">
            超级管理者界面
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" placeholder="请输入上传者名" class="handle-input" size="mini"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加管理员</el-button>
            <div class="scan-box">
                 <el-button type="primary" size="mini" @click="getData">查看全部</el-button>
                <el-button type="primary" size="mini" @click="getDataOfLevel(1)">查看上传者</el-button>
                <el-button type="primary" size="mini" @click="getDataOfLevel(2)">查看一级审核者</el-button>
                <el-button type="primary" size="mini" @click="getDataOfLevel(3)">查看二级审核者</el-button>
            </div>
            </div>
        </div>
        <el-table size="mini" reg="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" widt="40"></el-table-column>
            <el-table-column prop="name" label="用户名" width="400" align="center"></el-table-column>
            <el-table-column prop="password" label="密码" width="400" align="center"></el-table-column>
            <el-table-column label="权限等级" width="250" align="center">
                <template slot-scope="scope">
                    {{ changeLevel(scope.row.privilegeLevel) }}
                </template>
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
                    <el-button class="edit_button" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button class="delete_button" size="mini" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
	     background
	      layout="total,prev,pager,next"
	        :current-page="currentPage"
	        :page-size="pageSize"
		    :total="tableData.length"
            @current-change="handleCurrentChange"></el-pagination>
        </div>

        <!-- 添加上传者时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="添加管理员" :visible.sync="centerDialogVisible" width="600px" center>           
            <el-form :model="registerForm" ref="registerForm" label-width="100px">
                <el-form-item prop="name" label="管理员用户名" size="mini">
                    <el-input v-model="registerForm.name" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="管理员密码" size="mini">
                    <el-input v-model="registerForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="权限级别" size="mini">
                    <el-radio-group v-model="registerForm.privilegeLevel">
                        <el-radio :label="1">上传者</el-radio>
                        <el-radio :label="2">一级审核者</el-radio>
                        <el-radio :label="3">二级审核者</el-radio>
                        <el-radio :label="4">超级管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="beforeAddAdmin(registerForm.name)">确定</el-button>
            </span>
        </el-dialog>

        <!-- 修改上传者时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="编辑上传者" :visible.sync="editVisible" width="600px" center>           
            <el-form :model="editForm" ref="editForm" label-width="100px">
               <el-form-item prop="name" label="管理员用户名" size="mini">
                    <el-input v-model="editForm.name" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="管理员密码" size="mini">
                    <el-input v-model="editForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="权限级别" size="mini">
                    <el-radio-group v-model="editForm.privilegeLevel">
                        <el-radio :label="1">上传者</el-radio>
                        <el-radio :label="2">一级审核者</el-radio>
                        <el-radio :label="3">二级审核者</el-radio>
                        <el-radio :label="4">超级管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
            </span>
        </el-dialog>

        <!-- 删除上传者时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="删除上传者" :visible.sync="delVisible" width="300px" center>           
            <div aligen="center">删除上传者后不可恢复，是否确认删除？</div>
            <span slot="footer">
                <el-button size="mini" @click=" delVisible = false">取消</el-button>
                <el-button size="mini" @click="deleteRow">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {setUploader,getAllUploader,updateUploader,deleteUploader,uploaderOfStudyNumber,getUserOfLevel,getAllAdmin,adminOfName,addAdmin,updateAdmin,deleteAdmin} from '../api/index';
import {mixin} from '../mixins/index';
export default {
    mixins:[mixin],
    data(){
        return{
            centerDialogVisible:false,//添加上传者弹窗的显示状态
            editVisible:false,//编辑上传者弹窗的显示状态
            delVisible:false,//删除上传者弹窗的显示状态
            username: '',
            userLevel:'',
            registerForm:{ //添加框
                name:'',
                password: '',
                privilegeLevel:''
            },
            editForm:{ //编辑框
                id: '',
                name:'',
                password: '',
                privilegeLevel:''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 15,
            currentPage: 1,
            idx: -1,
            multipleSelection:[]
        
        }
    },
    computed:{
        // 计算当前索索结果表里的数据
        data(){
        return this.tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)  
	}
    },
   watch:{
    //    搜索框里面的内容发生变化的时候，搜索结果table列表也跟着发生编号
       select_word: function(){
           if(this.select_word == ''){
               this.tableData = this.tempData;
           }else{
               this.tableData=[];
               for(let item of this.tempData){
                   if(item.name.includes(this.select_word)){
                       this.tableData.push(item);
                   }
               }
           }
       }
   },
    created(){
        this.username =  localStorage.getItem('userName');
        this.userLevel = localStorage.getItem('level');
        console.log("created:"+this.username+"   level:"+this.userLevel)
        this.getData();
    },

    methods:{
        handleCurrentChange(val){
            this.currentPage=val;
        },
        getData(){
            this.tempData = [];
            this.tableData = [];
            getAllAdmin().then(res => {
                this.tempData=res;
                this.tableData=res;
                this.currentPage=1;
            })
        },
        //查询所有上传者
        getDataOfLevel(flag){
            
            this.tempData = [];
            this.tableData = [];
            getUserOfLevel(flag).then(res => {
                this.tempData=res;
                this.tableData=res;
                this.currentPage=1;
            })
            console.log("登录者"+this.username);
        },

        beforeAddAdmin(name){
            adminOfName(name).then(res =>{
                if(res.code==1){
                    this.notify(res.msg,"error");
                }
                else{
                    this.addNewAdmin();
                }
            })
        },
        addNewAdmin(){
    
            let params=new URLSearchParams();
            params.append('name',this.registerForm.name);
            params.append('password',this.registerForm.password);
            params.append('privilegeLevel',this.registerForm.privilegeLevel);
      
            addAdmin(params)
            .then(res =>{
                if(res.code == 1)              {
	    	        this.getData();	
                    this.notify("添加成功","success");
                }else{
                    this.notify("添加失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.centerDialogVisible=false;
        },
        handleEdit(row){
            this.editVisible=true;
            this.editForm = {
                id: row.id,
                name:row.name,
                password:row.password,
                privilegeLevel:row.privilegeLevel,
            }
        
        },

        //编辑保存上传者信息
        editSave(){
            let params=new URLSearchParams();
            params.append('id' , this.editForm.id);
            params.append('name',this.editForm.name);
            params.append('password',this.editForm.password);
            params.append('privilegeLevel',this.editForm.privilegeLevel);
            updateAdmin(params)
            .then(res =>{
                if(res.code == 1)              {
	    	        this.getData();	
                    this.notify("修改成功","success");
                }else{
                    this.notify(res.msg,"error");
                }
            })
            .catch(err => {
                console.log(err);
            })
            this.editVisible=false;
        },
        // 删除某个上传者
        deleteRow(){
             deleteAdmin(this.idx)
            .then(res =>{
                if(res)              {
	    	        this.getData();	
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            })
            this.delVisible=false;
        },
        songEdit(id,studyNumber,name)
        {
            console.log(studyNumber+"    "+this.username);
            if(this.username==studyNumber||this.userLevel=='3'){
            this.$router.push({path:`/PersonalFile`,query:{id,name}});
            this.notify("文件管理页面","success");

            }
            else{
                 this.notify("权限不足","error");
            }
        }
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .scan-box{
        margin-bottom: 20px;
        float:right;
        padding-right: 50px;
    }
    .uploader-img{
        width:100%;
        height: 80px;
        /* 添加圆弧 */
        border-radius: 5px;

        margin-bottom: 5px;
        overflow: hidden;
    }
    .handle-input{
        width:300px;
        display: inline-block;
    }
    .pagination{
        display: flex;
        justify-content: center;
    }

</style>