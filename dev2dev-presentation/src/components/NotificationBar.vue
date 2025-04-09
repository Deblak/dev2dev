<script setup>
import { onMounted, ref } from 'vue';
import { fetchEventSource } from '@microsoft/fetch-event-source';

const sseConnectionActive = ref(false);
const notifications = ref([])

onMounted(async () => {
    console.log("Notification bar monted")
    await fetchEventSource(`${import.meta.env.VITE_API_URL}sse/test`, {
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
        console.log(msg.data);
    }
});

})

</script>

<template>
  <div class="notification-bell clicable">
    <span class="material-symbols-outlined">notifications</span>
    <p class="notification-count">{{ notifications.length }}</p>
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
