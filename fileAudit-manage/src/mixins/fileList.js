import { mixin } from './index'
import {
  addPublicFile,
  delPublicFile,
  download,
  publicFileOfStatus,
  publicFileOfUploaderId,
  publicFileUploadUrl,
  updateFirstReason,
  updatePublicFile,
  updatePublicFileStatus,
  updateSecondReason
} from '../api'

export default {
  mixins: [mixin],
  data () {
    return {
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      refuseVisible: false,
      uploading: false,
      form: {},
      refuseForm: { id: null, reason: '' },
      files: [],
      select_word: '',
      pageSize: 5,
      currentPage: 1,
      idx: null,
      multipleSelection: []
    }
  },
  computed: {
    uploaderId () {
      return this.$route.query.id
    },
    uploaderName () {
      return this.$route.query.name
    },
    tableData () {
      return this.files.filter(file => (file.name || '').includes(this.select_word))
    },
    data () {
      const offset = (this.currentPage - 1) * this.pageSize
      return this.tableData.slice(offset, offset + this.pageSize)
    }
  },
  watch: {
    select_word () {
      this.currentPage = 1
    },
    uploaderId () {
      this.files = []
      this.getData()
    }
  },
  created () {
    this.getData()
  },
  methods: {
    deleteRequest: delPublicFile,
    handleCurrentChange (page) {
      this.currentPage = page
    },
    async getData () {
      const uploaderId = this.uploaderId
      if (!uploaderId) return
      const { fileStatus } = this.$options
      try {
        const files = fileStatus === undefined
          ? await publicFileOfUploaderId(uploaderId)
          : await publicFileOfStatus(uploaderId, fileStatus)
        if (uploaderId === this.uploaderId) {
          this.files = files
          const lastPage = Math.max(1, Math.ceil(this.tableData.length / this.pageSize))
          this.currentPage = Math.min(this.currentPage, lastPage)
        }
      } catch (error) {
        this.notify('无法加载文件列表', 'error')
      }
    },
    async addPublicFile () {
      if (this.uploading) return
      const formElement = this.$refs.uploadForm.$el
      const form = new FormData(formElement)
      const file = form.get('file')
      if (!file || !file.name) {
        this.notify('请选择要上传的文件', 'warning')
        return
      }
      if (!form.get('beginDate') || !form.get('endDate') || form.get('beginDate') > form.get('endDate')) {
        this.notify('请选择有效的发布起止日期', 'warning')
        return
      }
      if (!this.beforePublicFileUpload(file)) return

      form.append('uploaderId', this.uploaderId)
      form.append('uploaderName', this.uploaderName)
      this.uploading = true
      try {
        const result = await addPublicFile(form)
        if (result.code === 1) {
          formElement.reset()
          this.centerDialogVisible = false
          this.handleUploadSuccess(result)
        } else {
          this.notify(result.msg || '上传失败', 'error')
        }
      } catch (error) {
        this.handleUploadError()
      } finally {
        this.uploading = false
      }
    },
    handleEdit (row) {
      this.form = {
        id: row.id,
        name: row.name,
        introduction: row.introduction || '',
        beginDate: this.attachBirth(row.beginDate),
        endDate: this.attachBirth(row.endDate),
        beginTime: row.beginTime || '',
        endTime: row.endTime || ''
      }
      this.editVisible = true
    },
    async editSave () {
      const params = new URLSearchParams()
      Object.keys(this.form).forEach(field => params.set(field, this.form[field]))
      try {
        const result = await updatePublicFile(params)
        if (result.code === 1) {
          this.editVisible = false
          this.getData()
          this.notify('修改成功', 'success')
        } else {
          this.notify(result.msg || '修改失败', 'error')
        }
      } catch (error) {
        this.notify('修改失败', 'error')
      }
    },
    beforePublicFileUpload (file) {
      if (file.size >= 15 * 1024 * 1024) {
        this.$message.error('发布文件必须小于 15 MB')
        return false
      }
      return true
    },
    uploadPublicFileUrl (id) {
      return publicFileUploadUrl(id, this.uploaderName)
    },
    async changeStatus (id, status) {
      const params = new URLSearchParams()
      params.set('id', id)
      params.set('status', status)
      try {
        const result = await updatePublicFileStatus(params)
        if (result.code === 1) {
          this.getData()
          this.notify('审核成功', 'success')
          return true
        }
        this.notify(result.msg || '审核失败', 'error')
      } catch (error) {
        this.notify('审核失败', 'error')
      }
      return false
    },
    handleRefuse (row) {
      const reasonField = this.$options.rejectionStatus === -1 ? 'firstReason' : 'secondReason'
      this.refuseForm = { id: row.id, reason: row[reasonField] || '' }
      this.refuseVisible = true
    },
    async refuseSave () {
      const { id, reason } = this.refuseForm
      const { fileStatus, rejectionStatus } = this.$options
      const firstReview = rejectionStatus === -1
      const saveReason = firstReview ? updateFirstReason : updateSecondReason
      const params = new URLSearchParams()
      params.set('id', id)
      params.set(firstReview ? 'firstReason' : 'secondReason', reason)
      try {
        const result = await saveReason(params)
        if (result.code !== 1) {
          this.notify(result.msg || '保存审核意见失败', 'error')
          return
        }
        if (fileStatus !== rejectionStatus) {
          if (!await this.changeStatus(id, rejectionStatus)) return
        } else {
          this.getData()
          this.notify('修改成功', 'success')
        }
        this.refuseVisible = false
      } catch (error) {
        this.notify('保存审核意见失败', 'error')
      }
    },
    async download (path, name) {
      try {
        const blob = await download(path)
        const link = document.createElement('a')
        const url = URL.createObjectURL(blob)
        link.download = name
        link.href = url
        link.click()
        setTimeout(() => URL.revokeObjectURL(url), 0)
      } catch (error) {
        this.notify('下载失败', 'error')
      }
    }
  }
}
