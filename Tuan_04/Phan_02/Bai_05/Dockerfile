FROM golang:1.20-alpine AS builder

WORKDIR /app

COPY . .

# 👇 thêm dòng này
RUN go mod init go-app && go mod tidy

RUN go build -o app

FROM alpine:latest

WORKDIR /root/

COPY --from=builder /app/app .

EXPOSE 8080

CMD ["./app"]