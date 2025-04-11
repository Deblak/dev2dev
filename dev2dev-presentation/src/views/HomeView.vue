<template>
	<div class="home">
		<div class="home-half">
			<AccountCreate />
		</div>
		<div class="home-half">
			<div class="login-container">
				<div v-if="showToast" class="inline-toast">
					Your compte is created 🎉
				</div>

				<AccountLogin />
			</div>
		</div>
	</div>
</template>

<script >
import AccountCreate from "../components/AccountCreate.vue";
import AccountLogin from "../components/AccountLogin.vue";

export default {
	name: "home",
	components: {
		AccountCreate,
		AccountLogin,
	},

	data() {
		return {
			showToast: false,
		};
	},
	mounted() {
		const query = this.$route.query;

		if (query.validation === "success") {
			this.showToast = true;

			const newQuery = { ...query };
			delete newQuery.validation;
			this.$router.replace({ query: newQuery });

			setTimeout(() => {
				this.showToast = false;
			}, import.meta.env.VITE_TOAST_TIMEOUT);
		}
	},
};
</script>

<style scoped>
.home {
	display: flex;
	max-width: 1024px;
	margin: 0 auto;
}

.home-half {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
}

@media (max-width: 768px) {
	.home {
		flex-direction: column;
		padding: 0;
	}

	.home-half {
		height: auto;
		padding: 0 20px;
		margin: 0;
	}
}

.login-container {
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
	position: relative;
}

.inline-toast {
	background-color: #4caf50;
	color: white;
	padding: 10px 20px;
	border-radius: 6px;
	margin-bottom: 16px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
	animation: fadeInOut 3.5s ease-in-out forwards;
}

@keyframes fadeInOut {
	0% {
		opacity: 0;
		transform: translateY(-10px);
	}
	10%,
	90% {
		opacity: 1;
		transform: translateY(0);
	}
	100% {
		opacity: 0;
		transform: translateY(-10px);
	}
}
</style>