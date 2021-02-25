import {get,post} from "./http";

//判断管理员是否登录成功
export const getLoginStatus = (params) => post(`admin/login/status`,params);

//=====================歌手相关=======================

//查询歌手
export const getAllSinger = () => get(`singer/allSinger`);

//添加歌手
export const setSinger= (params) => post(`singer/add`,params);

//修改歌手信息
export const updateSinger = (params) => post(`singer/update`,params);

//删除歌手
export const deleteSinger = (id) => get(`singer/delete?id=${id}`);

//=====================歌曲相关=======================
export const songOfSingerId=(id) => get(`song/singer/detail?singerId=${id}`);

export const updateSong= (params) =>post(`song/update`,params);

export const delSong= (id) =>get(`song/delete?id=${id}`);

//根据歌曲id查询歌曲对象
export const songOfSongId= (id) =>get(`song/detail?songId=${id}`);

//根据歌曲id查询歌曲对象
export const songOfSongName= (songName) =>get(`song/songOfSongName?songName=${songName}`);

//=====================歌单相关=======================

//查询歌手
export const getAllSongList = () => get(`songList/allSongList`);

//添加歌手
export const setSongList= (params) => post(`songList/add`,params);

//修改歌手信息
export const updateSongList = (params) => post(`songList/update`,params);

//删除歌手
export const deleteSongList = (id) => get(`songList/delete?id=${id}`);

//=====================歌单的歌曲相关=======================
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);
//给歌单增加歌曲
export const listSongAdd = (params) => post(`listSong/add`,params);
//删除歌单的歌曲
export const delListSong = (songId,songListId) => get(`listSong/delete?songId=${songId}&songListId=${songListId}`);