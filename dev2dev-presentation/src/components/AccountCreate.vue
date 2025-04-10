<template>
	<div class="page">
		<h1>CREATE ACCOUNT</h1>
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
		</div>
	</div>
</template>
 
<script>
import "../styles/homeForm.css";

export default {
	name: "account-create",
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
	created() {
		this.handleTokenValidation();
	},
	methods: {
		validPassword(password) {
			const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
			return regex.test(password);
		},
		async handleTokenValidation() {
			const token = this.$route.query.token;
			if (!token) return;

			try {
				const response = await fetch(
					`http://localhost:8080/accounts/validate?token=${token}`
				);

				if (response.ok) {
					// Redirection vers login avec message
					this.$router.push("/accounts/login?validated=true");
				} else {
					const error = await response.text();
					this.$router.push(
						"/accounts/login?error=" + encodeURIComponent(error)
					);
				}
			} catch (err) {
				this.$router.push("/accounts/login?error=Erreur de validation");
			}
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

					if (response.ok) {
						alert("Compte créé! Un email de validation a été envoyé.");
						this.$router.push("/");
					} else {
						throw new Error("Erreur lors de la création du compte");
					}
				} catch (error) {
					console.error(error);
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
	.page {
		padding: 10px;
	}
}
</style>

