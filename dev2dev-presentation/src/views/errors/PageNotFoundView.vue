<template>
    <h1>404 - Page not found</h1>
    <div v-if="!token">
        <RouterLink to="/">{{$t('login')}}</RouterLink>
    </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { RouterLink } from "vue-router";

const token = ref(null);

onMounted(() => {
  token.value = localStorage.getItem("jwtToken");
});
</script>
