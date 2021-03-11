<template>
    <div>
        <div class="crumbs">
            用户个人信息页           
        </div>
        <div class="info_background">
            <el-form :model="registerForm" :rules="rules" ref="ruleForm">
                <el-form-item prop="name" label="用户名">
                     <el-input v-model="registerForm.name" placeholder="用户名" ></el-input>
                </el-form-item>
                <el-form-item label="权限等级" width="250" align="center">
                     <el-input v-model="registerForm.level" placeholder="权限等级" ></el-input>
            </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import {mixin} from '../mixins/index';
export default {
    mixins:[mixin],
    data(){
        return{
            registerForm:{ //添加框
                name:localStorage.getItem('userName'),
                level:localStorage.getItem('level'),
            }
            }
    }
}
</script>
<style scoped>
.info_background {
  position: absolute;
  left: 40%;
  top: 30%;
  width: 800px;
  height: 250px;
  /* 左移150 */
  margin-left: -190px;
  margin-top: -150px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;

}
</style>