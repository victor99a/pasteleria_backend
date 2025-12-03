# Etapa 1: construir la app con Gradle
FROM gradle:8.5-jdk17-alpine AS build

WORKDIR /app

# Copiamos TODO el proyecto Ktor
COPY . .

# Construye la distribución ejecutable (installDist)
RUN gradle installDist --no-daemon

# Etapa 2: imagen liviana solo con el JRE y la app ya construida
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 👇 MUY IMPORTANTE:
# El nombre de la carpeta lo toma de rootProject.name en settings.gradle.kts
# Si en tu settings.gradle.kts dice, por ejemplo:
#   rootProject.name = "pasteleria_backend_ktor"
# deja esta línea tal cual:
COPY --from=build /app/build/install/pasteleria_backend_ktor ./

# Puerto donde corre Ktor (el que tienes en application.conf)
EXPOSE 8080

# Comando de arranque
CMD ["bin/pasteleria_backend_ktor"]
