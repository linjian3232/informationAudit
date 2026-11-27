<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets"></i>发布文件信息
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入发布文件名" class="handle-input"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加发布文件</el-button>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="name" label="上传者-文件名" width="120" align="center"></el-table-column>
            <!-- <el-table-column prop="introduction" label="发布文件原因简述" width="250" align="center"></el-table-column> -->
            <el-table-column label="发布文件原因简述">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll;overflow-y: auto;overflow-x: auto">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column label="发布预期日期" align="center" width="150">
                <template slot-scope="scope">
                    <div >
                        <p>开始：{{attachBirth(scope.row.beginDate)}}</p>
                    </div>
                    <div >
                        <p>结束：{{attachBirth(scope.row.endDate)}}</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="每日发布时间段" align="center" width="150">
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
             <el-table-column label="一级审核反馈">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll;overflow-y: auto;overflow-x: auto">{{scope.row.firstReason}}</p>
                </template>
            </el-table-column>
             <el-table-column label="二级审核反馈">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll;overflow-y: auto;overflow-x: auto">{{scope.row.secondReason}}</p>
                </template>
            </el-table-column>
            <el-table-column label="资源更新" align="center" width="150">
                <template slot-scope="scope">
                    <el-upload :action="uploadPublicFileUrl(scope.row.id)" :before-upload="beforePublicFileUpload" 
                        :on-success="handleUploadSuccess" :on-error="handleUploadError" >
                        <el-button  class="update_button" size="mini">更新发布文件</el-button>
                    </el-upload>
                </template>
            </el-table-column>

            <el-table-column label="操作"  align="center" >
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)" class= "edit_button">编辑</el-button>
                    <el-button size="mini" @click="download(scope.row.url,scope.row.name)" class= "edit_button">下载</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row)" class="delete_button">删除</el-button>
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
            <el-form ref="uploadForm" label-width="120px">
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
                <el-button size="mini" :loading="uploading" @click="addPublicFile">确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改发布文件信息" :visible.sync="editVisible" width="400px" center>
            <el-form :model="form" ref="form" label-width="120px">
                <el-form-item prop="name" label="上传者-文件名" size="mini">
                    <el-input v-model="form.name" placeholder="上传者-文件名"></el-input>
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
import fileList from '../mixins/fileList'

export default {
  mixins: [fileList]
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
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