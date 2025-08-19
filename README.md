<h1 align="center"> 🗺️ Aplicación de Rutas - Android Studio</h1>
<p align="center">
  Esta es una aplicación móvil desarrollada en Android Studio que integra el SDK de Google Maps para la visualización de mapas e incluye un chatbot desarrollado en Flask como asistente interactivo.
  La aplicación también cuenta con un sistema de autenticación gestionado en Express y utiliza SQLite para el almacenamiento de rutas favoritas de los usuarios.
</p>

<p align="center"> 
  <img width="24%" alt="MiRuta 1" src="https://github.com/user-attachments/assets/565a00c6-b6c2-49a0-8d26-b81e95e74395" />
  <img width="24%" alt="MiRuta 2" src="https://github.com/user-attachments/assets/df90c8bb-83f6-4b8c-9158-0f011c287b6b" />
  <img width="24%" alt="MiRuta 3" src="https://github.com/user-attachments/assets/3df62038-30b3-4a33-ad18-2d151a3db1ec" />
  <img width="24%" alt="MiRuta 4" src="https://github.com/user-attachments/assets/159af63b-11f1-4af5-bc49-ad36fc50406c" />
</p> 

<p align="center">
  <img width="24%" alt="MiRuta 5" src="https://github.com/user-attachments/assets/8f7c4ee4-a261-41ba-967d-9f4ec6cf01d2" />
  <img width="24%" alt="MiRuta 6" src="https://github.com/user-attachments/assets/64f08137-db46-4c07-ab5e-0aa6ba88afa3" />
  <img width="24%" alt="MiRuta 7" src="https://github.com/user-attachments/assets/9dc61302-2477-46ae-be1e-4d514d651e6c" />
  <img width="24%" alt="MiRuta 8" src="https://github.com/user-attachments/assets/13e70209-cc22-42c4-a6ec-30df49f6fc8a" />
</p> 

<p align="center">
  <img width="24%" alt="MiRuta 9" src="https://github.com/user-attachments/assets/3eef91cc-17e9-4bcf-bc11-ad162c853a8d" />
  <img width="24%" alt="MiRuta 10" src="https://github.com/user-attachments/assets/9f46c206-33b9-4930-bf9e-b43b461baac3" />
  <img width="24%" alt="MiRuta 11" src="https://github.com/user-attachments/assets/147eec43-da7b-4e01-a509-b093a5016cb7" />
  <img width="24%" alt="MiRuta 12" src="https://github.com/user-attachments/assets/abe5e9e9-878c-4281-b573-f20a7d40b3aa" />
</p> 

<p align="center">
  <img width="24%" alt="MiRuta 13" src="https://github.com/user-attachments/assets/3f0e3773-1d9f-4eed-acd5-9ef126050a55" />
  <img width="24%" alt="MiRuta 14" src="https://github.com/user-attachments/assets/9c163151-6eb6-46b8-989e-d893581a5eb1" />
  <img width="24%" alt="MiRuta 15" src="https://github.com/user-attachments/assets/b8f1bae7-ff8a-4c06-9d7d-0bf4c44cf37f" />
  <img width="24%" alt="MiRuta 16" src="https://github.com/user-attachments/assets/e1e8e837-621a-46ac-a8d6-5ce108929236" />
</p> 

<p align="center">
  <img width="24%" alt="MiRuta 17" src="https://github.com/user-attachments/assets/7dd6e834-f4af-448f-b577-ac6d6fbb5723" />
</p> 


## 🚀 Tecnologías Utilizadas
### 📱 Aplicación móvil

Android Studio (Java/Kotlin)
Google Maps SDK
SQLite (almacenamiento local)

### 🤖 Chatbot
Flask (Python)

### 🔐 Autenticación
Node.js + Express
JWT (tokens de autenticación)

## ⚙️ Instalación y Configuración
### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/usuario/android-rutas-app.git
cd android-rutas-app
```

### 2️⃣ Configuración de la App en Android Studio
Abrir la carpeta android-app en Android Studio.
Configurar una API Key de Google Maps en el archivo AndroidManifest.xml:
```bash
<meta-data android:name="com.google.android.geo.API_KEY" android:value="TU_API_KEY_AQUI"/>
```

Ejecutar la aplicación en un emulador o dispositivo físico.

### 3️⃣ Configuración del Chatbot (Flask)
```bash
cd chatbot
pip install -r requirements.txt
python app.py
```

El chatbot correrá en: http://localhost:5000

### 4️⃣ Configuración del Backend de Autenticación (Express)
```bash
cd backend-auth
npm install
npm run dev
```
El backend se ejecutará en: http://localhost:4000

## 📌 Funcionalidades
🗺️ Google Maps integrado: Visualiza mapas interactivos con la ubicación actual.
💬 Chatbot inteligente: Asistente desarrollado en Flask para resolver dudas.
🔐 Sistema de autenticación: Registro e inicio de sesión con Express y JWT.
⭐ Favoritos: Guarda rutas en SQLite para acceder a ellas rápidamente.

## 📖 Notas
Requiere configurar la API Key de Google Maps antes de ejecutar la app.
Flask y Express deben estar corriendo para que el chatbot y la autenticación funcionen.
SQLite se maneja localmente dentro de la aplicación móvil.

