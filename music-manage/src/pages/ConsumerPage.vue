<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" placeholder="请输入歌手名" class="handle-input" size="mini"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加用户</el-button>
                
            </div>
        </div>
        <el-table size="mini" reg="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" widt="40"></el-table-column>
            <el-table-column label="用户图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="consumer-img">
                        <img :src="getUrl(scope.row.avator)" style="width:100%"/>  
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
		    :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新头像</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="120" align="center"></el-table-column>
            <el-table-column label="性别" width="50" align="center">
                <template slot-scope="scope">
                    {{ changeGender(scope.row.gender) }}
                </template>
            </el-table-column>
            <el-table-column prop="phoneNumber" label="手机号" width="120" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="240" align="center"></el-table-column>

            <el-table-column label="生日" width="120" align="center">
                 <template slot-scope="scope">
                    {{ attachBirth(scope.row.birth) }}
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="签名" align="center"></el-table-column>
            <el-table-column prop="location" label="地区" width="100" align="center"></el-table-column>
            <el-table-column label="操作" width="150" align="center">
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

        <!-- 添加歌手时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="添加新用户" :visible.sync="centerDialogVisible" width="400px" center>           
            <el-form :model="registerForm" ref="registerForm" label-width="80px">
                <el-form-item prop="username" label="用户名" size="mini">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码" size="mini">
                    <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="registerForm.gender">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="phoneNumber" label="手机号" size="mini">
                    <el-input v-model="registerForm.phoneNumber" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="用户名" size="mini">
                    <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="birth" label="生日" size="mini">
                    <el-date-picker type="date" v-model="registerForm.birth" placeholder="选择日期" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" label="签名" size="mini">
                    <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                </el-form-item>
                 <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="registerForm.location" placeholder="地区"></el-input>
                </el-form-item>
                 
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addConsumer">确定</el-button>
            </span>
        </el-dialog>

        <!-- 修改歌手时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="修改用户" :visible.sync="editVisible" width="400px" center>           
            <el-form :model="editForm" ref="editForm" label-width="80px">
                 <el-form-item prop="username" label="用户名" size="mini">
                    <el-input v-model="editForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码" size="mini">
                    <el-input type="password" v-model="editForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="editForm.gender">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="phoneNumber" label="手机号" size="mini">
                    <el-input v-model="editForm.phoneNumber" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="用户名" size="mini">
                    <el-input v-model="editForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="birth" label="生日" size="mini">
                    <el-date-picker type="date" v-model="editForm.birth" placeholder="选择日期" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" label="签名" size="mini">
                    <el-input v-model="editForm.introduction" placeholder="签名"></el-input>
                </el-form-item>
                 <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="editForm.location" placeholder="地区"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
            </span>
        </el-dialog>

        <!-- 删除歌手时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="删除用户" :visible.sync="delVisible" width="300px" center>           
            <div aligen="center">删除歌手后不可恢复，是否确认删除？</div>
            <span slot="footer">
                <el-button size="mini" @click=" delVisible = false">取消</el-button>
                <el-button size="mini" @click="deleteRow">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {getAllConsumer,setConsumer,updateConsumer,deleteConsumer} from '../api/index';
import {mixin} from '../mixins/index';
export default {
    mixins:[mixin],
    data(){
        return{
            centerDialogVisible:false,//添加歌手弹窗的显示状态
            editVisible:false,//编辑歌手弹窗的显示状态
            delVisible:false,//删除歌手弹窗的显示状态
            registerForm:{ //添加框
                username:'',
                password:'',
                gender:'',
                phoneNumber:'',
                email:'',
                birth: '',
                introduction: '',
                location: ''
                
            },
            editForm:{ //编辑框
                id: '',
                username:'',
                password:'',
                gender:'',
                phoneNumber:'',
                email:'',
                birth: '',
                introduction: '',
                location: ''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 5,
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
                   if(item.username.includes(this.select_word)){
                       this.tableData.push(item);
                   }
               }
           }
       }
   },
    created(){
        this.getData();
    },

    methods:{
        handleCurrentChange(val){
            this.currentPage=val;
        },
        //查询所有用户
        getData(){
            this.tempData = [];
            this.tableData = [];
            getAllConsumer().then(res => {
                this.tempData=res;
                this.tableData=res;
                this.currentPage=1;
            })
        },

        //添加用户
        addConsumer(){
            let d=this.registerForm.birth;
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
            let params=new URLSearchParams();
            params.append('username',this.registerForm.name);
            params.append('password',this.registerForm.password);
            params.append('gender',this.registerForm.gender);
            params.append('phoneNumber',this.registerForm.phoneNumber);
            params.append('email',this.registerForm.email);
            params.append('birth',datetime);
            params.append('introduction',this.registerForm.introduction);
            params.append('location',this.registerForm.location);
            params.append('avator','/avatorImg/default.jpg');

            setConsumer(params)
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
                username:row.username,
                password:row.password,
                gender:row.gender,
                phoneNumber:row.phoneNumber,
                email:row.email,
                birth: row.birth,
                introduction:row.introduction,
                location: row.location
            }
        },

        //编辑保存歌手信息
        editSave(){
            let d= new Date(this.editForm.birth);
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
            let params=new URLSearchParams();
             params.append('username',this.editForm.name);
            params.append('password',this.editForm.password);
            params.append('gender',this.editForm.gender);
            params.append('phoneNumber',this.editForm.phoneNumber);
            params.append('email',this.editForm.email);
            params.append('birth',datetime);
            params.append('introduction',this.editForm.introduction);
            params.append('location',this.editForm.location);
            updateConsumer(params)
            .then(res =>{
                if(res.code == 1)              {
	    	        this.getData();	
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            })
            this.editVisible=false;
        },
        //更新图片
        uploadUrl(id){
            return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`
        },

        // 删除某个歌手
        deleteRow(){
             deleteConsumer(this.idx)
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
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .consumer-img{
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