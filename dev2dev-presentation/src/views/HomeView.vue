<template>
	<div class="home">
		<div class="home-half">
			<AccountCreate />
		</div>
		<div class="home-half">
			<AccountLogin />
		</div>
	</div>
</template>

<script >
import AccountCreate from "../components/AccountCreate.vue";
import AccountLogin from "../components/AccountLogin.vue";

export default {
	name: "home",
	components: {
		AccountCreate, AccountLogin
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
</style>
