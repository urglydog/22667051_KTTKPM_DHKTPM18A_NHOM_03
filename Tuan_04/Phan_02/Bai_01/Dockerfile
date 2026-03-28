# # Sử dụng Node.js 18 làm base image
# FROM node:18

# # Tạo thư mục làm việc trong container
# WORKDIR /app

# # Sao chép file package*.json để cài đặt dependencies
# COPY package*.json ./

# # Cài đặt dependencies
# RUN npm install

# # Sao chép toàn bộ mã nguồn vào container
# COPY . .

# # Expose cổng 3000
# EXPOSE 3000

# # Lệnh chạy ứng dụng
# CMD ["npm", "start"]

FROM nginx
COPY index.html /usr/share/nginx/html/index.html
