import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/HomeView.vue"),
    },
    {
      path: "/create-account",
      name: "account-create",
      component: () => import("../components/AccountCreate.vue"),
    },
    {
      path: "/accounts/login",
      name: "login",
      component: () => import("../components/AccountLogin.vue"),
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
    {
      path: "/account-notification-settings",
      name: "account-notification-settings",
      component: () => import("../views/AccountNotificationSetings.vue"),
    },
    {
      path: "/verification-code",
      name: "verification-code",
      component: () => import("../views/EmailVericationCodeView.vue"),
    },
    {
      path: "/integrator",
      name: "integrator",
      component : () => import("../views/IntegratorView.vue")
    }
  ],
});
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("jwtToken");
  if (to.meta.requiresAuth && !isAuthenticated) {
    return next("/");
  }

  next();
});

export default router;
