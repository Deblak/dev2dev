<template>
	<div class="page">
		<h1>{{ $t("ArticlesListShare") }}</h1>
		<div class="form-container">
			<v-card
				v-for="(article, index) in paginatedArticles"
				:key="article.link"
				:class="{ 'notification-unread': !article.isRead }"
				class="mb-4"
				@click="markAsRead(article)"
			>
				<div class="field">
					<label class="field-label">{{ $t("title") }}</label>
					<a :href="article.link" target="_blank" class="field-link">
						{{ article.title }}
					</a>
				</div>

				<div class="field-date">
					<label class="field-label">{{ $t("dateandhour") }}</label>
					<p class="field-text">
						{{ formatDateTime(article.sharedDate) }}
					</p>
				</div>

				<div class="field">
					<label class="field-label">{{ $t("description") }}</label>
					<p class="field-text">
						{{ article.description }}
					</p>
				</div>

				<div class="text-center mt-4" v-if="index === paginatedArticles.length - 1 && pageCount > 1">
					<button @click="prevPage" :disabled="currentPage === 1">
						&lt; {{ $t("prev") || "Précédent" }}
					</button>
					<span style="margin: 0 10px">
						{{ currentPage }} / {{ pageCount }}
					</span>
					<button @click="nextPage" :disabled="currentPage === pageCount">
						{{ $t("next") || "Suivant" }} &gt;
					</button>
				</div>

				<hr v-if="index !== paginatedArticles.length - 1" class="separator" />
			</v-card>
		</div>
	</div>
</template>


<script>
import "../styles/homeForm.css";

export default {
	data() {
		return {
			articles: [],
			currentPage: 1,
			pageSize: 4,
		};
	},
	async mounted() {
		const token = localStorage.getItem("jwtToken");

		const response = await fetch("http://localhost:8080/articles/share", {
			headers: { Authorization: `Bearer ${token}` },
		});
		if (response.ok) {
			this.articles = await response.json();
		} else {
			console.error("Erreur lors du chargement des articles.");
		}
	},
	computed: {
		paginatedArticles() {
			const start = (this.currentPage - 1) * this.pageSize;
			const end = start + this.pageSize;
			return this.articles.slice(start, end);
		},
		pageCount() {
			return Math.ceil(this.articles.length / this.pageSize);
		},
	},
	methods: {
		formatDateTime(dateStr) {
			const date = new Date(dateStr);

			return date.toLocaleDateString(this.$i18n.locale, {
				year: "numeric",
				month: "long",
				day: "numeric",
				hour: "2-digit",
				minute: "2-digit",
				second: "2-digit",
				hour12: false,
			});
		},
		goToPage(page) {
			if (page >= 1 && page <= this.pageCount) {
				this.currentPage = page;
			}
		},
		nextPage() {
			if (this.currentPage < this.pageCount) {
				this.currentPage++;
			}
		},
		prevPage() {
			if (this.currentPage > 1) {
				this.currentPage--;
			}
		},
	},
	async markAsRead(article) {
		if (!article || !article.id) {
			console.error("Article ou notificationId manquant.");
			return;
		}

		const token = localStorage.getItem("jwtToken");

		const response = await fetch(
			`http://localhost:8080/notifications/read/${article.id}`,
			{
				method: "PUT",
				headers: { Authorization: `Bearer ${token}` },
			}
		);
		if (response.ok) {
			// Met à jour la notification dans la liste locale après la mise à jour réussie
			const updatedArticles = this.articles.map((art) => {
				if (art.id === article.id) {
					art.isRead = true;
				}
				return art;
			});
			this.articles = updatedArticles; // Mise à jour de l'état local
		} else {
			console.error("Erreur lors de la mise à jour de la notification.");
		}
	},
};
</script>


<style scoped>
.text-center {
	text-align: center;
	display: flex;
	justify-content: center;
}

.form-container {
	width: 100%;
	max-width: 700px;
	margin: auto;
	padding: 20px;
	background: #f0f0f0;
	border-radius: 10px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
	color: black;
	text-align: left;
}

.field-label {
	display: block;
}

.field-date {
	display: flex;
	align-items: center;
}
/* Fond bleu pour les notifications non lues */
.notification-unread {
	background-color: #d0ebff; /* bleu ciel */
	transition: background-color 0.3s ease;
	cursor: pointer;
}

/* Style général des cartes */
.notification-card {
	transition: background-color 0.3s ease;
	cursor: pointer;
}

.separator {
	margin-top: 20px;
	border: 1px solid #ddd;
}

.message-error {
	font-size: 0.9em;
	color: red;
}

input,
textarea {
	width: 60%;
	margin-bottom: 0.5rem;
	margin-left: 0.5rem;
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
