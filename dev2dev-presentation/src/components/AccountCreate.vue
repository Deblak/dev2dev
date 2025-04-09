<!-- src/components/RegisterForm.vue -->
<template>
	<div class="page">
		<h1>SIGN UP</h1>
		<div class="form-container">
			<form @submit.prevent="validForm">
				<label>Email*</label>
				<input
					type="email"
					v-model="form.username"
					placeholder="email@email.com"
				/>
				<p class="error" v-if="errors.username">{{ errors.username }}</p>

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
			<p class="already-accou t">
				Already have an account
				<a href="/login">Sign In</a>
			</p>
		</div>
	</div>
</template>

<script>
import "../styles/homeForm.css";

export default {
	name: "AccountCreate",
	data() {
		return {
			form: {
				username: "",
				password: "",
			},
			errors: {
				username: "",
				password: "",
			},
			visiblePassword: false,
		};
	},
	methods: {
		validPassword(mdp) {
			const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
			return regex.test(mdp);
		},
		async validForm() {
			this.errors.username = "";
			this.errors.password = "";

			if (!this.form.username.trim()) {
				this.errors.username = "Email is required.";
			}

			if (!this.validPassword(this.form.password)) {
				this.errors.password =
					"8 characters min, with one uppercase, one lowercase, one number and one special character.";
			}

			if (!this.errors.username && !this.errors.password) {
				try {
					const response = await fetch("http://localhost:8080/accounts", {
						method: "POST",
						headers: {
							"Content-Type": "application/json",
						},
						body: JSON.stringify({
							username: this.form.username,
							password: this.form.password,
						}),
					});
					if (!response.ok) {
						const errorData = await response.json();
						alert("Error: " + (errorData.message || "Something went wrong."));
						return;
					}

					alert("Account created successfully!");
					this.form.username = "";
					this.form.password = "";
				} catch (error) {
					console.error("Network error:", error);
					alert("Network error. Please try again later.");
				}
			}
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

