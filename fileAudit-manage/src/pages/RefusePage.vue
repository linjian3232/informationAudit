<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets"></i>一级驳回文件
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入发布文件名" class="handle-input"></el-input>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="name" label="上传者-文件名" width="200" align="center"></el-table-column>
            <el-table-column prop="introduction" label="发布文件原因简述" width="250" align="center"></el-table-column>
            <!-- <el-table-column label="发布文件原因简述">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
                </template>
            </el-table-column> -->
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
            <el-table-column label="每日发布时间段" align="center" width="250">
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
            <el-table-column prop="firstReason" label="一级审核反馈" width="180" align="center"></el-table-column>
            <el-table-column prop="secondReason" label="二级审核反馈" width="180" align="center"></el-table-column>
            <el-table-column label="操作" width="250" align="center">
                <template slot-scope="scope">
                    <el-button size="mini"  @click="handleRefuse(scope.row)" class= "update_button">修改意见</el-button>
                    <el-button size="mini" @click="download(scope.row.url,scope.row.name)" class= "update_button">下载</el-button>
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
                    <el-date-picker type="date" v-model="form.endDate" placeholder="选择日期" style="width:100%" value-format="yyyy-MM-dd" format="yyyy-MM-dd"></el-date-picker>
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
         <el-dialog title="驳回原因" :visible.sync="refuseVisible" width="300px" center>
             <el-form :model="refuseForm" ref="form" label-width="120px">
                  <el-form-item prop="firstReason" label="驳回原因" size="mini">
                    <el-input v-model="refuseForm.reason" placeholder="驳回原因"></el-input>
                </el-form-item>       
             </el-form>
            <span slot="footer">
                <el-button size="mini" @click="refuseVisible = false">取消</el-button>
                <el-button size="mini" @click="refuseSave">确定</el-button>                
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
  mixins: [fileList],
  fileStatus: -1,
  rejectionStatus: -1
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