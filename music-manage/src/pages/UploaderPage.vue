<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" placeholder="请输入上传者名" class="handle-input" size="mini"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加上传者</el-button>
                
            </div>
        </div>
        <el-table size="mini" reg="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" widt="40"></el-table-column>
            <el-table-column label="上传者图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="uploader-img">
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>  
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
		    :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新头像</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="上传者" width="120" align="center"></el-table-column>
            <el-table-column prop="studyNumber" label="学号/工号" width="200" align="center"></el-table-column>
            <el-table-column label="性别" width="150" align="center">
                <template slot-scope="scope">
                    {{ changeGender(scope.row.gender) }}
                </template>
            </el-table-column>
            <el-table-column prop="academy" label="所属学院" width="250" align="center"></el-table-column>
            <el-table-column prop="major" label="所属专业" width="350" align="center"></el-table-column>
            <el-table-column label="文件管理" width="180" align="center">
                <template slot-scope="scope">
                <el-button  size="mini" @click="songEdit(scope.row.id,scope.row.studyNumber,scope.row.name)">文件管理</el-button>
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
        <el-dialog title="添加上传者" :visible.sync="centerDialogVisible" width="400px" center>           
            <el-form :model="registerForm" ref="registerForm" label-width="80px">
                <el-form-item prop="name" label="上传者名" size="mini">
                    <el-input v-model="registerForm.name" placeholder="上传者名"></el-input>
                </el-form-item>
                <el-form-item prop="studyNumber" label="学号/工号" size="mini">
                    <el-input v-model="registerForm.studyNumber" readonly="true" placeholder="学号/工号"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="registerForm.gender">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">不明</el-radio>
                    </el-radio-group>
                </el-form-item>
                 <el-form-item prop="academy" label="所属学院" size="mini">
                    <el-input v-model="registerForm.academy" placeholder="所属学院"></el-input>
                </el-form-item>
                 <el-form-item prop="major" label="所属专业" size="mini">
                    <el-input v-model="registerForm.major" placeholder="所属专业"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addUploader">确定</el-button>
            </span>
        </el-dialog>

        <!-- 修改上传者时弹出窗口 visible表示是否可见 -->     
        <el-dialog title="编辑上传者" :visible.sync="editVisible" width="400px" center>           
            <el-form :model="editForm" ref="editForm" label-width="80px">
                <el-form-item prop="name" label="上传者名" size="mini">
                    <el-input v-model="editForm.name" placeholder="上传者名"></el-input>
                </el-form-item>
                 <el-form-item prop="studyNumber" label="学号/工号" size="mini">
                    <el-input v-model="editForm.studyNumber" placeholder="学号/工号"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="editForm.gender">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">不明</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="academy" label="所属学院" size="mini">
                    <el-input v-model="editForm.academy" placeholder="所属学院"></el-input>
                </el-form-item>

                <el-form-item prop="major" label="所属专业" size="mini">
                    <el-input v-model="editForm.major" placeholder="所属专业"></el-input>
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
import {setUploader,getAllUploader,updateUploader,deleteUploader} from '../api/index';
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
                studyNumber: localStorage.getItem('userName'),
                gender:'',
                academy:'',
                major:''
            },
            editForm:{ //编辑框
                id: '',
                name:'',
                studyNumber:localStorage.getItem('userName'),
                gender:'',
                academy:'',
                major:''
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
        //查询所有上传者
        getData(){
            
            this.tempData = [];
            this.tableData = [];
            getAllUploader().then(res => {
                this.tempData=res;
                this.tableData=res;
                this.currentPage=1;
            })
            console.log("登录者"+this.username);
        },
        addUploader(){

            let params=new URLSearchParams();
            params.append('name',this.registerForm.name);
            params.append('studyNumber',this.registerForm.studyNumber);
            params.append('gender',this.registerForm.gender);
            params.append('academy',this.registerForm.academy);
            params.append('major',this.registerForm.major);
            params.append('pic','/img/uploaderPic/default.jpg');
      

            setUploader(params)
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
            if(this.userLevel!='3'&&this.username!=row.studyNumber){
                this.notify("您无此权限","error");
            }
            else{
            this.editVisible=true;
            this.editForm = {
                id: row.id,
                name:row.name,
                studyNumber:row.studyNumber,
                gender:row.gender,
                academy:row.academy
            }
            }
        },

        //编辑保存上传者信息
        editSave(){
            let d= new Date(this.editForm.birth);
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
            let params=new URLSearchParams();
            params.append('id' , this.editForm.id);
            params.append('name',this.editForm.name);
            params.append('studyNumber',this.editForm.studyNumber);
            params.append('gender',this.editForm.gender);
            params.append('academy',this.editForm.academy);
            params.append('major',this.editForm.major);
            updateUploader(params)
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
            return `${this.$store.state.HOST}/uploader/updateUploaderPic?id=${id}`
        },

        // 删除某个上传者
        deleteRow(){
             deleteUploader(this.idx)
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