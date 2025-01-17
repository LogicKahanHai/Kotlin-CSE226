module.exports = (sequelize, DataTypes) => {
  return sequelize.define("Student", {
    name: DataTypes.STRING,
    roll_number: { type: DataTypes.STRING, unique: true },
    registration_number: { type: DataTypes.STRING, unique: true },
    section: DataTypes.STRING,
  });
};
