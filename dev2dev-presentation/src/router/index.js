import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      alias: ["/index.html", "/login"],
      name: "login",
      component: () => import("../views/AccountLogInView.vue"),
    },
    {
      path: "/create-account",
      name: "account-create",
      component: () => import("../components/AccountCreate.vue"),
    },
    {
      path: "/:notFound",
      name: "not-found",
      component: () => import("../views/errors/PageNotFoundView.vue"),
    },
    {
      path: "/article-share",
      name: "article-share",
      component: () => import("../views/ArticleShareView.vue"),
    },
  ],
});

export default router;
