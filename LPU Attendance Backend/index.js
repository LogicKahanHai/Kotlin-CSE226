const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const db = require("./models");

const app = express();
const PORT = 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Routes
app.use("/api/teachers", require("./routes/teachers"));
app.use("/api/students", require("./routes/students"));
app.use("/api/attendance", require("./routes/attendance"));

// Sync Database
db.sequelize.sync().then(() => {
  console.log("Database synced");
  app.listen(PORT, () =>
    console.log(`Server running on http://localhost:${PORT}`)
  );
});
