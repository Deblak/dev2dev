<script>
export default {
	data() {
		return {
			code: "",
		};
	},
	methods: {
		async submit() {
			const username = localStorage.getItem("username");
			if (!username) {
				alert("Session expired. Please login again.");
				this.$router.push("/");
				return;
			}

			const response = await fetch(
				"http://localhost:8080/accounts/verification-code",
				{
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify({
						username: username,
						code: this.code,
					}),
				}
			);

			if (response.ok) {
				const data = await response.json();
				localStorage.setItem("jwtToken", data.token);
				localStorage.setItem("role", data.role);
				localStorage.removeItem("preJwtToken");
				localStorage.removeItem("username");
				const roleValue = localStorage.getItem("role")
				if(roleValue === 'INTEGRATOR') {
					this.$router.push("/integrator").then(() => {
					this.$router.go();
				});
				} else if (roleValue === 'MEMBER') {
					this.$router.push("/article-share").then(() => {
						this.$router.go();
					});
				}
			} else {
				alert("Invalid or expired verification code.");
			}
		},
		handleCodeInput() {
			if (this.code.length >= 4) {
				document.getElementById("code").blur();
			}
		},
	},
};
</script>
<template>
	<main>
		<h1>{{ $t("login") }}</h1>
		<p>
			Please enter the verification code you received to continue your login.
		</p>
		<form @submit.prevent="submit">
			<label for="code">{{ $t("verificationCode") }}</label>
			<input
				id="code"
				v-model="code"
				type="text"
				class="one-time-code"
				inputmode="numeric"
				required
				ref="codeInput"
				@input="handleCodeInput"
				maxlength="4"
			/>

			<button type="submit">{{ $t("confirm") }}</button>
		</form>
	</main>
</template>
<style>
main {
	padding: 20px;
}

label::after {
	content: " *";
}

form {
	display: flex;
	flex-direction: column;
	width: min-content;
	align-items: stretch;
}

.password-container {
	position: relative;
	display: flex;
	align-items: center;
}

.one-time-code {
	all: unset;
	width: 12ch;
	font-size: 2.5em;
	letter-spacing: 2ch;
	padding: 0.5ch;
	background: repeating-linear-gradient(
		to right,
		#eee 0ch,
		#eee 2ch,
		transparent 0ch,
		transparent 3ch
	);
}

form > button {
	width: min-content;
	align-self: flex-end;
	margin-top: 1rem;
	padding: 1rem;
	cursor: pointer;
}
</style>