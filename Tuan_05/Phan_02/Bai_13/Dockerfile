# Build React
FROM node:18 AS build

WORKDIR /app
COPY package.json ./
RUN npm install

COPY . .
RUN npm run build

# Serve bằng Nginx
FROM nginx:latest

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80