<template>
    <div class="table">
        <div class="crumbs">
            发布文件页面
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入发布文件名" class="handle-input"></el-input>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="name" label="上传者-文件名" width="120" align="center"></el-table-column>
            <el-table-column label="发布文件原因简述" align="center">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column label="发布预期日期" align="center" width="250">
                <template slot-scope="scope">
                    <div >
                        <p>开始：{{attachBirth(scope.row.beginDate)}}</p>
                    </div>
                    <div >
                        <p>结束：{{attachBirth(scope.row.endDate)}}</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="日播放时间段" align="center" width="250">
                <template slot-scope="scope">
                    <div >
                        <p>开始：{{scope.row.beginTime}}</p>
                    </div>
                    <div >
                        <p>结束：{{scope.row.endTime}}</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="文件审核状态" width="150" align="center">
                <template slot-scope="scope">
                    {{ statusStyle(scope.row.status) }}
                </template>
            </el-table-column>
            <el-table-column prop="firstReason" label="一级审核反馈" width="150" align="center"></el-table-column>
            <el-table-column prop="secondReason" label="二级审核反馈" width="150" align="center"></el-table-column>
            <el-table-column label="操作" width="250" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="download(scope.row.url,scope.row.name)" class= "edit_button">下载</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)" class="delete_button">删除</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                background
                layout = "total,prev,pager,next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                >
            </el-pagination>
        </div>

        <el-dialog title="添加发布文件" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="120px" action="" id="tf"> 
                <div>
                    <label>文件发布原因</label>
                    <el-input type="text" name="introduction"></el-input>
                </div>
                <div>
                    <label>预期发布开始日</label>
                    <el-input type="date" name="beginDate"></el-input>
                </div>
                <div>
                    <label>预期发布结束日</label>
                    <el-input type="date" name="endDate"></el-input>
                </div>       
                <div>
                    <label>日开始时间</label>
                    <el-input type="text" name="beginTime"></el-input>
                </div>    
                <div>
                    <label>日结束时间</label>
                    <el-input type="text" name="endTime"></el-input>
                </div>        
        
                <div>
                    <label>文件上传</label>
                    <input type="file" name="file">
                </div>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addPublicFile">确定</el-button>                
            </span>
        </el-dialog>

        <el-dialog title="修改发布文件信息" :visible.sync="editVisible" width="400px" center>
            <el-form :model="form" ref="form" label-width="120px">
                <el-form-item prop="name" label="上传者-文件名" size="mini">
                    <el-input v-model="form.name" placeholder="上传者-歌名"></el-input>
                </el-form-item>                
                <el-form-item prop="introduction" label="文件发布原因" size="mini">
                    <el-input v-model="form.introduction" placeholder="文件发布原因"></el-input>
                </el-form-item>  
                 <el-form-item prop="beginDate" label="预期发布开始日" size="mini">
                    <el-input type="date" v-model="form.beginDate" placeholder="选择日期" style="width:100%"></el-input>
                </el-form-item>
                 <el-form-item prop="endDate" label="预期发布结束日" size="mini">
                    <el-date-picker type="date" v-model="form.endDate" placeholder="选择日期" style="width:100%" value-format="yyyy-MM-dd" 
format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item prop="beginTime" label="日开始时间" size="mini">
                    <el-input v-model="form.beginTime" placeholder="开始时间"></el-input>
                </el-form-item> 
                <el-form-item prop="endTime" label="日结束时间" size="mini">
                    <el-input v-model="form.endTime" placeholder="结束时间"></el-input>
                </el-form-item> 
                
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>                
            </span>
        </el-dialog>

        <el-dialog title="删除发布文件" :visible.sync="delVisible" width="300px" center>
            <div align="center">删除不可恢复，是否确定删除？</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">取消</el-button>
                <el-button size="mini" @click="deleteRow">确定</el-button>                
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mixin } from '../mixins/index';
import {mapGetters} from 'vuex';
import '@/assets/js/iconfont.js';
import {publicFileOfUploaderId,updatePublicFile,delPublicFile,download,updatePublicFileStatus,publicFileOfStatus} from '../api/index';


export default {
    mixins: [mixin],
    data(){
        return{
            uploaderId: '',               //上传者id
            uploaderName: '',             //上传者名
            centerDialogVisible: false, //添加弹窗是否显示
            editVisible: false,         //编辑弹窗是否显示
            delVisible: false,          //删除弹窗是否显示
            registerForm:{     
	     //添加框
                introduction: '',
                beginDate: '',
                endDate:'',
                beginTime:'',
                endTime: ''
            },
            form:{      //编辑框
                id: '',
                name: '',
                introduction: '',
                beginDate: '',
                endDate:'',
                beginTime:'',
                endTime: ''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 5,    //分页每页大小
            currentPage: 1,  //当前页
            idx: -1,          //当前选择项
            multipleSelection: [],   //哪些项已经打勾
            toggle: false           //播放器的图标状态
        }
    },
    computed:{
        ...mapGetters([
            'isPlay',
            'url'
        ]),
        //计算当前搜索结果表里的数据
        data(){
            return this.tableData.slice((this.currentPage - 1) * this.pageSize,this.currentPage * this.pageSize)
        }
    },
    watch:{
        //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
        select_word: function(){
            if(this.select_word == ''){
                this.tableData = this.tempData;
            }else{
                this.tableData = [];
                for(let item of this.tempData){
                    if(item.name.includes(this.select_word)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){
        this.uploaderId = this.$route.query.id;
        this.uploaderName = this.$route.query.name;
        this.getData();
    },
    methods:{
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //查询所有上传者
        getData(){
            this.tempData = [];
            this.tableData = [];
            publicFileOfStatus(this.uploaderId,1).then(res => {
                this.tempData = res;
                this.tableData = res;
                this.currentPage = 1;
            })
        },
        //添加上传者
        addPublicFile(){
            let _this = this;
            var form = new FormData(document.getElementById('tf'));
            console.log(form.get('introduction'));
            form.append('uploaderId',this.uploaderId);
            form.append('uploaderName',this.uploaderName);
            
            var req = new XMLHttpRequest();
            req.onreadystatechange = function(){
                //req.readyState == 4 获取到返回的完整数据
                //req.status == 200 和后台正常交互完成
                if(req.readyState == 4 && req.status == 200){
                    let res = JSON.parse(req.response);
                    if(res.code){
                        _this.getData();
                        _this.registerForm = {};
                        _this.notify(res.msg,'success');
                    }else{
                         _this.notify('保存失败','error');
                    }
                }
            }
            req.open('post',`${_this.$store.state.HOST}/publicFile/add`,false);
            req.send(form);
            _this.centerDialogVisible = false;
        },
        //弹出编辑页面
        handleEdit(row){
            this.editVisible = true;
            this.form = {
                id: row.id,
                name: row.name,
                introduction: row.introduction,
                beginDate: row.beginDate,
                endDate: row.endDate,
                beginTime: row.beginTime,
                endTime:row.endTime
            }
        },
        //保存编辑页面修改的数据
        editSave(){
            let params = new URLSearchParams();
            // let begin=attachBirth(this.form.beginDate);
            // console.log(begin);
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('introduction',this.form.introduction);
            params.append('beginDate',this.form.beginDate);
            params.append('endDate',this.form.endDate);
            params.append('beginTime',this.form.beginTime);
            params.append('endTime',this.form.endTime);
            updatePublicFile(params)
            .then(res => {
                if(res.code == 1){
                    this.getData();
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.editVisible = false;
        },
        //删除一名上传者
        deleteRow(){
            delPublicFile(this.idx)
            .then(res => {
                if(res){
                    this.getData();
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.delVisible = false;
        },
        //上传发布文件之前的校验
        beforePublicFileUpload(file){
            const isLt2M = (file.size /1024 /1024) <20;
            if(!isLt2M){
            this.$message.error('上传文件的大小不饿能超过20M');
            return false;
        }
        return true;
        },
        //上传发布文件成功之后要做的工作
        handlePublicFileSuccess(res){
            let _this = this;
            if(res.code == 1){
                _this.getData();
                _this.$notify({
                    title: '上传成功',
                    type: 'success'
                });
            }else{
                _this.$notify({
                    title: '上传失败',
                    type: 'error'
                });
            }
        },
        //更新发布文件url
        uploadPublicFileUrl(id){
            let name=this.uploaderName;
            return `${this.$store.state.HOST}/publicFile/updatePublicFileUrl?id=${id}&&uploaderName=${name}`
        },


        changeStatus(id,status){
            console.log("status:"+status);
            let params = new URLSearchParams();
             params.append('id',id);
             params.append('status',status)
             updateSongStatus(params)
            .then(res => {
                if(res.code == 1){
                    this.getData();
                    this.notify("审核成功","success");
                }else{
                    this.notify("审核失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.editVisible = false;
        },

        //下载音乐
        download(url,name){
            download(url)
            .then(res=>{
                let content =res.data;
                let eleLink = document.createElement('a');
                eleLink.download=name;
                eleLink.style.display='none';
                //把字符内容转黄成blob地址
                let blob = new Blob([content]);
                eleLink.href = URL.createObjectURL(blob);
                // 把链接地址加到docunment里
                document.body.appendChild(eleLink);
                //触发点击
                eleLink.click();
                //然后移除掉这个新加的空间
                document.body.removeChild(eleLink);
            })
            .catch(err=>{
                console.log(err);
            })
        }
    }   
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.publicFile-img {
  width: 100%;
  height: 80px;
  /* 添加圆弧 */
  border-radius: 5px;

  margin-bottom: 5px;
  overflow: hidden;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.pagination {
  display: flex;
  justify-content: center;
}
   
.edit_button {
  color: white;
  background-color: #77ac98;
}
.delete_button {
  color: white;
  background-color: #8f4b2e;
}
.play {
        position: absolute;
        z-index: 100;
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        top: 18px;
        left: 15px;
    }

    .icon {
        width: 2em;
        height: 2em;
        color: white;
        fill: currentColor;
        overflow: hidden;
    }
</style>