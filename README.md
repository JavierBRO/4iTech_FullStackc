# 4iTech_Dockerizado Full Stack (PENDIENTE DOCKERIZACION)
Proyecto 4iTech integrado en Docker para su despliegue en local sin necesidad de arranque de IDEs, VSC e IntelliJ
# Backend y Frontend integrados
No desacoplados

## ARCHIVO Dockerfile para backend:
# https://hub.docker.com/_/eclipse-temurin    el docker pull de abajo (descarga de la imagen) lo ejecutamos por consola:
# docker pull eclipse-temurin:21-jre-jammy

# En IntelliJ IDEA, en el panel lateral derecho, en el icono de la M de Maven
#   desplegar donde pone Lifecycle y pulsar dos veces en package
#   esto generará el archivo .jar que contiene la aplicación java empaquetada
#   comprobar que el archivo .jar está en la carpeta target

#FROM eclipse-temurin:21-jre-jammy
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} backend.jar
#ENTRYPOINT ["java", "-jar", "/backend.jar"]

# Construir la imagen docker y se debe ejecutar donde esté el Dockerfile
# Es decir, en el directorio del backend : ejecutar en el directorio del Backend el comando de abajo con el punto final
# docker build -t backend:0.0.1 .

## ARCHIVO Dockerfile para frontend:
# Fase de construcción, compilar el proyecto Angular    hacer pull descarga de imagen, de abajo
# docker pull node:20.11.0-alpine
##FROM node:20.11.0-alpine as build

# Establecer directorio de trabajo dentro del contenedor
##WORKDIR /app

# Instalar dependencias
##COPY package*.json ./
##RUN npm i

# Copiar los archivos del proyecto al directorio de trabajo del contenedor
##COPY . .

# Construir la aplicación Angular en modo producción para generar la carpeta dist
##RUN npm run build -- --configuration production

# docker pull nginx:1.25-alpine
##FROM nginx:1.25-alpine

# Copiar configuración nginx
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Eliminar los archivos por defecto de nginx:
##RUN rm -rf /usr/share/nginx/html/*

# Copiar los archivos de la build de Angular a nginx
# /app/dist/nombreproyecto <-- AQUÍ SE PONE EL NOMBRE DEL PROYECTO DE ANGULAR
# COPY --from=build /app/dist/frontend/usr/share/nginx/html
##COPY --from=build /app/dist/angular-4i-tech/browser /usr/share/nginx/html

##EXPOSE 80

# Ejecutar nginx en primer plano
##CMD ["nginx", "-g", "daemon off;"]

# Construir la imagen frontend
# docker build -t frontend:0.0.1 .
# docker rm frontend

