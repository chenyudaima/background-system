<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      ws: null
    }
  },
  created() {
    //统计在线人数
    this.ws = new WebSocket("ws://localhost:8080/ws")
  }
};
</script>

<style></style>
