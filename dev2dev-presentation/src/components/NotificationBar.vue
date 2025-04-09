<script setup>
import { onMounted, ref } from 'vue';
import { fetchEventSource } from '@microsoft/fetch-event-source';

const sseConnectionActive = ref(false);
const notifications = ref([])

onMounted(async () => {
    console.log("Notification bar monted")
    const token = localStorage.getItem('jwtToken')
    await fetchEventSource(`${import.meta.env.VITE_API_URL}sse`, {
      async onopen(response) {
        if (response.ok ) {
          sseConnectionActive.value = true;
            return; // everything's good
        } else if (response.status >= 400 && response.status < 500 && response.status !== 429) {
            throw new FatalError();
        } else {
            throw new RetriableError();
        }
    },
    onmessage(msg) {
        notifications.value.push(msg.data);
    },
    headers : {
      Authorization: `Bearer ${token}`
    }
});

})

</script>

<template>
  <div class="notification-bell clicable">
    <span class="material-symbols-outlined">notifications</span>
    <p class="notification-count" v-if="notifications.length < 10">{{ notifications.length }}</p>
    <p class="notification-count-max" v-else >9+</p>
    <div class="notification-count-bg"></div>
  </div>
</template>

<style scoped>

.notification-count-bg {
  position: absolute;
  background-color: white;
  width: 18px;
  height: 18px;
  border-radius: 100%;
  top: -5px;
  right: -7px;
}

.notification-count {
  position: absolute;
  top: -25px;
  right: -2px;
  z-index: 1;
}

.notification-count-max {
  position: absolute;
  top: -20px;
  right: -6px;
  z-index: 1;
  font-size: small;
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
