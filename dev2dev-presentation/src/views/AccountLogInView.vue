<script>
export default {
    data() {
        return {
            formData: {
                username: "",
                password: ""
            }
        };
    },
    methods: {
        async submit() {
            const options = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.formData)
            };
            const response = await fetch('http://localhost:8080/login', options);
            if (response.ok) {
                alert('Account authenticated with username: ' + this.formData.username);
            } else if (response.status == 401) {
                alert("Bad credentials");
            } else {
                alert('Account not found with username: ' + this.formData.username);
            }
        }
    }
};
</script>

<template>
    <main>
        <form @submit.prevent="submit" novalidate>
            <div>
                <label for="username">{{ $t('email') }}</label>
                <input
                    type="email"
                    name="username"
                    id="username"
                    v-model="formData.username"
                    :placeholder="$t('emailPlaceholder')"
                />
            </div>
            <div>
                <label for="password">{{ $t('password') }}</label>
                <input
                    type="password"
                    name="password"
                    id="password"
                    v-model="formData.password"
                    :placeholder="$t('passwordPlaceholder')"
                />
            </div>
            <RouterLink to="/create-account">{{ $t('signup') }}</RouterLink>
            <button type="submit">{{ $t('login') }}</button>
        </form>
    </main>
</template>

<style scoped src="../styles/login.css"></style>
