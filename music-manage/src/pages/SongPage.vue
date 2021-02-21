<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets"></i>歌曲信息
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌曲</el-button>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column label="歌曲图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="song-img">
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                    </div>
                   <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
		    :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手-歌名" width="120" align="center"></el-table-column>
            <el-table-column prop="introduction" label="专辑" width="150" align="center"></el-table-column>
            <el-table-column label="歌词" align="center">
                <template slot-scope="scope">
                  <ul style="height:100px;overflow:scroll;">
                    <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                      {{item}}
                    </li>
                  </ul>
                   
                </template>
            </el-table-column>

            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button> 
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

        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
                <div>
                    <label>歌名</label>
                    <el-input type="text" name="name"></el-input>
                </div>
                <div>
                    <label>专辑</label>
                    <el-input type="text" name="introduction"></el-input>
                </div>
                <div>
                    <label>歌词</label>
                    <el-input type="textarea" name="lyric"></el-input>
                </div>
                <div>
                    <label>歌曲上传</label>
                    <input type="file" name="file">
                </div>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addSong">确定</el-button>                
            </span>
        </el-dialog>

    <!-- 修改歌手时弹出窗口 visible表示是否可见 -->
    <el-dialog
      title="编辑歌曲"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="name" label="歌手-歌名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌手-歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input
            v-model="editForm.introduction"
            placeholder="专辑"
          ></el-input>
        </el-form-item>
          <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input
            v-model="editForm.lyric"
            placeholder="歌词"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editVisible = false">取消</el-button>
        <el-button size="mini" @click="editSave">确定</el-button>
      </span>
    </el-dialog>

    <!-- 删除歌手时弹出窗口 visible表示是否可见 -->
    <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
      <div aligen="center">删除歌曲后不可恢复，是否确认删除？</div>
      <span slot="footer">
        <el-button size="mini" @click="delVisible = false">取消</el-button>
        <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from '../mixins/index';

import { songOfSingerId,updateSong,deleteSong} from '../api/index';

export default {
  mixins: [mixin],
  data() {
    return {
      singerId: '',
      singerName: '',
      centerDialogVisible: false, //添加歌手弹窗的显示状态
      editVisible: false, //编辑歌手弹窗的显示状态
      delVisible: false, //删除歌手弹窗的显示状态
      registerForm: {
        //添加框
        name: '',
        singerName: '',
        introduction: '',
        lyric: ''
      },
      editForm: {
        //编辑框
        id: '',
        name: '',

        introduction: '',
        lyric: ''
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5,
      currentPage: 1,
      idx: -1,
      multipleSelection: [],
    }
  },
  computed: {
    // 计算当前索索结果表里的数据
    data() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize,this.currentPage * this.pageSize)
   
    }
  },
  watch: {
    //    搜索框里面的内容发生变化的时候，搜索结果table列表也跟着发生编号
    select_word: function () {
      if (this.select_word == '') {
        this.tableData = this.tempData;
      } else {
        this.tableData = [];
        for (let item of this.tempData) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item);
          }
        }
      }
    }
  },
  created() {
      this.singerId=this.$route.query.id;
       this.singerName=this.$route.query.name;
    this.getData();
  },

  methods: {
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    //查询所有歌手
    getData() {
      this.tempData = [];
      this.tableData = [];
      songOfSingerId( this.singerId).then(res => {
        this.tempData = res;
        this.tableData = res;
        this.currentPage=1;
      })
    },
    addSong() {
      let _this=this;
      var form= new FormData(document.getElementById('tf'));
      form.append('singerId',this.singerId);
      form.set('name',this.singerName+'-'+form.get('name'));
      if(!form.get('lyric')){
          form.set('lyric','[00:00:00]暂无歌词');
      }
      var req = new XMLHttpRequest();
      req.onreadystatechange = function(){
        //  req.readyState == 4 获取到返回的完整数据
	//   req.status == 200 和后台正常交互完成
          if(req.readyState == 4 && req.status==200){
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
      req.open('post',`${_this.$store.state.HOST}/song/add`,false);
      req.send(form);
      _this.centerDialogVisible = false;
    },
    handleEdit(row) {
      this.editVisible = true;
      this.editForm = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        lyric: row.lyric
      };
    },

    //编辑保存歌手信息
    editSave() {
      
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("name", this.editForm.name);
      params.append("introduction", this.editForm.introduction);
      params.append("lyric", this.editForm.lyric);
      updateSong(params)
        .then((res) => {
          if (res.code == 1) {
            this.getData();
            this.notify("修改成功", "success");
          } else {
            this.notify("修改失败", "error");
          }
        })
        .catch((err) => {
          console.log(err);
        });
      this.editVisible = false;
    },
    //更新图片
    uploadUrl(id) {
      // return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`;
    },

    // 删除某个歌手
    deleteRow() {
      deleteSong(this.idx)
        .then((res) => {
          if (res) {
            this.getData();
            this.notify("删除成功", "success");
          } else {
            this.notify("删除失败", "error");
          }
        })
        .catch((err) => {
          console.log(err);
        });
      this.delVisible = false;
    },

    parseLyric(text){
      let lines=text.split("\n");
      let pattern= /\[\d{2}:\d{2}.(d{3}|\d{2})\]/g;
      let result=[];
      for (let item of lines){
        let value=item.replace(pattern,'');
        result.push(value);
      }
      return result;
    }
    // songEdit(id, name) {
    //   this.$router.push({ path: `/Song`, query: { id, name } });
    // },
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.song-img {
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
</style>