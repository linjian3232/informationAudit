import Vue from 'vue'
import Login from '@/pages/Login.vue'
import { mixin } from '@/mixins'
import { getFileUrl } from '@/api'

describe('文件发布两级审核系统', () => {
  it('shows the login page without filling in account credentials', () => {
    const LoginView = Vue.extend(Login)
    Vue.config.ignoredElements = ['el-form', 'el-form-item', 'el-input', 'el-button']
    const view = new LoginView().$mount()
    expect(view.$el.querySelector('.ms-title').textContent).toBe('文件发布两级审核系统登录')
    expect(view.ruleForm).toEqual({ username: '', password: '' })
    view.$destroy()
  })

  it('addresses stored file and avatar paths on the current origin', () => {
    expect(getFileUrl('publicFile/report.txt')).toBe('/publicFile/report.txt')
    expect(getFileUrl('/publicFile/审核 #1.txt')).toBe('/publicFile/%E5%AE%A1%E6%A0%B8%20%231.txt')
    expect(getFileUrl('/img/uploaderPic/default.jpg')).toBe('/img/uploaderPic/default.jpg')
    expect(getFileUrl('')).toBe('')
  })

  it('asks for confirmation once and deletes selected record IDs', async () => {
    const view = {
      multipleSelection: [{ id: 3 }, { id: 7 }],
      $confirm: jest.fn().mockResolvedValue(),
      deleteRecords: jest.fn().mockResolvedValue()
    }

    await mixin.methods.delAll.call(view)

    expect(view.$confirm).toHaveBeenCalledTimes(1)
    expect(view.deleteRecords).toHaveBeenCalledWith([3, 7])
  })
})
