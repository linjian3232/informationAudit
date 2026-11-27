process.env.NODE_ENV = 'development'
process.env.HOST = '127.0.0.1'

const assert = require('assert')
const http = require('http')
const { JSDOM } = require('jsdom')

function listen (server, port) {
  const socket = server.listen(port, '127.0.0.1')
  return new Promise((resolve, reject) => {
    socket.once('listening', () => resolve(socket.address().port))
    socket.once('error', reject)
  })
}

function waitForElement (window, selector) {
  return new Promise((resolve, reject) => {
    const deadline = Date.now() + 10000
    function check () {
      const element = window.document.querySelector(selector)
      if (element) {
        resolve(element)
      } else if (Date.now() >= deadline) {
        reject(new Error(`Timed out waiting for ${selector}`))
      } else {
        setTimeout(check, 25)
      }
    }
    check()
  })
}

async function run () {
  const received = []
  const backend = http.createServer((request, response) => {
    const chunks = []
    request.on('data', chunk => chunks.push(chunk))
    request.on('end', () => {
      received.push({
        method: request.method,
        url: request.url,
        body: Buffer.concat(chunks).toString()
      })
      response.setHeader('Content-Type', 'application/json')
      if (request.url === '/admin/login/status') {
        response.end(JSON.stringify({ code: 1 }))
      } else if (request.url === '/uploader/allUploader') {
        response.end(JSON.stringify([
          { id: 7, name: '测试上传者', studyNumber: 'audit-uploader', gender: 1, pic: '/img/uploaderPic/default.jpg' }
        ]))
      } else if (request.url === '/publicFile/uploader/detail?uploaderId=7') {
        response.end(JSON.stringify([{ id: 9, name: '测试发布文件.txt', status: 0, url: '/publicFile/test-file.txt' }]))
      } else {
        response.writeHead(404)
        response.end('{}')
      }
    })
  })

  let devServer
  let dom
  try {
    const backendPort = await listen(backend, 0)
    process.env.AUDIT_BACKEND_URL = `http://127.0.0.1:${backendPort}`
    devServer = require('../../build/dev')()
    const port = await listen(devServer.server, 0)
    dom = await JSDOM.fromURL(`http://127.0.0.1:${port}/`, {
      resources: 'usable',
      runScripts: 'dangerously',
      pretendToBeVisual: true
    })

    const username = await waitForElement(dom.window, 'input[placeholder="用户名"]')
    const password = await waitForElement(dom.window, 'input[placeholder="密码"]')
    assert.strictEqual(dom.window.document.title, '文件发布两级审核系统')
    assert.strictEqual(username.value, '')
    assert.strictEqual(password.value, '')
    username.value = 'audit-uploader'
    username.dispatchEvent(new dom.window.Event('input', { bubbles: true }))
    password.value = 'test#password&'
    password.dispatchEvent(new dom.window.Event('input', { bubbles: true }))
    dom.window.document.querySelector('button[type="submit"]').click()

    await waitForElement(dom.window, '.el-table__body .el-button')
    assert.match(dom.window.location.hash, /^#\/Uploader$/)
    const data = new URLSearchParams(received[0].body)
    assert.strictEqual(received[0].method, 'POST')
    assert.strictEqual(received[0].url, '/admin/login/status')
    assert.strictEqual(data.get('name'), 'audit-uploader')
    assert.strictEqual(data.get('password'), 'test#password&')
    assert(received.some(request => request.url === '/uploader/allUploader'))

    const fileManagement = Array.from(dom.window.document.querySelectorAll('.el-table__body button'))
      .find(button => button.textContent.trim() === '文件管理')
    assert(fileManagement)
    fileManagement.click()
    await waitForElement(dom.window, 'input[placeholder="请输入发布文件名"]')
    assert.match(dom.window.location.hash, /^#\/PersonalFile\?/)
    const file = await waitForElement(dom.window, '.el-table__body tr td:nth-child(2) .cell')
    assert.strictEqual(file.textContent.trim(), '测试发布文件.txt')
    assert(received.some(request => request.url === '/publicFile/uploader/detail?uploaderId=7'))

    console.log('Passed login, uploader navigation, file list, and dev proxy smoke test.')
  } finally {
    if (dom) dom.window.close()
    if (devServer) await new Promise(resolve => devServer.close(resolve))
    await new Promise(resolve => backend.close(resolve))
  }
}

run().catch(error => {
  console.error(error)
  process.exitCode = 1
})
