<script setup>
import NotificationBar from './NotificationBar.vue';
</script>

<template>
    <header>
        <h1>Dev2Dev</h1>
        <div class="notification-bar" v-if="true">
            <NotificationBar/>
        </div>
    </header>
</template>

<style scoped>

header {
    background-color: rgb(179, 179, 179);
    height: 50px;
    padding-left: 5%;
    padding-right: 5%;
    margin-bottom: 50px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-shadow: 1px 1px 15px 1px rgba(0,0,0,0.34);
}

</style>