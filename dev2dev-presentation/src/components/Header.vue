<script setup>
import { onMounted, onUpdated, ref } from "vue";
import NotificationBar from "./NotificationBar.vue";
import { RouterLink } from "vue-router";

const token = ref(null);
const userRole = ref(null);

onMounted(() => {
  token.value = localStorage.getItem("jwtToken");
  userRole.value = localStorage.getItem("role");
});
</script>

<template>

  <header>
    <h1>Dev2Dev</h1>

    <div class="menu" v-if="token && userRole === 'MEMBER'">
      <button class="margin-btn">
        <RouterLink to="/article-share" class="text-decoration-none">{{
          $t("shareArticle")
        }}</RouterLink>
      </button>
      <div class="notification-bar" v-if="token && userRole === 'MEMBER'">
        <a href="/account-notification-settings">
            profil
          </a>
        <NotificationBar />
      </div>
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
  box-shadow: 1px 1px 15px 1px rgba(0, 0, 0, 0.34);
}

.notification-bar {
    display: flex;
}

a {
    margin-right: 50px;
}

.menu {
  width: 150px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* padding: 15px; */
}
.margin-btn {
  margin-bottom: 15%;
}
.text-decoration-none {
  text-decoration: none;
  color: white;
}
</style>

