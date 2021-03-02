<template>
  <div class="login-wrap">
    <div class="ms-title">music 后台管理登录</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
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
          this.$router.push({path:`/uploader`,query:{username}});
          this.notify("登录成功"+"\n"+"上传者界面","success");
        }
         if(res.code == 2){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
        this.$router.push({path:`/reviewer`,query:{username}});
          this.notify("登录成功"+"\n"+"一级审核者界面:","success");
        }
         if(res.code == 3){
          // vue提供的一个缓存机制，类似一个map
          localStorage.setItem('userName',this.ruleForm.username)
        this.$router.push({path:`/info`,query:{username}});
          this.notify("登录成功"+"\n"+"二级审核者界面","success");
        }
        else if(res.code==0){
          this.notify("登录失败","error");
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
  color: #fff;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
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
