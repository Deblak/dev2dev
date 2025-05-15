<script setup lang="ts">
import { ref } from 'vue';
import { useI18n } from 'vue-i18n'
const { t } = useI18n() //destructuration on lui dit dans le panier useI18n je veux seuelement la pomme t. 
const websiteName = ref<string>('');
const link = ref<string>('');
const description = ref<string>('');
const errors = ref<{[key : string]: string}>({}); // index signature 
const alreadyExistingTitle = ref<string>('');
const alreadyExistingUrl = ref<string>('');
const internalError = ref<string>("")
const successMsg = ref<string>('');
const validLink = (link : string) => {
    const regex : RegExp =/^https?:\/\/[^<>/\\s]+(?:\/[^<>/\s]+)*$/;
    return regex.test(link);
};
const reseterrorsValue = () => {
    errors.value.websiteNameErrorMsg = ""
    errors.value.descriptionErrorMsg = ""
    errors.value.linkErrorMsg = ""
    alreadyExistingTitle.value = ""
    alreadyExistingUrl.value = ""
    internalError.value = ""
}
const setFormError =  () => {
    reseterrorsValue()
        if(!websiteName.value.trim() || websiteName.value.trim().length > 200) {
            errors.value.websiteNameErrorMsg = "Incorrect value , please try again";
        };
        if(!description.value.trim() || description.value.trim().length > 1000) {
            errors.value.descriptionErrorMsg = "Incorrect description, please try again"
        }
        if(link.value.trim().length > 2300 || !validLink(link.value)) {
            errors.value.linkErrorMsg = "Invalid link, please provide another one"
        }
    }
const createRssFlux = (async ()=> {
    setFormError();
    if(errors.value.websiteNameErrorMsg.length > 0 || errors.value.descriptionErrorMsg.length > 0 || errors.value.linkErrorMsg.length > 0) {
            return;
        }
        try {
        const token = localStorage.getItem("jwtToken");
    const response = await fetch("http://localhost:8080/sandbox-rss/api/v1/provider", {
        method : "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body : JSON.stringify({
            title : websiteName.value,
            url : link.value,
            description : description.value
          }),
    });
    if(response.ok) {
        link.value = '';
        websiteName.value = '';
        description.value = '';
        successMsg.value = "Rss field provider correctly created !!!"
    }else if (response.status === 400 || 404){
        window.alert("not working")
        console.log(response)
        const contentType = response.headers.get("content-type");
        console.log(contentType)
        if(contentType && contentType.includes("application/json")) {
        response.json().then(error => {
            console.log(error)
            if(error.fieldErrors){
            if(error.fieldErrors.title) {
                alreadyExistingTitle.value = "Name already exists, please chose another one"
            } else
            if(error.fieldErrors.url) {
                alreadyExistingUrl.value = "Url already exists, please chose another one"
            } 
        } else {
            alreadyExistingUrl.value = "Some article from the rss feed already exists in the database"
        }
        }) }else {
            response.text().then(() => alreadyExistingUrl.value = "Error parsing of the format date ")
        }
    }} catch (error) {
        console.log(error.message)
        console.log(error)
        window.alert(error)
        internalError.value = "Internal server error";

    }
    setTimeout(() => (successMsg.value = ""), 6000);

})

</script>

<template>

        <form class="formContainer" @submit.prevent="createRssFlux" novalidate>
        <div class="firstLine">
            <div>
                <label for="websiteName">{{ t('websiteName') }}</label>
                <input type="texte" name="websiteName" id="websiteName" v-model="websiteName" required/>
                <p class="error" v-if="errors.websiteNameErrorMsg">{{ errors.websiteNameErrorMsg }}</p>
                <p class="error" v-if="alreadyExistingTitle">{{ alreadyExistingTitle  }}</p>
            </div>
            <div>
                <label for="rssUrl">{{ t('rssUrl') }}</label>
                <input type="texte" name="rssUrl" id="rssUrl" v-model="link" required/>
                <p class="error" v-if="errors.linkErrorMsg">{{ errors.linkErrorMsg }}</p>
                <p class="error" v-if="alreadyExistingUrl">{{ alreadyExistingUrl  }}</p>
            </div>
        </div>
        <div class="secondLine">
            <label for="description">{{ t('description') }}</label>
            <input type="texte" name="description" id="description"  v-model="description" required/>
            <p class="error" v-if="errors.descriptionErrorMsg">{{ errors.descriptionErrorMsg}}</p>
        </div>
        <p v-if="successMsg" class="message-success "> {{ successMsg }}</p>
        <p v-if="internalError" class="error "> {{ internalError }}</p>
        <button type="submit">{{t('articleShareBtn')}}</button>
    </form>

</template>

<style scoped>
.formContainer {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: column;
    gap: 3rem;
    box-shadow: 1rem 1rem 1rem grey; /* 1= horizontal/verti ; 2 = effet floutage ; 3 = expansion (radius)*/ 
    transition: all 0.2s ease;
    user-select:none;
}
.formContainer:hover {
    /* box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2); */
    transform: translateY(1px);
}

.firstLine {
    display: flex;
    gap : 3rem;
    flex-wrap: wrap;
}
.firstLine > div {
    display: flex;
    flex-direction: column;
    flex: 1;
}
.firstLine > div > input {
    max-width: 80%;
    min-height: 1.8rem;
}
.secondLine {
    display: flex;
    flex-direction: column;
    align-items: center;
}
.secondLine > input {
    height: 100%;
    width: 80%;
    min-height: 10rem;
    overflow-y: auto; 
    resize: vertical;
    margin-bottom: 2rem;
}

label::after {
	content: " *";
    color: red;
}
button {
    cursor: pointer;
}
.message-success {
  color: rgb(38, 147, 28);
}
</style>