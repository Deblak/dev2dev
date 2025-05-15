import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createI18n } from "vue-i18n";
import English from "./i18n/locale-en-US.json";
import French from "./i18n/locale-fr-FR.json";

import "./styles/common.css";

const app = createApp(App);

export const i18n = createI18n({
  locale: "en",
  fallbackLocale: "en",
  legacy: false,
  globalInjection: true,
  messages: {
    en: English,
    fr: French,
  },
});

app.use(router);
app.use(i18n);
app.mount("#app");

app.config.globalProperties.$setLanguage = (lang) => {
  i18n.global.locale = lang;
};
