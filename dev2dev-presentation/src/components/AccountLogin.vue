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
            const response = await fetch('http://localhost:8080/accounts/login', options);
            
            if (response.ok) {
                const data = await response.json();
                alert('Account authenticated with username: ' + this.formData.username);
                localStorage.setItem('jwtToken', data.token);
                localStorage.setItem('role', data.role)
                window.location.reload()
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
    <section class="page">
    <h1>{{$t('login')}}</h1>
    <div class="form-container">
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
                <div class="password-container">
                <input
                    type="password"
                    name="password"
                    id="password"
                    v-model="formData.password"
                    :placeholder="$t('passwordPlaceholder')"
                />
                <p class="eye" @click="visiblePassword = !visiblePassword">
						<i :class="visiblePassword ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"></i>
				</p>
                </div>
            </div>
            <button type="submit">{{ $t('login') }}</button>
            <RouterLink to="/create-account">{{ $t('signup') }}</RouterLink>
        </form>
        </div>
    </section>
</template>

<style scoped>
h1{
    text-align: center;
    text-transform: uppercase;
}
button, a{
    display: flex;
    justify-content: center;
    margin-top: 1rem;
}
label::after {
    content: ' *';
}
.page {
	padding: 20px;
}

.eye {
	position: absolute;
	right: 10px;
	cursor: pointer;
	font-size: 18px;
}

.password-container {
	position: relative;
	display: flex;
	align-items: center;
}

.password-container input {
	width: 100%;
	position: relative;
}

</style>