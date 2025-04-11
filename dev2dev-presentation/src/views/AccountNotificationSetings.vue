<script setup>
import { onMounted, ref } from 'vue';

const options = ref({});

onMounted( async () => {
    const response = await fetch("http://localhost:8080/accounts/notification-type", {
        headers : {
      Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
    }
    })
    const data = await response.json();
    options.value = data;
});

function handleClick(key) {
    options.value[key] = !options.value[key]
}

async function submit() {
    const token = localStorage.getItem('jwtToken')
    const response = await fetch("http://localhost:8080/accounts/notification-type", {
        method: "PATCH",
        
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(options.value)

    })
    const data = await response.json()
    if (response.status === 200) {
        alert("settings save");
        options.value = data;
    }
}

</script>

<template>
    <h5>Notification settings:</h5>
    <form @submit.prevent="submit" novalidate>
        <div v-for="(val, key) in options" class="account-setting-input">
            <label for="key">{{ key }}</label>
            <input id="key" type="checkbox" :checked="val" @click="handleClick(key)">
        </div>
        <button type="submit">save</button>
    </form>

</template>

<style scoped>
.account-setting-input {
    max-width: 120px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>