import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/HomeView.vue"),
      meta: {
        requiresAuth : false,
        forbiddenAccesRoles : ['INTEGRATOR','MEMBER']
      }
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
      path: "/forbidden",
      name: "forbidden",
      component: () => import("../views/errors/PageForbiddenView.vue"),
    },
    {
      path: "/article-share",
      name: "article-share",
      meta: {
        requiresAuth: true,
        allowedRole : 'MEMBER'
      },
      component: () => import("../views/ArticleShareView.vue"),
    },
    {
      path: "/articles-list-share",
      name: "articles-list-share",
      component: () => import("../views/ArticlesListShareView.vue"),
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
      meta: {
        requiresAuth: true,
        allowedRole : 'INTEGRATOR'
      },
      component : () => import("../views/IntegratorView.vue")
    }
  ],
});
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("jwtToken");
  const role = localStorage.getItem('role');
  const rolesAllowed = to.meta.allowedRole;
  const forbiddenRole = to.meta.forbiddenAccesRoles || []
  const isAlreadylogged = forbiddenRole.includes(role);
  if (to.meta.requiresAuth && !isAuthenticated) {
    return next("/");
  }
  if(rolesAllowed && role !== rolesAllowed || role && isAlreadylogged) {
    return next({name: 'forbidden'})
  }

  next();
});

export default router;
