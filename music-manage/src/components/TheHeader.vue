<template>
    <div class="header">
        <!-- 折叠图片 -->
        <div class="collapse-btn" @click="collapseChange">
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">music后台管理</div>
        <div class="header-right">
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
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
    methods:{
        //侧边栏折叠
        collapseChange(){
            this.collapse=!this.collapse;
            bus.$emit('collapse',this.collapse);
        },
        //全屏事件
        handleFullScreen(){
            if(this.fullscreen){
                if(document.exitFullscreen){
                    document.exitFullscreen();
                }else if(document.webkitCancelFullScreen){ //safari\ chrome
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
                }
                else if(element.webkitCancelFullScreen)
                {
                    element.webkitCancelFullScreen();
                }
                else if(element.mozCancelFullScreen)
                {
                    element.mozCancelFullScreen();
                }
                else if(element.msExitFullScreen)
                {
                    element.msExitFullScreen();
                }
            }
            this.fullscreen=!this.fullscreen;
        }
    }
}
</script>
<style scoped>
.header{
    position: relative;
    background-color: #253041;
    box-sizing: boder-box;
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
    margin-right: 50px;
    font-size: 24px;
}
</style>