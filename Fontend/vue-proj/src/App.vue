<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
// 禁止页面进行缩放
export default {
  mounted() {
    // 禁止键盘事件 (Ctrl + +/-/0)
    document.addEventListener('keydown', this.handleKeyDown)
    // 禁止 Ctrl + 鼠标滚轮
    // document.addEventListener('wheel', this.handleWheel, { passive: false })
  },
  beforeDestroy() {
    document.removeEventListener('keydown', this.handleKeyDown)
    document.removeEventListener('wheel', this.handleWheel)
  },
  methods: {
    handleKeyDown(e) {
      // 检测 Ctrl + 加号/减号/0 (Mac 的 Meta 键)
      if ((e.ctrlKey || e.metaKey) && 
          ['+', '-', '0', '='].includes(e.key)) {
        e.preventDefault()
      }
    },
    handleWheel(e) {
      // 检测 Ctrl + 滚轮
      if (e.ctrlKey) {
        e.preventDefault()
      }
    }
  }
}
</script>

<style>
/* 增加全局样式，防止刷新后页面变化 */
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow: hidden;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow: hidden !important;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

</style>