<script setup>
import { onMounted, ref } from 'vue';

onMounted(() => {
    console.log("Notification bar monted")
})

</script>

<template>
  <div class="notification-bell clicable">
    <span class="material-symbols-outlined">notifications</span>
    <p class="notification-count">0</p>
    <div class="notification-count-bg"></div>
  </div>
</template>

<style scoped>

.clicable {
  cursor: pointer;
}

.notification-count-bg {
  position: absolute;
  background-color: white;
  width: 16px;
  height: 16px;
  border-radius: 100%;
  top: -5px;
  right: -7px;
}

.notification-count {
  position: absolute;
  top: -22px;
  right: -3px;
  z-index: 1;
}

.notification-bell {
  position: relative;
}

.material-symbols-outlined {
  font-variation-settings:
  'FILL' 0,
  'wght' 400,
  'GRAD' 0,
  'opsz' 40
}
</style>
