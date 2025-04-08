<script setup>
import { onMounted, ref } from 'vue';

onMounted(() => {
    console.log("Notification bar monted")
})
</script>

<template>
    <span class="material-symbols-outlined">notifications</span>
</template>

<style scoped>

.material-symbols-outlined {
  font-variation-settings:
  'FILL' 0,
  'wght' 400,
  'GRAD' 0,
  'opsz' 24
}

</style>