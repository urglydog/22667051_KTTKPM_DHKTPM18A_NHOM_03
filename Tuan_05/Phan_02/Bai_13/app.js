const express = require("express");
const app = express();

const PORT = 3000;

// route test
app.get("/", (req, res) => {
  res.send("🚀 Node app is running!");
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});