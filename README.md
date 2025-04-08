# Welcome to dev2dev !

dev2dev project with **Java** (JDK 21), **Vite Vue.js** (v.3). No front frameworks.

## Init database


## Init back

## Init front
- Install dependencies
```bash
npm install
```
- Run Vite for dev environnement
```bash
npm run dev
```
- Run Vite for prod environnement
```bash
npm run build
```

### Custom to manage environnement
Create files (on the root)  and custom your  environnement variables with Vite convention: 

dev2dev-presentation/
├── public/
├── src/
│   └── directories...
│   └── ...
├── .env.development
├── .env.production
├── package.json

 - For dev environnement
File name: `.env.development`
Variable template: `VITE_API_URL=http://localhost:<number>/`

 - For prod environnement
File name: `.env.development`
Variable template: `VITE_API_URL=VITE_API_URL=https://<domain-name>`

- For call variable, you can use:
`const apiUrl = import.meta.env.VITE_API_URL;`
