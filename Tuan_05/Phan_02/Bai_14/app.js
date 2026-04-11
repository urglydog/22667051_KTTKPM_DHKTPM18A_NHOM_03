const express = require("express");
const mysql = require("mysql2");

const app = express();

// ⚠️ host = tên service mysql trong docker-compose
const db = mysql.createConnection({
  host: "mysql",
  user: "root",
  password: "123456",
  database: "testdb"
});

db.connect(err => {
  if (err) {
    console.log("❌ DB connection failed:", err);
  } else {
    console.log("✅ Connected to MySQL");
  }
});

app.get("/", (req, res) => {
  res.send("🚀 Node connected to MySQL!");
});

app.listen(3000, () => {
  console.log("Server running on port 3000");
});