import { getFileUrl, uploaderAvatarUrl } from '../api'

const genders = { 0: '女', 1: '男', 2: '其他', 3: '未知' }
const roles = { 1: '上传者', 2: '一级审核者', 3: '二级审核者', 4: '超级管理员' }
const statuses = {
  0: '未审核',
  1: '一级审核通过',
  2: '二级审核通过',
  '-1': '一级审核未通过',
  '-2': '二级审核未通过'
}

export const mixin = {
  methods: {
    notify (title, type) {
      this.$notify({ title, type })
    },
    getUrl: getFileUrl,
    uploadUrl: uploaderAvatarUrl,
    changeGender (value) {
      return genders[value] || value
    },
    changeLevel (value) {
      return roles[value] || value
    },
    statusStyle (value) {
      return statuses[value] || value
    },
    attachBirth (value) {
      return value ? String(value).slice(0, 10) : ''
    },
    beforeAvatarUpload (file) {
      if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
        this.$message.error('头像图片只能是 JPG 或 PNG 格式')
        return false
      }
      if (file.size >= 2 * 1024 * 1024) {
        this.$message.error('头像图片必须小于 2 MB')
        return false
      }
      return true
    },
    handleUploadSuccess (result) {
      if (result.code === 1) {
        this.getData()
        this.notify('上传成功', 'success')
      } else {
        this.notify(result.msg || '上传失败', 'error')
      }
    },
    handleUploadError () {
      this.notify('上传失败', 'error')
    },
    handleDelete (row) {
      this.idx = row.id
      this.delVisible = true
    },
    handleSelectionChange (selection) {
      this.multipleSelection = selection
    },
    deleteRow () {
      this.delVisible = false
      return this.deleteRecords([this.idx])
    },
    async delAll () {
      const ids = this.multipleSelection.map(row => row.id)
      if (!ids.length) {
        this.notify('请选择要删除的记录', 'warning')
        return
      }
      try {
        await this.$confirm(`确定删除选中的 ${ids.length} 条记录吗？`, '批量删除', { type: 'warning' })
      } catch (error) {
        return
      }
      await this.deleteRecords(ids)
    },
    async deleteRecords (ids) {
      const results = await Promise.all(ids.map(async id => {
        try {
          return await this.deleteRequest(id) === true
        } catch (error) {
          return false
        }
      }))
      const deleted = results.filter(Boolean).length
      if (deleted) {
        this.multipleSelection = []
        this.getData()
      }
      if (deleted === ids.length) {
        this.notify('删除成功', 'success')
      } else {
        this.notify(`已删除 ${deleted} 条，${ids.length - deleted} 条删除失败`, 'error')
      }
    }
  }
}
