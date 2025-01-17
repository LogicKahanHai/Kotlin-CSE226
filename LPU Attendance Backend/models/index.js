const { Sequelize } = require("sequelize");

const sequelize = new Sequelize("attendance", "username", "password", {
  host: "localhost",
  dialect: "postgres",
});

const db = {};
db.Sequelize = Sequelize;
db.sequelize = sequelize;

// Models
db.Teacher = require("./Teacher")(sequelize, Sequelize);
db.Student = require("./Student")(sequelize, Sequelize);
db.AttendanceSession = require("./AttendanceSession")(sequelize, Sequelize);
db.AttendanceRecord = require("./AttendanceRecord")(sequelize, Sequelize);

module.exports = db;
