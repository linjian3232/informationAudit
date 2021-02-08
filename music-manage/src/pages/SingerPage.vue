<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入歌手名" class="handle-input" size="mini"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌手</el-button>
                
            </div>
        </div>
        <el-table size="mini" border style="width:100%" height="680px" :data="data">
            <el-table-column label="歌手图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="singer-img">
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>  
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
		    :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新头像</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手" width="120" align="center"></el-table-column>
            <el-table-column label="性别" width="50" align="center">
                <template slot-scope="scope">
                    {{ changeGender(scope.row.gender) }}
                </template>
            </el-table-column>
            <el-table-column label="生日" width="120" align="center">
                 <template slot-scope="scope">
                    {{ attachBirth(scope.row.birth) }}
                </template>
            </el-table-column>
            <el-table-column prop="location" label="地区" width="100" align="center"></el-table-column>
            <el-table-column label="简介">
                <template slot-scope="scope">
                <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
	     background
	      layout="total,prev,pager,next"
	       :current-page="currentPage"
	        :page-size="pageSize"
		 :total="tableData.length"></el-pagination>
        </div>

        <!-- 弹出窗口 visible表示是否可见 -->     
        <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>           
            <el-form :model="registerForm" ref="registerForm" label-width="80px">
                <el-form-item prop="name" label="歌手名" size="mini">
                    <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="registerForm.gender">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">不明</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birth" label="生日" size="mini">
                    <el-date-picker type="date" v-model="registerForm.birth" placeholder="选择日期" style="width:100%"></el-date-picker>
                </el-form-item>
                 <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="registerForm.location" placeholder="地区"></el-input>
                </el-form-item>
                 <el-form-item prop="introduction" label="简介" size="mini">
                    <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addSinger">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {setSinger,getAllSinger} from '../api/index';
import {mixin} from '../mixins/index';
export default {
    mixins:[mixin],
    data(){
        return{
            centerDialogVisible:false,
            registerForm:{ //添加框
                name:'',
                gender:'',
                birth: '',
                location: '',
                introduction: ''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 5,
            currentPage: 1
        
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
        this.getData();
    },

    methods:{
        //查询所有歌手
        getData(){
            this.tempData = [];
            this.tableData = [];
            getAllSinger().then(res => {
                this.tempData=res;
                this.tableData=res;
            })
        },
        addSinger(){
            let d=this.registerForm.birth;
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
            let params=new URLSearchParams();
            params.append('name',this.registerForm.name);
            params.append('gender',this.registerForm.gender);
            params.append('pic','img/singerPic/default.jpg');
            params.append('birth',datetime);
            params.append('location',this.registerForm.location);
            params.append('introduction',this.registerForm.introduction);

            setSinger(params)
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
            })
            this.centerDialogVisible=false;
        },
        //更新图片
        uploadUrl(id){
            return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
        }
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .singer-img{
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