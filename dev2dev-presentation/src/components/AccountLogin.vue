<script>
export default {
    name: "account-login",
    data() {
        return {
            visiblePassword: false,
            formData: {
                username: "",
                password: ""
            },
            error: {
                username: "",
                password: "",
                message: ""
            }
        };
    },
    methods: {
        validateForm() {
            this.error.username = "";
            this.error.password = "";
            this.error.message = "";

            let isValid = true;

            if (!this.formData.username) {
                this.error.username = "Email is required.";
                isValid = false;
            } else if (!/^\S+@\S+\.\S+$/.test(this.formData.username)) {
                this.error.username = "Email not valid.";
                isValid = false;
            }

            if (!this.formData.password) {
                this.error.password = "Password is required";
                isValid = false;
            }

            return isValid;
        },

        async submit() {
            if (!this.validateForm()) return;

            const options = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.formData)
            };

            try {
                const response = await fetch('http://localhost:8080/accounts/login', options);

                if (response.ok) {
                    const data = await response.json();
                    alert('Additional authentication is required. Please check your email.');
                    localStorage.setItem('username', this.formData.username);
                    this.$router.push('verification-code');
                } else if (response.status === 401) {
                    this.error.message = "Bad credentials";
                } else if (response.status === 405) {
                    this.errorMessage = "Your email address is not verified.";
                } else {
                    this.error.message = "Account not found with username: " + this.formData.username;
                }
            } catch (error) {
                this.error.message = "Error connecting to the server.";
                console.error(error);
            }
        }
    }
};
</script>
<template>
    <section class="page">
        <h1>{{ $t('login') }}</h1>
        <div class="form-container">
            <form @submit.prevent="submit" novalidate>
                <label for="username">{{ $t('email') }}</label>
                <input type="text" name="username" id="username" inputmode="email" v-model="formData.username"
                    :placeholder="$t('emailPlaceholder')" />
                <p v-if="error.username" class="error">{{ error.username }}</p>

                <label for="password">{{ $t('password') }}</label>
                <div class="password-container">
                    <input :type="visiblePassword ? 'text' : 'password'" name="password" id="password"
                        v-model="formData.password" :placeholder="$t('passwordPlaceholder')" />
                    <p class="eye" @click="visiblePassword = !visiblePassword">
                        <i :class="visiblePassword ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"></i>
                    </p>
                </div>
                <p v-if="error.password" class="error">{{ error.password }}</p>
                <p v-if="error.message" class="error">{{ error.message }}</p>
                <button type="submit">{{ $t('login') }}</button>
            </form>
        </div>
    </section>
</template>

<style scoped>
h1 {
    text-align: center;
    text-transform: uppercase;
}

label::after {
	content: " *";
}

.page {
    padding: 20px;
}

.password-container {
    position: relative;
    display: flex;
    align-items: center;
}

.password-container input {
    position: relative;
}

.eye {
    position: absolute;
    right: 10px;
    cursor: pointer;
    font-size: 18px;
}

input {
    max-width: 100%;
}

@media (max-width: 600px) {
    button {
        width: 100%;
    }
    .page {
        padding: 10px;
    }
}
</style>