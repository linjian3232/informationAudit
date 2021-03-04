import Axios from "axios";
import {get,post} from "./http";

//判断管理员是否登录成功
export const getLoginStatus = (params) => post(`admin/login/status`,params);

//====================上传者相关=======================

//查询歌手
export const getAllUploader = () => get(`uploader/allUploader`);

//添加歌手
export const setUploader= (params) => post(`uploader/add`,params);

//修改歌手信息
export const updateUploader = (params) => post(`uploader/update`,params);

//删除歌手
export const deleteUploader = (id) => get(`uploader/delete?id=${id}`);

// //=====================歌曲相关=======================
// export const getAllSong = () =>get(`song/allSong`);

// export const songOfSingerId=(id) => get(`song/singer/detail?singerId=${id}`);

// export const updateSong= (params) =>post(`song/update`,params);

// export const delSong= (id) =>get(`song/delete?id=${id}`);

// //根据歌曲id查询歌曲对象
// export const songOfSongId= (id) =>get(`song/detail?songId=${id}`);

// //根据歌曲名字查询歌曲对象
// export const songOfSongName= (songName) =>get(`song/songOfSongName?songName=${songName}`);

// export const songOfStatus= (status,singerId) =>get(`song/songOfStatus?status=${status}&&singerId=${singerId}`);

// //更新歌曲状态
// export const updateSongStatus= (params) =>post(`song/updateStatus`,params);


//=====================文件相关=======================
export const getAllPublicFile = () =>get(`publicFile/allPublicFile`);

export const publicFileOfUploaderId=(id) => get(`publicFile/uploader/detail?uploaderId=${id}`);

export const updatePublicFile= (params) =>post(`publicFile/update`,params);

export const delPublicFile= (id) =>get(`publicFile/delete?id=${id}`);

//根据歌曲id查询歌曲对象
export const publicFileOfPublicFileId= (id) =>get(`publicFile/detail?publicFileId=${id}`);

export const publicFileOfStatus= (uploaderId,status) =>get(`publicFile/publicFileOfStatus?uploaderId=${uploaderId}&&status=${status}`);

//更新歌曲状态
export const updatePublicFileStatus= (params) =>post(`publicFile/updateStatus`,params);

//保存一级反馈信息
export const updateFirstReason= (params) =>post(`publicFile/updateFirstReason`,params);
//=====================用户相关==================================
//查询所有用户
export const getAllConsumer = () => get(`consumer/allConsumer`);
//添加歌手
export const setConsumer= (params) => post(`consumer/add`,params);

//修改歌手信息
export const updateConsumer = (params) => post(`consumer/update`,params);

//删除歌手
export const deleteConsumer = (id) => get(`consumer/delete?id=${id}`);

//=====================用户相关==================================
// 下载音乐
export const download = (url) => Axios({
    method: 'get',
    url:url,
    responseType: 'blob'
});
