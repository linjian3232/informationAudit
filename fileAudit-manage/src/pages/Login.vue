<template>
  <div class="login-wrap">
    <div class="ms-title">文件发布两级审核系统登录</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" @submit.native.prevent="submitForm">
        <el-form-item prop="username" label="用户名">
          <el-input v-model.trim="ruleForm.username" placeholder="用户名" auto-complete="username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" v-model="ruleForm.password" placeholder="密码" auto-complete="current-password"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" native-type="submit" :loading="submitting">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { getLoginStatus } from '../api'

const roleHomePages = {
  1: '/Uploader',
  2: '/Reviewer',
  3: '/Teminator',
  4: '/Consumer'
}

export default {
  mixins: [mixin],
  data () {
    return {
      submitting: false,
      ruleForm: { username: '', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitForm () {
      this.$refs.ruleForm.validate(valid => {
        if (valid && !this.submitting) this.login()
      })
    },
    async login () {
      const { username, password } = this.ruleForm
      const params = new URLSearchParams()
      params.set('name', username)
      params.set('password', password)

      this.submitting = true
      try {
        const result = await getLoginStatus(params)
        const homePage = roleHomePages[result.code]
        if (!homePage) {
          this.notify(result.msg || '登录失败', 'error')
          return
        }

        localStorage.setItem('userName', username)
        localStorage.setItem('level', result.code)
        this.$router.push(homePage)
        this.notify('登录成功', 'success')
      } catch (error) {
        this.notify('登录失败，请检查后端服务是否已启动', 'error')
      } finally {
        this.submitting = false
      }
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
