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
      <RouterLink to="/article-share" class="text-decoration-none">{{
        $t("shareArticle")
      }}</RouterLink>
      <div class="notification-bar">
        <RouterLink to="/account-notification-settings"> 
          <i class="profil mdi mdi-account"></i> 
        </RouterLink>
        <NotificationBar />
      </div>
    </div>
    <div v-if="token && userRole === 'INTEGRATOR'">
        <RouterLink to="/integrator">
          <span class="material-symbols-outlined" id="rss_feed_icon">rss_feed</span>
        </RouterLink>
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

.profil {
  color: black;
  font-size: 28px;
}

.notification-bar {
  display: flex;
}

a {
  margin-right: 50px;
}

.menu {
  width: 300px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.text-decoration-none {
  text-decoration: none;
  display: block;
  width: 100px;
}
#rss_feed_icon{
  color: black;
  font-size: 28px;
}
#rss_feed_icon:hover{
  color : red;
}
#rss_feed_icon:active{
  color : black;
}
</style>