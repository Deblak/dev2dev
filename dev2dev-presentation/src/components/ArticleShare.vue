<script>
export default {
  data() {
    return { errors: [], url: "", successMsg: "" };
  },
  methods: {
    setSuccessMsg(message) {
      this.successMsg = message;
    },
    async checkForm(e) {
      this.errors = [];
      if (!this.url) {
        this.errors.push("urlRequire");
      } else if (!this.isValidURL(this.url)) {
        this.errors.push("urlNotValid");
      }
      if (this.errors.length > 0) {
        return;
      }

      try {
        const response = await fetch("http://localhost:8080/articles/share", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ url: this.url }),
        });
        if (response.ok) {
          this.setSuccessMsg(this.$t("articleSharedSuccess"));
          this.url = ""; // reset
        } else if (response.status == 400) {
          //add handle errors
          response.json().then((e) => {
            this.errors.push(e.url || e.title);
          });
        }
      } catch (error) {
        this.errors.push("errorServer");
      }
    },
    isValidURL(str) {
      try {
        new URL(str);
        return true;
      } catch (_error) {
        return false;
      }
    },
  },
};
</script>

<template>
  <div class="text-center">
    <div class="article-share-box">
      <h1>{{ $t("articleShareTitle") }}</h1>
      <p v-if="successMsg">{{ successMsg }}</p>
      <form @submit.prevent="checkForm" novalidate>
        <div>
          <label for="url">{{ $t("articleShareLabel") }} </label>
          <input type="url" name="url" id="url" v-model="url" />
        </div>
        <p v-for="error in errors" class="message-error" v-if="errors">
          <!-- {{ error }} -->
          {{ $t(error) }}
        </p>
        <button type="submit">{{ $t("articleShareBtn") }}</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.text-center {
  text-align: center;
  display: flex;
  justify-content: center;
}
.article-share-box {
  width: 50%;
  border: solid rgb(96, 95, 95);
  padding-bottom: 4rem;
  box-shadow: 5px 5px 5px rgb(96, 95, 95);
}
label::after {
  content: "*";
  color: red;
}
.message-error {
  font-size: small;
  color: red;
}
input {
  width: 60%;
  margin-bottom: 0.5rem;
  margin-left: 0.5rem;
}
button {
  margin-top: 1rem;
  padding-left: 1rem;
  padding-right: 1rem;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
}
</style>
