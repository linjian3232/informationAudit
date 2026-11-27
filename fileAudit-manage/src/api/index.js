import 'url-search-params-polyfill'
import { get, post } from './http'

export const getLoginStatus = params => post('/admin/login/status', params)
export const getUserOfLevel = privilegeLevel => get('/admin/level', { privilegeLevel })
export const getAllAdmin = () => get('/admin/all')
export const addAdmin = params => post('/admin/add', params)
export const updateAdmin = params => post('/admin/update', params)
export const deleteAdmin = id => get('/admin/delete', { id })
export const adminOfName = name => get('/admin/verifyExist', { name })

export const getAllUploader = () => get('/uploader/allUploader')
export const setUploader = params => post('/uploader/add', params)
export const updateUploader = params => post('/uploader/update', params)
export const deleteUploader = id => get('/uploader/delete', { id })
export const uploaderOfStudyNumber = studyNumber => get('/uploader/uploaderOfStudyNumber', { studyNumber })
export const uploaderAvatarUrl = id => `/uploader/updateUploaderPic?id=${encodeURIComponent(id)}`

export const addPublicFile = form => post('/publicFile/add', form, { timeout: 60000 })
export const publicFileOfUploaderId = uploaderId => get('/publicFile/uploader/detail', { uploaderId })
export const publicFileOfStatus = (uploaderId, status) => get('/publicFile/publicFileOfStatus', { uploaderId, status })
export const updatePublicFile = params => post('/publicFile/update', params)
export const delPublicFile = id => get('/publicFile/delete', { id })
export const updatePublicFileStatus = params => post('/publicFile/updateStatus', params)
export const updateFirstReason = params => post('/publicFile/updateFirstReason', params)
export const updateSecondReason = params => post('/publicFile/updateSecondReason', params)
export const publicFileUploadUrl = (id, uploaderName) => {
  const params = new URLSearchParams()
  params.set('id', id)
  params.set('uploaderName', uploaderName)
  return `/publicFile/updatePublicFileUrl?${params}`
}

export const getFileUrl = path => {
  if (!path) return ''
  return '/' + path.replace(/^\/+/, '').split('/').map(encodeURIComponent).join('/')
}

export const download = path => get(getFileUrl(path), undefined, { responseType: 'blob' })
