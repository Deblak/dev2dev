<!-- src/components/RegisterForm.vue -->
<template>
	<div class="page">
		<h1>SIGN UP</h1>
		<div class="form-container">
			<form @submit.prevent="validForm">
				<label>Email*</label>
				<input
					type="email"
					v-model="form.email"
					placeholder="email@email.com"
				/>
				<p class="error" v-if="errors.email">{{ errors.email }}</p>

				<label>Password*</label>
				<div class="password-container">
					<input
						:type="visiblePassword ? 'text' : 'password'"
						v-model="form.password"
						placeholder="Test123!"
					/>
					<p class="eye" @click="visiblePassword = !visiblePassword">
						<i :class="visiblePassword ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"></i>
                    </p>
				</div>
				<p class="error" v-if="errors.password">{{ errors.password }}</p>

				<button type="submit">Create account</button>
			</form>
            <p class="already-accou t
            ">
        Already have an account
        <a href="/login">Sign In</a>
    </p>
		</div>
	</div>
</template>

<script>


export default {
	name: "AccountCreate",
	data() {
		return {
			form: {
				email: "",
				password: "",
			},
			errors: {
				email: "",
				password: "",
			},
			visiblePassword: false,
		};
	},
	methods: {
		validForm() {
			this.errors.email = "";
			this.errors.password = "";

			if (!this.form.email.trim()) {
				this.errors.email = "Email is required.";
			}

			if (!this.validPassword(this.form.password)) {
				this.errors.password =
					"8 characters min, with one uppercase, one lowercase, one number and one special character.";
			}

			if (!this.errors.email && !this.errors.password) {
				alert("Account created successfully !");
			}
		},
		validPassword(mdp) {
			const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
			return regex.test(mdp);
		},
	},
};
</script>

<style scoped>
.page {
	flex-direction: column;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	text-align: center;
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

.form-container {
	width: 100%;
	max-width: 400px;
	margin: auto;
	padding: 20px;
	background: #f0f0f0;
	border-radius: 10px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
	color: black;
	text-align: left;
}

form {
	display: flex;
	flex-direction: column;
    width: 100%;
}

input {
	width: 100%;
	align-self: center;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 5px;
}


button {
	align-self: center;
	margin-top: 20px;
	padding: 8px;
	border : none;
}


@media (max-width: 600px) {
	.form-container {
		padding: 15px;
	}
	input {
		max-width: 100%;
	}
	button {
		width: 100%;
	}
}
</style>

