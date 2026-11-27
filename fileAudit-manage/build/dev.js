'use strict'
process.env.NODE_ENV = 'development'

const http = require('http')
const express = require('express')
const proxy = require('http-proxy-middleware')
const webpack = require('webpack')
const devMiddleware = require('webpack-dev-middleware')
const { dev } = require('../config')
const webpackConfig = require('./webpack.dev.conf')

function createDevServer () {
  const app = express()
  for (const route of Object.keys(dev.proxyTable)) {
    app.use(proxy(route, { logLevel: 'warn', ...dev.proxyTable[route] }))
  }

  const assets = devMiddleware(webpack(webpackConfig), {
    publicPath: dev.assetsPublicPath,
    watchOptions: { poll: dev.poll },
    stats: 'errors-warnings'
  })
  app.use(assets)
  const server = http.createServer(app)
  return {
    server,
    close (callback) {
      assets.close(() => server.close(callback))
    }
  }
}

if (require.main === module) {
  const host = process.env.HOST || dev.host
  const port = Number(process.env.PORT || dev.port)
  const { server } = createDevServer()
  server.listen(port, host, () => {
    console.log(`File audit frontend: http://${host}:${server.address().port}`)
  })
}

module.exports = createDevServer
