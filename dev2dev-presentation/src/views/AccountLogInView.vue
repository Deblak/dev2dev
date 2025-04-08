<template>
    <main>
        <form>
            <div>
                <label for="username">{{$t('email')}}</label>
                <input id="username" :placeholder="$t('emailPlaceholder')"></input>
            </div>
            <div>
                <label for="password">{{$t('password')}}</label>
                <input id="password" type="password" :placeholder="$t('passwordPlaceholder')"></input>
            </div>
            <RouterLink to="/create-account">{{$t('signup')}}</RouterLink>
            <button type="submit">{{$t('login')}}</button>
        </form>
    </main>
</template>
<style scoped src="../styles/login.css">
</style>