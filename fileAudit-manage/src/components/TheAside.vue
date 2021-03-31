<template>
    <div class="sidebar">
        <el-menu
            style="width:150px"
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#334256"
            text-color="#ffffff"
            active-text-color="#20a0ff"
            router
            >
            <template v-for="item in items">
                <template>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        {{item.title}}
                    </el-menu-item>
                </template>
            </template>
        </el-menu>

    </div>
</template>

<script>
import bus from "../assets/js/bus"
export default {
    data() {
        return{
            // 收缩状态，此处为不收缩
            collapse: true,
            items:[
                {
                    icon: 'el-icon-document',
                    index: 'Info',
                    title: '系统首页'
                },
                 {
                    icon: 'el-icon-document',
                    index: 'Consumer',
                    title: '用户管理'
                },
                 {
                    icon: 'el-icon-document',
                    index: 'Uploader',
                    title: '上传者管理'
                },
                {
                    icon: 'el-icon-document',
                    index: 'Reviewer',
                    title: '一级审核者'
                },
                 {
                    icon: 'el-icon-document',
                    index: 'Teminator',
                    title: '二级审核者'
                }

            ]
        }
    },
    computed:{
        onRoutes(){
            return this.$route.path.replace('/','');
        }
    },
    created(){
        //通过bus进行组件间的通信，来折叠侧边栏
        bus.$on('collapse',msg =>{
            this.collapse = msg
        })
    }

}
</script>
<style scoped>
.sidebar {
    display:block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    background-color: #334256;
    /* 设置如果超出范围可以下滑 */
    overflow-y: scroll;

}

/* 用于在没有超过范围的时候将滑纽栏取消掉 */
.sidebar::-webkit-scrollbar{
    width: 0;
}

/* not(el-menu--collapse)的意义在于只有当collapse（折叠状态为false的时候）才采用该css样式 */
.sidebar-el-menu:not(.el-menu--collapse){
    width: 0px;
}

/* 解决因为v-for产生列表多出一截空白 */
.sidebar >ul {
    height: 100%;
}

</style>