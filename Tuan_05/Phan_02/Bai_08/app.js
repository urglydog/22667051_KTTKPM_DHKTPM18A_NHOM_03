const express = require("express");
const mysql = require("mysql2");

const app = express();
const PORT = 3000;

// Tạo kết nối MySQL
const db = mysql.createConnection({
  host: "mysql",        // ⚠️ QUAN TRỌNG: tên service trong docker-compose
  user: "user",
  password: "password",
  database: "mydb"
});

// Kết nối DB
db.connect((err) => {
  if (err) {
    console.error("❌ Lỗi kết nối MySQL:", err);
  } else {
    console.log("✅ Kết nối MySQL thành công");
  }
});

// API test
app.get("/", (req, res) => {
  db.query("SELECT NOW() as time", (err, result) => {
    if (err) {
      return res.send("Lỗi DB");
    }
    res.send("Time từ MySQL: " + result[0].time);
  });
});

app.listen(PORT, () => {
  console.log(`Server chạy tại port ${PORT}`);
});