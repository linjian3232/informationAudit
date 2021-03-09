<template>
    <div class="header">
        <!-- 折叠图片 -->
        <div class="collapse-btn" @click="collapseChange">
            <!-- <i class="el-icon-menu"></i> -->
            <el-tooltip :content="collapse?`关闭管理面板`:`开启管理面板`" placement="bottom">
                <i class="el-icon-menu"></i>
            </el-tooltip>
        </div>
        <div class="logo">music后台管理</div>
        <div class="header-right">
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <div class="user-avator">
                <img src="../assets/img/user.jpg"/>
            </div>
            <!-- el-dropdown :下拉菜单 -->
            <el-dropdown class="user-name" trigger ="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    {{userName}}
                    <i class="el-icon-caret-bottom"></i>
                </span>
               
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>


<script>
import bus from "../assets/js/bus";
export default {
    data(){
        return{
            collapse: false,
            fullscreen:false
        }

    },
    //监视状态
    computed:{
        userName(){
            return localStorage.getItem('userName');
        }
    },
    methods:{
        notify(title,type){
        this.$notify({
            title: title,
            type: type
        })
    },
        //侧边栏折叠
        collapseChange(){
            let level= localStorage.getItem('level');
            if(level=='4'){
            this.collapse=!this.collapse;
            bus.$emit('collapse',this.collapse);
            if(this.collapse==true){
            this.notify("管理面板已开启","success");}
            }
            else{
            this.notify("您需要超级管理员权限","error");
            }
        },
        //全屏事件
        handleFullScreen(){
            if(this.fullscreen){
                if(document.exitFullscreen){
                    document.exitFullscreen();
                }else if(document.webkitCancelFullScreen){ //safari、 chrome
                    document.webkitCancelFullScreen();
                }
                else if(document.mozCancelFullScreen){//firefox
                    document.mozCancelFullScreen();
                }
                else if (document.msExitFullScreen){//ie
                    document.msExitFullScreen();
                }
            }
            else{
                let element =document.documentElement;
                if(element.requestFullscreen)
                {
                    element.requestFullscreen();
                }else if(element.webkitRequestFullScreen){      //safari 、Chrome
                    element.webkitRequestFullScreen();
                }else if(element.mozRequestFullScreen){         //firefox
                    element.mozRequestFullScreen();
                }else if (element.msRequestFullScreen){         //ie
                    element.msRequestFullScreen();
                }
            }
            this.fullscreen=!this.fullscreen;
        },
        handleCommand(command){
            if(command=="logout"){
                // 退出登录清除缓存
                localStorage.removeItem('userName');
                this.$router.push("/");
            }
        }
    }
}
</script>
<style scoped>
.header{
    position: relative;
    background-color: #253041;
    box-sizing: border-box;
    width:100%;
    height: 70px;
    font-size: 22px;
    color:white;
}

.collapse-btn{
    float: left;
    padding: 0 21px;
    /* 鼠标移过来变成手状 */
    cursor:pointer;
    /* 起始高度 */
    line-height: 70px;
}

/* header内style */
.header .logo{
    /* 靠左对齐 */
    float:left;
    line-height: 70px;
}

.header-right{
    float:right;
    /* 右边距 */
    padding-right: 50px;
    /* 横着平均分配 布局*/
    display: flex;
    height:70px;
    align-items: center;
}

.btn-fullscreen{
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.user-avator{
    margin-left:20px;

}
.user-avator img{
    display: block;
    width:40px;
    height:40px;
    /* 圆弧  50%正圆形*/
    border-radius:50%;
}

.user-name{
    margin-left:10px;
}

.el-dropdown-link{
    color:#ffffff;
    cursor: pointer;
}
</style>