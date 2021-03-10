<template>
  <div class="login-wrap">
    <div class="ms-title">文件发布系统登录</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="ruleForm.username" placeholder="用户名" ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" v-model="ruleForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {mixin} from "../mixins/index";
import {getLoginStatus} from "../api/index";
export default {
  mixins:[mixin],
  data: function(){
    return {
      ruleForm:{
        username: "admin",
        password: "123"
      },
      rules:{
        username:[
          {required:true,message:"请输入用户名",trigger:"blur"}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"}
        ]
      }
    };
  },
  methods:{
    submitForm(d){
      let params = new URLSearchParams();
      params.append("name",this.ruleForm.username);
      params.append("password",this.ruleForm.password);
      let username= this.ruleForm.username;
      getLoginStatus(params)
      .then((res) =>{
        if(res.code == 1){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
          localStorage.setItem('level','1')
          this.$router.push("/Uploader");
          this.notify("登录成功"+"\n"+"上传者界面","success");
        }
         if(res.code == 2){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
          localStorage.setItem('level','2')
          this.$router.push("/Reviewer");
          this.notify("登录成功"+"\n"+"一级审核者界面:","success");
        }
         if(res.code == 3){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
          localStorage.setItem('level','3')
          this.$router.push("/Teminator");
          this.notify("登录成功"+"\n"+"二级审核者界面","success");
        }
        if(res.code == 4){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
          localStorage.setItem('level','4')
          this.$router.push("/Consumer");
          this.notify("登录成功"+"\n"+"超级管理员界面","success");
        }
        else if(res.code==0){
          this.notify(res.msg,"error");
        }
      });
  }
  }
}
</script>
<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/background.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #000;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 250px;
  /* 左移150 */
  margin-left: -190px;
  margin-top: -150px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
}
.login-btn {
  /* 居中 */
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
</style>
